/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author Joaquín Cruz Lorenzo
 */
public class Shield extends CombatElement {
    // ELIMINO LOS ATRIBUTOS 'power' y 'uses'
    // ya que están en la superclase
    // Atributos de instancia privados
    // private float protection;        --> ELIMINADO
    // private int uses;                      --> ELIMINADO
    
    // Modifico ahora el constructor
    public Shield(float protection, int uses) {
        // Llamo al constructor en la superclase (CombatElement)
        // 'protection' se convierte en 'effect' de la superclase
        super(protection, uses);
    }
    
    public Shield(Shield other) {
        super(other.getEffect(), other.getUses());
    }
    
    // Método de ataque que devuelva un número en coma flotante (float), público
    // Delego en 'produceEffect'
    public float protect(){
        // Uso el método de la superclase
        return produceEffect();
    }

    // ELIMINO EL MÉTODO discard()
    // Ya que NO es necesario, lo hereda de CombatElement.
    // public boolean discard() {
   //    return Dice.discardElement(uses);
   // }
    
    @Override
    public String toString() {
        return "S[" + "protection=" + getEffect() + ", uses=" + getUses() + ']';
    }
}
