/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author Joaquín Cruz Lorenzo
 */
public class Shield {
    // Atributos de instancia privados
    private float protection;
    private int uses;
    
    // Constructor
    public Shield(float protection, int uses) {
        this.protection = protection;
        this.uses = uses;
    }
    
    public float protect(){
        if (uses > 0){
            uses--;
            return protection;
        }
        
        return 0.0f;
    }

    @Override
    public String toString() {
        return "S[" + "protection=" + protection + ", uses=" + uses + ']';
    }
    
    public boolean discard() {
        return Dice.discardElement(uses);
    }
}
