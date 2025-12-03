/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package irrgarten.UI;

import irrgarten.Directions;
import irrgarten.GameState;

/**
 *
 * @author Joaquín Cruz Lorenzo
 */
public interface UI {
    
    public void showGame(GameState gameState);
    
    public Directions nextMove();
    
}