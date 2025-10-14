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
    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELDS = 3;
    private static final int INITIAL_HEALTH = 10;
    private static final int HITS2LOSE = 3;
    
    // ==================================
    // ATRIBUTOS DE INSTANCIA 
    // ==================================
    private String name;
    private char number;
    private float intelligence, strength, health;
    private int row, col, consecutiveHits = 0;
    
    private ArrayList<Weapon> weapons = new ArrayList();
    private ArrayList<Shield> shields = new ArrayList();
    
    // ===============================================
    // MÉTODOS PÚBLICOS
    // ===============================================
    
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
    
    /**
     * Restaura al jugador al estado inicial.
     * <ul>
     *          <li> Vacía armas y escudos. </li>
     *          <li> Reinicia salud e impactos consecutivos. </li>
     * </ul>
     */
    public void resurrect() {
        weapons.clear();
        shields.clear();
        health = INITIAL_HEALTH;
        consecutiveHits = 0;
    }

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
     * Cambia la posicion del jugador dentro del laberinto.
     * @param row
     * @param col 
     */
    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    /**
     * Indica si el jugador está muerto (salud menor o igual a 0).
     * @return true si el jugador está muerto, false en caso contrario.
     */
    public boolean dead() {
       return health <= 0;
    }
    
    /**
     * Calucla la potencia de ataque del jugador.
     * <p> La fórmula básica es {@code strength + sumWeapons()} </p>
     * @return  potencia de ataque total
     */
    public float attack() {
        return strength + sumWeapons();
    }
    
    /**
     * Inicia la defensa ante un ataque recibido.
     * <p> Lo dejamos sin implementar en P2. Se nos dará información en P3 </p>
     * @param receivedAttack intensidad del ataque recibido
     * @return 
     */
    public boolean defend(float receivedAttack) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public String toString() {
        return "Player{" + "name= " + name + ", number= " + number + ", intelligence=" + intelligence + ", strength= " + strength + ", health= " + health + 
                ", row= " + row + ", col= " + col + ", consecutiveHits= " + consecutiveHits + ", weapons= " + weapons + ", shields= " + shields + '}';
    }
    
    // ==================================
    // P3 - Métodos públicos a implementar
    // ==================================
    public Directions move(Directions direction, Directions[] validMoves){ throw new UnsupportedOperationException(); }
    public void receiveReward() { throw new UnsupportedOperationException(); }
    
    
    
    // ==========================================================
    // MÉTODOS PRIVADOS
    // ==========================================================
    /**
     * Suma la potencia de todas las armas equipadas (consumiendo sus usos)
     * @return suma total de ataque de armas activas
     */
    private float sumWeapons() {
        float total = 0.0f;
        
        for (Weapon w : weapons) total += w.attack();
        
        return total;
    }
    
    /**
     * Suma la capacidad de protección de todos los escudos (consumiendo sus usos)
     * @return  protección total ofrecida por los escudos
     */
    private float sumShields() {
        float total = 0.0f;
        
        for (Shield s : shields) total += s.protect();
        
        return total;
    }
    
    /**
     * Calcula la energía defensiva del jugador.
     * <p> {@code defensiveEnergy = intelligence + sumShields()} </p>
     * @return  valor de energía defensiva
     */
    private float defensiveEnergy() {
        return intelligence + sumShields();
    }
    
    /**
     * Incrementa el número de impactos consecutivos sufridos.
     */
    private void incConsecutiveHits() {
        consecutiveHits++;
    }
    
    /**
     * Reinicia el contador de impactos consecutivos a 0.
     */
    private void resetHits() {
        consecutiveHits = 0;
    }
    
    /**
     * Resta un punto de salud al jugador.
     */
    private void gotWounded() {
        health--;
    }
    
    /**
     * Genera un arma nueva con valores aleatorios dados por {@link Dice}.
     * @return  nueva instancia de {@link Weapon}
     */
    private Weapon newWeapon() {
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }
    
    /**
     * Genera un escudo nuevo con valores aleatorios dados por {@link Dice}.
     * @return nueva instancia de {@link Shield}
     */
    private Shield newShield() {
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }
    
    // =========================================
    // P3 - Métodos privados a implementar
    // =========================================
    private void receiveWeapon(Weapon w) { throw new UnsupportedOperationException(); }
    private void receiveShield(Shield s) { throw new UnsupportedOperationException(); }
    private boolean manageHit(float receivedAttack) { throw new UnsupportedOperationException(); }
}
