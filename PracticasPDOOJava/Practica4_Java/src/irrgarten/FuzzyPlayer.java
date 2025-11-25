/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;

/**
 *
 * @author Joaquín Cruz Lorenzo
 */
public class FuzzyPlayer extends Player {

    /**
     * Constructor: Crea un FuzzyPlayer a partir de otro Player (resurrección)
     * Copia su estado (nombre, inteligencia, fuerza, etc.) y sus pertenencias.
     * @param other El jugador original que ha muerto
     */
    public FuzzyPlayer(Player other) {
        super(other);
    }
    
    /**
     * Movimiento del FuzzyPlayer
     * 1) Calcula hacia donde se movería un jugador normal (preferencia)
     * 2) Pide al dado que decida si respeta esta preferencia o mueve al azar.
     */
    @Override
    public Directions move(Directions direction, Directions[] validMoves) {
        // 1. Calculo la dirección preferida usando la lógica del padre (Player)
        Directions preference = super.move(direction, validMoves);
        
        // 2. Convierto el array a ArrayList para el método Dice
        // Dice.nextStep espera un ArrayList, así que hago la conversión.
        ArrayList<Directions> validMovesList = new ArrayList<>();
        for (Directions d : validMoves) {
            validMovesList.add(d);
        }
        
        // 3. Uso el método nuevo de Dice
        return Dice.nextStep(preference, validMovesList, getIntelligence());
    }
    
    /**
     * Ataque del FuzzyPlayer
     * Suma de armas + Dice.intensity(fuerza).
     */
    @Override 
    public float attack() {
        // Nota: Necesito acceder a sumWeapons().
        return sumWeapons() + Dice.intensity(getStrength());
    }
}
