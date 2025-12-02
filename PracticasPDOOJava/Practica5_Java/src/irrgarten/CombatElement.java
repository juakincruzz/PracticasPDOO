/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author Joaquín Cruz Lorenzo
 */
public abstract class CombatElement {
    // Atributos
    private float effect;
    private int uses;

    // Constructor.
    public CombatElement(float effect, int uses) {
        this.effect = effect;
        this.uses = uses;
    }
    
    protected float produceEffect() {
        if (getUses() > 0) {
            this.uses--;
            return getEffect();
        } else return 0;   
    }

    /**
     * Devuelve el efecto base del elemento.
     * (Protected para que solo las clases hijas puedan verlo)
     * @return el valor del efecto
     */
    public float getEffect() {
        return effect;
    }

    /**
     * Devuelve los usos restantes.
     * (Protected para que solo las clases hijas puedan verlo)
     * @return los usos restantes
     */
    public int getUses() {
        return uses;
    }
    
    public boolean discard() {
        return Dice.discardElement(this.uses);
    }
    
    @Override
    public abstract String toString();
}
