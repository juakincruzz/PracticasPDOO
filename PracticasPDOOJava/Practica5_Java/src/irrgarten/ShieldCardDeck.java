/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author Joaquín Cruz Lorenzo
 */
public class ShieldCardDeck extends CardDeck<Shield> {

    @Override
    protected void addCards() {
        // Añade aquí los escudos que quieras
        addCard(new Shield(2.0f, 3));
        addCard(new Shield(1.0f, 5));
        addCard(new Shield(3.0f, 1));
    }
}
