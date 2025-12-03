/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten.UI;

import irrgarten.Directions;
import irrgarten.GameState;
import java.util.Scanner;


/**
 * Representa la Vista (Interfaz de Usuario textual) del juego.
 * Es la única clase que interactúa con el usuario
 * @author Joaquín Cruz Lorenzo
 */
public class TextUI implements UI {
    // Atributo para leer de la consola
    private Scanner in = new Scanner(System.in);
    
    /**
     * Muestra el estado actual del juego en la consola.
     * @param gameState 
     */
    public void showGame(GameState gameState) {
        // Imprimo el estado del laberinto
        System.out.println("==================================================");
        System.out.println("--- LABERINTO ---");
        System.out.println(gameState.getLabyrinth());
        
        // Imprimo el estado de los jugadores
        System.out.println("==================================================");
        System.out.println("\n--- JUGADORES ---");
        System.out.println(gameState.getPlayers());
        
        // Imprimo el estado de los monstruos
        System.out.println("==================================================");
        System.out.println("\n--- MONSTRUOS ---");
        System.out.println(gameState.getMonsters());
        
        // Imprimo el log de eventos
        System.out.println("==================================================");
        System.out.println("\n--- LOG ---");
        System.out.println(gameState.getLog());
        
        // Indicamos el turno
        System.out.println("\nTurno del jugador: " + gameState.getCurrentPlayer());
        
        // Si hay un jugador, lo anunciamos
        if (gameState.isWinner()) {
            System.out.println("\n¡¡¡ EL JUEGO HA TERMINADO !!!");
            System.out.println("El jugador " + gameState.getCurrentPlayer() + " ha ganado! Felicidades :)");
        }
        
        System.out.println("==================================================");
    }
    
    /**
     * Lee un movimiento del jugador de forma robusta
     * @return La dirección elegida por el usuario
     */
    public Directions nextMove() {
       System.out.println("Elige un movimiento (w:UP, a:LEFT, s:DOWN, d:RIGHT): ");
       boolean goInput = false;
       String c = "";
       
       // Bucle robusto para asegurar una entrada válida (w, a, s, d)
       while(!goInput) {
           c = in.nextLine(); // Lee la línea entera
           
           if (c.equalsIgnoreCase("w") || c.equalsIgnoreCase("a") || c.equalsIgnoreCase("s") || c.equalsIgnoreCase("d")) {
               goInput = true;
           } else {
               System.out.println("La entrada es incorrecta. Introduzca w, a, s o d: ");
           }
       }
       
       // Convierto las letras en las direcciones
       switch (c.toLowerCase()) {
           case "w": 
               return Directions.UP;
           case "a":
               return Directions.LEFT;
           case "s":
               return Directions.DOWN;
           case "d":
               return Directions.RIGHT;
           default:
               // Esto es innaccesible gracias al bucle, pero Java lo requiere
               return Directions.UP;
       }
    }
}
