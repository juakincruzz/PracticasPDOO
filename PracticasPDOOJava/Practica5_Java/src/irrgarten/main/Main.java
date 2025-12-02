/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten.main;

// Importo las clases de los otros paquetes
import irrgarten.Game;
import irrgarten.Controller.Controller; 
import irrgarten.UI.TextUI;

/**
 * Programa principal que inicia el juego.
 * Su única responsabilidad es instanciar el Modelo, la Vista y el Controlador.
 * Y arrancar el juego.
 * @author Joaquín Cruz Lorenzo
 */
public class Main {
    /**
     * Punto de entrada de la aplicación
     * @param args 
     */
    public static void main(String[] args) {
        // 1. Crear el modelo (el juego con 2 jugadores, por ejemplo)
        Game game = new Game(1); 
        
        // 2. Crear la vista (la interfaz textual)
        // Asegurar de que el nombre del paquete UI coincide.
        TextUI ui = new TextUI();
        
        // 3. Crear el Controlador (y pasarle el Modelo y la Vista)
        // Asegurar de que el nombre del paquete Controller coincide.
        Controller controller = new Controller(game, ui);
        
        // 4. Arrancar el juego (el controlador toma el control)
        controller.play();
    }
}
