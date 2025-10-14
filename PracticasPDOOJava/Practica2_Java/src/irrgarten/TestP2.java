package irrgarten;

import java.util.Arrays;

/**
 *
 * @author Joaquin Cruz Lorenzo
 */
public class TestP2 {
    public static void main(String[] args) {
        
        // --- 2. Pruebas de la clase Monster ---
        System.out.println("========================================");
        System.out.println("=== PRUEBAS DE LA CLASE MONSTER ===");
        System.out.println("========================================");
        
        // Creación de un monstruo
        Monster monster = new Monster("Drácula", 8.5f, 9.0f);
        System.out.println("Monstruo recién creado: " + monster);

        // Pruebas de estado
        System.out.println("¿Está muerto?: " + monster.dead()); // Debería ser false
        monster.gotWounded();
        monster.gotWounded();
        System.out.println("Herido 2 veces: " + monster);
        System.out.printf("Ataque del monstruo: %.2f%n", monster.attack()); // Valor aleatorio
        monster.setPos(3, 4);
        System.out.println("Posición actualizada: " + monster);
        System.out.println();
        
        
        // --- 3. Pruebas de la clase Player ---
        System.out.println("========================================");
        System.out.println("=== PRUEBAS DE LA CLASE PLAYER ===");
        System.out.println("========================================");

        // Creación de un jugador
        Player player = new Player('1', 7.2f, 6.8f);
        System.out.println("Jugador recién creado: " + player);

        // Pruebas de estado
        System.out.println("¿Está muerto?: " + player.dead()); // Debería ser false
        System.out.printf("Ataque (sin armas): %.2f%n", player.attack());
        // player.defensiveEnergy() es privado, no se puede probar directamente
        
        player.setPos(1, 1);
        System.out.println("Posición actualizada: " + player);
        player.gotWounded();
        // player.incConsecutiveHits() es privado, no se puede probar directamente
        System.out.println("Jugador herido: " + player);

        // Prueba de resurrección
        player.resurrect();
        System.out.println("Jugador resucitado: " + player);
        System.out.println();
        
        // --- 4. Pruebas de la clase Labyrinth ---
        System.out.println("========================================");
        System.out.println("=== PRUEBAS DE LA CLASE LABYRINTH ===");
        System.out.println("========================================");
        
        // Creación de un laberinto
        Labyrinth lab = new Labyrinth(5, 6, 4, 5); // 5x6 con salida en (4,5)
        System.out.println("--- Laberinto Inicial ---");
        // El toString() por defecto de Java para arrays anidados no es visual,
        // tendrías que implementarlo de forma personalizada si quieres verlo como en Ruby.
        // System.out.println(lab); // Usará el toString() que implementaste.
        
        // Pruebas de métodos (los métodos de consulta son privados en tu versión Java)
        // System.out.println("¿Hay ganador?: " + lab.haveAWinner()); // haveAWinner es público
        // System.out.println("¿Posición (2,2) es válida?: " + lab.posOK(2, 2)); // posOK es privado
        
        // Añadir un monstruo
        System.out.println("Añadiendo monstruo a una posición aleatoria...");
        int[] randomPos = lab.randomEmptyPos();
        lab.addMonster(randomPos[Labyrinth.ROW], randomPos[Labyrinth.COL], monster);
        System.out.println("Monstruo añadido en " + Arrays.toString(randomPos));
        System.out.println();
        
        
        // --- 5. Pruebas de la clase Game ---
        System.out.println("========================================");
        System.out.println("=== PRUEBAS DE LA CLASE GAME ===");
        System.out.println("========================================");

        // Creación de una partida con 2 jugadores
        Game game = new Game(2);
        System.out.println("Partida creada con 2 jugadores.");
        System.out.println();

        // Obtener y mostrar el estado inicial del juego
        System.out.println("--- Estado Inicial del Juego ---");
        GameState initialState = game.getGameState();
        System.out.println("Laberinto:\n" + initialState.getLabyrinth());
        System.out.println("Jugadores:\n" + initialState.getPlayers());
        System.out.println("Monstruos:\n" + initialState.getMonsters());
        System.out.println("Jugador Actual: " + initialState.getCurrentPlayer());
        System.out.println("¿Hay ganador?: " + initialState.isWinner());
        System.out.println("Log: '" + initialState.getLog() + "'");
        System.out.println();
        System.out.println("¿Fin del juego?: " + game.finished()); // Debería ser false
    }
}