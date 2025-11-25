/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author Joaquín Cruz Lorenzo
 */
public class WeaponCardDeck extends CardDeck<Weapon> {
    
    @Override
    protected void addCards() {
        // Añade aquí las armas que quieras para tu juego
        addCard(new Weapon(2.0f, 3));
        addCard(new Weapon(1.0f, 5));
        addCard(new Weapon(3.0f, 1));
        addCard(new Weapon(1.5f, 4));
    }
}
