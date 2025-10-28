/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.Random;

/**
 *
 * @author Joaquín Cruz Lorenzo
 */
public final class Dice {
    // Atributos de clase privados.
    private static final int MAX_USES = 5; // Numero maximo de usos de armas y escudos.
    private static final float MAX_INTELLIGENCE = 10.0f; // Valor maximo para la inteligencia de jugadores y monstruos.
    private static final float MAX_STRENGTH = 10.0f; // Valor maximo de fuerza de jugadores y monstruos.
    private static final double RESURRECT_PROB = 0.3; // probabilidad de que un jugador sea resucitado en cada turno.
    private static final int WEAPONS_REWARD = 2; // Numero maximo de armas recibidas al ganar un combate.
    private static final int SHIELDS_REWARD = 3; // Numero maximo de escudos recibidos al ganar un combate.
    private static final int HEALTH_REWARD = 5; // Numero maximo de unidades de salud recibidas al ganar un combate.
    private static final int MAX_ATTACK = 3; // Maxima potencia de ataque de las armas.
    private static final int MAX_SHIELD = 2; // Maxima potencia de los escudos.
    
    // Generador de numeros aleatorios
    private static final Random generator = new Random();

    // Constructor privado: Clase estatica (no instanciable)
    private Dice() {}
    
    // Metodos aleatorios
    public static int randomPos(int max) {
        return generator.nextInt(max); // [0, max)
    }
    
    public static int whoStarts(int nPlayers) {
        return generator.nextInt(nPlayers); // [0, nPlayers)
    }
    
    public static float randomIntelligence() {
        return generator.nextFloat() * MAX_INTELLIGENCE; // [0, 10)
    }
    
    public static float randomStrength() {
        return generator.nextFloat() * MAX_STRENGTH; // [0, 10)
    }
    
    public static boolean resurrectPlayer() {
        return generator.nextDouble() < RESURRECT_PROB; // 30%
    }
    
    public static int weaponsReward() {
        return generator.nextInt(WEAPONS_REWARD + 1) ; // [0, 2]
    }
    
    public static int shieldsReward() {
        return generator.nextInt(SHIELDS_REWARD + 1); // [0, 3]
    }
    
    public static int healthReward() {
        return generator.nextInt(HEALTH_REWARD + 1); // [0, 5]
    }
    
    public static float weaponPower() {
        return generator.nextFloat() * MAX_ATTACK; //[0, 3)
    }
    
    public static float shieldPower() {
        return generator.nextFloat() * MAX_SHIELD; //[0, 2)
    }
    
    public static int usesLeft() {
        return generator.nextInt(MAX_USES + 1); // [0, 5]
    }
    
    public static float intensity(float competence) {
        return generator.nextFloat() * competence; // [0, competence)
    }
    
    public static boolean discardElement(int usesLeft) {
        float p = 1.0f - (usesLeft / (float) MAX_USES);
        
        return generator.nextFloat() < p;
    }
}
