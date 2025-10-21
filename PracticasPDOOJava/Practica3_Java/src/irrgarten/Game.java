package irrgarten;

import java.util.ArrayList;

/**
 * Núcleo del juego: gestiona jugadores, monstruos, el laberinto, el turno 
 * y el registro de eventos (log).
 * @author Joaquín Cruz Lorenzo
 */
public class Game {
    // Número máximo de rondas
    private static final int MAX_ROUNDS = 10;
    
    private  int currentPlayerIndex;
    private String log;
    private Player currentPlayer; // Variable creada para el método nextStep
    
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Monster> monsters = new ArrayList<>();
    private final Labyrinth labyrinth;
    
    // =================================================
    // MÉTODOS PÚBLICOS
    // =================================================
    
    /**
     * Crea una partida con {@code nplayers} jugadores. Decide quién empieza, 
     * crea el laberinto y realiza una confifuración mínima (colocar salida y monstruos).
     * @param nplayers número de jugadores
     */
    public Game(int nplayers) {
        // 1) Crear jugadores (ejemplo: letras 'A', 'B', 'C', ...)
        for(int i = 0; i < nplayers; i++){
            char number = (char) ('A' + i);
            float intel = Dice.randomIntelligence();
            float str = Dice.randomStrength();
            players.add(new Player(number, intel, str));
        }
        
        this.log = "";
        
        // 2) Quien empieza
        currentPlayerIndex = Dice.whoStarts(nplayers);
        
        // 3) Crear laberinto (dimensiones y salida de ejemplo)
        int rows = 6, cols = 8, exitRow = 0, exitCol = cols - 1;
        labyrinth = new Labyrinth(rows, cols, exitRow, exitCol);
        
        // 4) Configurar laberinto (bloques/monstruos)
        configureLabyrinth();
        
        // 5) Repartir jugadores (en P3; aquí puede no estar implementado)
        try {
            labyrinth.spreadPlayers(players);
        } catch (UnsupportedOperationException ex) {
            // Nada aún, se completa en P3
        }
     }
    
    /**
     * 
     * @return {@code true} si el juego ha finalizado (hay ganador)
     */
    public boolean finished() {
        return labyrinth.haveAWinner(); // Delega en el laberinto.
    }
    
    /**
     * Fabrica un {@link GameState} con información textual del tablero, 
     * jugadores y monstruos, el jugador actual, si hay ganador y el log.
     * @return  estado observable del juego
     */
    public GameState getGameState() {
        // Laberinto como texto
        String labTxt = labyrinth.toString();
        
        // Listado de jugadores
        StringBuilder ps = new StringBuilder();
        for (Player p : players) ps.append(p).append('\n');
        
        // Listado de monstruos
        StringBuilder ms = new StringBuilder();
        for (Monster m : monsters) ms.append(m).append('\n');
        
        return new GameState(labTxt, ps.toString(), ms.toString(), currentPlayerIndex, finished(), log);
    }
    
    // =========================================
    // P3 - Métodos públicos a implementar
    // =========================================
    /**
     * Ejecuta el siguiente paso (turno) del juego.
     * @param preferredDirection
     * @return Si el juego ha terminado
     */
     public boolean nextStep(Directions preferredDirection) { 
         String log = "";
         
         boolean dead = currentPlayer.dead(); // Diagrama 1.1
         
         // Diagrama alt: [!dead]
         if (!dead) {
             Directions direction = actualDirection(preferredDirection); // Diagrama 1.2: actualDirection(preferredDirection)
             
             // Diagrama opt: [direction != preferredDirection]
             if (direction != preferredDirection) {
                 logPlayerNoOrders(); // Diagrama 1.3: logPlayerNoOrders()
             }
             
             Monster monster = labyrinth.putPlayer(direction, currentPlayer); // Diagrama 1.4: putPlayer(direction, currentPlayer)
             
             // Diagrama alt: [monster == null]
             if (monster == null) {
                logNoMonster(); // Diagrama 1.5: logNoMonster()
             } else {
                 GameCharacter winner = combat(monster); // Diagrama 1.6: combat(monster)
                 
                 manageReward(winner); // Diagrama 1.7: manageReward(winner)
             }
         } else {
             manageResurrection(); // Diagrama 1.8: manageResurrection()
         }
         
         boolean endGame = finished(); // Diagrama 1.9: finished();
         
         // Diagrama opt: [!endGame]
         if (!endGame) {
             nextPlayer(); // Diagrama 1.10: nextPlayer()
         }
         
         return endGame; // Diagrama 1.11: return endGame
     }
    
     
     
     // ========================================
     // MÉTODOS PRIVADOS
     // ========================================
     
    /**
     * Configura el laberinto añadiendo monstruos.
     * Los monstruos se guardan también en el contendor de esta clase.
     */
    private void configureLabyrinth() {
        // Crear algunos monstruos con valores aleatorios razonables
        for (int i = 0; i < 3; i++) {
            Monster m = new Monster("M" + i, Dice.randomIntelligence(), Dice.randomStrength());
            
            // Colocar una posición vacía aleatoria
            int[] pos = labyrinth.randomEmptyPos();
            labyrinth.addMonster(pos[Labyrinth.ROW], pos[Labyrinth.COL], m);
            monsters.add(m);
        }
    }
    
     /**
      * Pasa el turno al siguiente jugador.
      */
    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) %players.size();
    }
    
    // Métodos de log
    private void logPlayerWon() { log += "Jugador ha ganado!\n"; }
    private void logMonsterWon() { log += "Monstruo ha ganado!\n"; }
    private void logResurrected() { log += "Jugador resucitado.\n"; }
    private void logPlayerSkipTurn() { log += "Jugador ha perdido turno (muerto)\n"; }
    private void logPlayerNoOrders() { log += "Jugador no ha recibido ordenes validas\n"; }
    private void logNoMonster() { log += "No hay monstruo en esta casilla / sin movimiento\n"; }
    private void logRounds(int rounds, int max) { log += "Rondas: " + rounds + " / " + max + "\n"; }
    
    // ========================================
    // P3 - Métodos privados a implementar
    // ========================================
    /**
     * Determina la dirección real del movimiento
     * @param preferredDirection
     * @return Dirección final (la deseada, o la primera válida si la deseada no se puede)
     */
    private Directions actualDirection(Directions preferredDirection) { 
        int currentRow = currentPlayer.getRow(); // Diagrama 1.1: getRow()
        int currentCol = currentPlayer.getCol(); // Diagrama 1.2: getCol()
        
        ArrayList<Directions>validMoves = labyrinth.validMoves(currentRow, currentCol); // Diagrama 1.3: validMoves(currentRow, currentCol)
        
        Directions output = currentPlayer.move(preferredDirection, validMoves.toArray(new Directions[0]));  // Diagrama 1.4: move(preferredDirection, validMoves)
        
        return output;
    }
    
    /**
     * Realiza un asalto de combate entre el jugador actual y un monstruo
     * @param monster
     * @return Ganador del combate (PLAYER o MONSTER)
     */
    private GameCharacter combat(Monster monster) { 
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;
        
        float playerAttack = currentPlayer.attack(); // Diagrama 1.1: attack()
        
        boolean lose = monster.defend(playerAttack); // Diagrama 1.2: defend(playerAttack)
        
        // Diagrama opt: [(!lose) && (rounds < MAX_ROUNDS)]
        if (!lose && rounds < MAX_ROUNDS) {
            winner = GameCharacter.MONSTER; 
            rounds++;
            
            float monsterAttack = monster.attack(); // Diagrama 1.3: attack()
            
            lose = currentPlayer.defend(monsterAttack); // Diagrama 1.4: defend(monsterAttack)
            
            // Diagrama opt: [!lose]
            if (!lose) {
                playerAttack = currentPlayer.attack(); // Diagrama 1.5: attack()
                
                winner = GameCharacter.PLAYER;
                
                lose = monster.defend(playerAttack); // Diagrama 1.6: defend(playerAttack)
            }
        }
        
        logRounds(rounds, MAX_ROUNDS); // Diagrama 1.7: logRounds(rounds, MAX_ROUNDS)
        
        return winner;
    }
    
    /**
     * Gestiona la recompensa (si gana PLAYER) o el log (si gana MONSTER)
     * @param winner 
     */
    private void manageReward(GameCharacter winner) { 
        // Diagrama alt: [winner == GameCharacter.PLAYER]
        if (winner == GameCharacter.PLAYER) {
            currentPlayer.receiveReward(); // Diagrama 1.1: receiveReward()
            
            logPlayerWon(); // Diagrama 1.2: logPlayerWon()
        } else {
            logMonsterWon(); // Diagrama 1.3: logMonsterWon()
        }
    }
    
    /**
     * Gestiona la resurrección de un jugador si está muerto al inicio del turno.
     */
    private void manageResurrection(){ 
        boolean resurrect = Dice.resurrectPlayer(); // Diagrama 1.1: resurrectPlayer()
        
        // Diagrama alt: [resurrect]
        if (resurrect) {
            currentPlayer.resurrect(); // Diagrama 1.2: resurrect()
            
            logResurrected(); // Diagrama 1.3: logResurrected()
        } else {
            logPlayerSkipTurn(); // Diagrama 1.4: logPlayerSkipTurn()
        }
    }
}
