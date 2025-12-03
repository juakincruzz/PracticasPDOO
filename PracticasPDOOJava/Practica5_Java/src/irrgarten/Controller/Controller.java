/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten.Controller;

import irrgarten.Directions;
import irrgarten.Game;
import irrgarten.UI.UI;

/**
 * Clase controladora que coordina el Modelo (Game) y la Vista (TextUI).
 * Contiene el bucle principal del juego.
 * @author Joaquín
 */
public class Controller {
    // --- Atributos (asociaciones del diagrama) ---
    private Game game;
    private UI ui;

    /**
     * Constructor que recibe e incializa el modelo y la vista.
     * @param game (Estado del juego)
     * @param ui   (Interfaz de usuario)
     */
    public Controller(Game game, UI ui) {
        this.game = game;
        this.ui = ui;
    }
    
    /**
     * Inicia y ejecuta el bucle principal del juego.
     * Este método no termina hasta que el juego haya finalizado
     */
    public void play() {
        boolean endGame = false;
        
        // Bucle principal del juego: se ejecuta mientras el juego no haya terminado.
        while (!endGame) {
            // 1. Mostrar el estado actual del juego
            ui.showGame(game.getGameState());
            
            // 2. Pedir al jugador el siguiente movimiento
            Directions direction = ui.nextMove();
            
            // 3. Ejecutar el siguiente paso (turno) en el modelo
            endGame = game.nextStep(direction);
        }
        
        // 4. Fuera del bucle: el juego ya ha terminado.
        // Muestro el estado final (Pantalla de ganador)
        ui.showGame(game.getGameState());
    }
}
