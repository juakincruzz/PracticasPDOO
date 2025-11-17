/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author Joaquín Cruz Lorenzo
 */
public class Weapon extends CombatElement {
    // ELIMINO LOS ATRIBUTOS 'power' y 'uses'
    // ya que están en la superclase
    // Atributos privados.
    // private float power;    --> ELIMINADO
    // private int uses;           --> ELIMINADO
    
    // Modifico ahora el constructor..
    public Weapon(float power, int uses) {
        // Llamo al constructor en la superclase (CombatElement)
        // 'power' se convierte en 'effect' de la superclase
        super(power, uses);
    }
    
    public Weapon(Weapon other) {
        super(other.getEffect(), other.getUses());
    }
    
    // Método de ataque que devuelva un número en coma flotante (float), público
    // Delego en 'produceEffect'
    public float attack(){
        // Uso el método de la superclase
        return produceEffect();
    }
    
    // ELIMINO EL MÉTODO discard()
    // Ya que NO es necesario, lo hereda de CombatElement.
    // public boolean discard() {
    //      return Dice.discardElement(uses);
    // }
    
    @Override
    public String toString() {
        return "W[" + "power=" + getEffect() + ", uses=" + getUses() + ']';
    }
}
