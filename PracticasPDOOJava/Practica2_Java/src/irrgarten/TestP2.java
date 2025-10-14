package irrgarten;

import java.util.Arrays;

/**
 * Test para la Práctica 2, centrado en la interfaz pública de las clases.
 * @author Joaquin Cruz Lorenzo
 */
public class TestP2 {
    public static void main(String[] args) {
        
        System.out.println("========================================");
        System.out.println("=== PRUEBAS DE LA CLASE MONSTER ===");
        System.out.println("========================================");
        
        Monster monster = new Monster("M1", 8.5f, 9.0f);
        System.out.println("Monstruo recién creado: " + monster);
        System.out.println("¿Está muerto?: " + monster.dead());
        System.out.println("Herido 2 veces: " + monster);
        System.out.printf("Ataque del monstruo: %.2f%n", monster.attack());
        monster.setPos(3, 4);
        System.out.println("Posición actualizada: " + monster);
        System.out.println();
        
        System.out.println("========================================");
        System.out.println("=== PRUEBAS DE LA CLASE PLAYER ===");
        System.out.println("========================================");

        Player player = new Player('1', 7.2f, 6.8f);
        System.out.println("Jugador recién creado: " + player);
        System.out.println("¿Está muerto?: " + player.dead());
        System.out.printf("Ataque (sin armas): %.2f%n", player.attack());
        player.setPos(1, 1);
        player.gotWounded();
        System.out.println("Jugador herido una vez: " + player);
        player.resurrect();
        System.out.println("Jugador resucitado: " + player);
        System.out.println();
        
        System.out.println("========================================");
        System.out.println("=== PRUEBAS DE LA CLASE LABYRINTH ===");
        System.out.println("========================================");
        
        Labyrinth lab = new Labyrinth(5, 6, 4, 5);
        System.out.println("--- Laberinto Inicial ---");
        System.out.println(lab);
        System.out.println("¿Hay ganador?: " + lab.haveAWinner());
        
        System.out.println("Añadiendo monstruo a una posición aleatoria...");
        int[] randomPos = lab.randomEmptyPos();
        lab.addMonster(randomPos[Labyrinth.ROW], randomPos[Labyrinth.COL], monster);
        System.out.println("Monstruo añadido en " + Arrays.toString(randomPos));
        System.out.println("--- Laberinto con Monstruo ---");
        System.out.println(lab);
        System.out.println();
        
        System.out.println("========================================");
        System.out.println("=== PRUEBAS DE LA CLASE GAME ===");
        System.out.println("========================================");

        Game game = new Game(2);
        System.out.println("Partida creada con 2 jugadores.");
        System.out.println();

        System.out.println("--- Estado Inicial del Juego ---");
        GameState initialState = game.getGameState();
        System.out.println("Laberinto:\n" + initialState.getLabyrinth());
        System.out.println("Jugadores:\n" + initialState.getPlayers());
        System.out.println("Monstruos:\n" + initialState.getMonsters());
        System.out.println("Jugador Actual: " + initialState.getCurrentPlayer());
        System.out.println("¿Hay ganador?: " + initialState.isWinner());
        System.out.println("Log: '" + initialState.getLog() + "'");
        System.out.println();
        System.out.println("¿Fin del juego?: " + game.finished());
    }
}