package irrgarten;

import java.util.ArrayList;

/**
 * Representa un jugador dentro del laberinto
 * <p>
 * Cada jugador posee características propias como inteligencia, fuerza y salud,
 * además de una posición (row/column) dentro del {@link labyrinth}.
 * Puede equipar un número de limitado de armas y escudos, atacar, defenderse y 
 * resucitar tras ser derrotado.
 * </p>
 * 
 * @author Joaquín Cruz Lorenzo
 */
public class Player {
    // ==================================
    // CONSTANTES DE CLASE
    // ==================================
    public static int MAX_WEAPONS = 2;
    public static int MAX_SHIELDS = 3;
    public static int INITIAL_HEALTH = 10;
    public static int HITS2LOSE = 3;
    
    // ==================================
    // ATRIBUTOS DE INSTANCIA 
    // ==================================
    private String name;
    private char number;
    private float intelligence, strength, health;
    private int row, col, consecutiveHits = 0;
    
    private ArrayList<Weapon> weapons = new ArrayList();
    private ArrayList<Shield> shields = new ArrayList();
    
    /**
     * Constructor
     * @param number
     * @param intelligence
     * @param strength 
     */
    public Player(char number, float intelligence, float strength) {
        this.number = number;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = INITIAL_HEALTH;
        this.row = -1;
        this.col = -1;
        this.name = "Player #" + number;
    }
    
    // ==================================
    // MÉTODOS CONSULTORES
    // ==================================

    /**
     * 
     * @return fila actual del jugador 
     */
    public int getRow() {
        return row;
    }
    
    /**
     * 
     * @return columna actual del jugador
     */
    public int getCol() {
        return col;
    }
    
    /**
     * 
     * @return número del jugador 
     */
    public char getNumber() {
        return number;
    }
    
    /**
     * Restaura al jugador al estado inicial.
     */
    public void resurrect() {
        weapons.clear();
        shields.clear();
        health = INITIAL_HEALTH;
        consecutiveHits = 0;
    }
    
    /**
     * Cambia la posicion del jugador dentro del laberinto.
     * @param row
     * @param col 
     */
    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    /**
     * 
     * @return true si el jugador está muerto, false en caso contrario.
     */
    public boolean dead() {
       return health <= 0;
    }
    
    public Directions move(Directions direction, Directions[] validMoves){
        throw new UnsupportedOperationException();
    }
    
    public float attack() {
        return strength + sumWeapons();
    }
    
    public boolean defend(float receivedAttack) {
        throw new UnsupportedOperationException();
    }
    
    public void receiveReward() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "Player{" + "name= " + name + ", number= " + number + ", intelligence=" + intelligence + ", strength= " + strength + ", health= " + health + 
                ", row= " + row + ", col= " + col + ", consecutiveHits= " + consecutiveHits + ", weapons= " + weapons + ", shields= " + shields + '}';
    }
    
    private void receiveWeapon(Weapon w) {
        throw new UnsupportedOperationException();
    }
    
    private void receiveShields(Shield s) {
        throw new UnsupportedOperationException();
    }
    
    private Weapon newWeapon() {
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }
    
    private Shield newShield() {
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }
    
    private float sumWeapons() {
        float total = 0.0f;
        
        for (Weapon w : weapons) total += w.attack();
        
        return total;
    }
    
    private float sumShields() {
        float total = 0.0f;
        
        for (Shield s : shields) total += s.protect();
        
        return total;
    }
    
    private float defensiveEnergy() {
        return intelligence + sumShields();
    }
    
    private boolean manageHit(float receivedAttack) {
        throw new UnsupportedOperationException();
    }
    
    private void resetHits() {
        consecutiveHits = 0;
    }
    
    private void gotWounded() {
        health--;
    }
    
    private void incConsecutiveHits() {
        consecutiveHits++;
    }
}
