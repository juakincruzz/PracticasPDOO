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
    
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Monster> monsters = new ArrayList<>();
    private final Labyrinth labyrinth;
    
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
        
        // 2) Quien empieza
        currentPlayerIndex = Dice.whoStarts(nplayers);
        
        // 3) Crear laberinto (dimensiones y salida de ejemplo)
        int rows = 6, cols = 8, exitRow = 0, exitCol = cols - 1;
        labyrinth = new Labyrinth(rows, cols, exitRow, exitCol);
        
        // 4) Configurar laberinto (bloques/monstruos)
        configureLabyrinth();
        
        // 5) Repartir jugadores (en P3; aquí puede no estar implementado)
        try {
            labyrinth.spreadPlayers(players.toArray(new Player[0]));
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
            int var = 2;
        }
    }
    
    // ========================================
    // P3
    // ========================================
    public boolean nextStep(Directions preferredDirection) {
        throw new UnsupportedOperationException();
    }
    
    private Directions actualDirection(Directions preferredDirection) {
        throw new UnsupportedOperationException();
    }
    
    private GameCharacter combat(Monster monster) {
        throw new UnsupportedOperationException();
    }
    
    private void manageReward(GameCharacter winner) {
        throw new UnsupportedOperationException();
    }
    
    private void manageResurrection(){
        throw new UnsupportedOperationException();
    }
    
}
