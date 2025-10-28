/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author Joaquín Cruz Lorenzo
 */
public class Weapon {
    // Atributos privados.
    private float power;
    private int uses;
    
    // Constructor.
    public Weapon(float power, int uses) {
        this.power = power;
        this.uses = uses;
    }
    
    // Metodo de ataque que devuelva un numero en coma flotante (float), publico
    public float attack(){
        if (uses > 0){
            uses--;
            return power;
        }
        return 0.0f;
    }

    @Override
    public String toString() {
        return "W[" + "power=" + power + ", uses=" + uses + ']';
    }
    
    public boolean discard() {
        return Dice.discardElement(uses);
    }
}
