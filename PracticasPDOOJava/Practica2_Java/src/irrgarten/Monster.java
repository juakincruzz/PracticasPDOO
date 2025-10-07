package irrgarten;

/**
 * Representa un monstruo en el laberinto.
 * <p>
 * Tiene nombre, inteligencia, fuerza, salud y una posicion (row/colum)
 * En esta practica realiza ataques y gestiona heridas. Continuacion en P3
 * </p>
 * 
 * @autor Joaquín Cruz Lorenzo
 */
public class Monster {
    public static int INITIAL_HEALTH = 5;
    
    private String name;
    private float intelligence, strength, health;
    private int row = -1, col = -1; // No colocado.
    
    /**
     * Crea un nuevo monstruo sin colocar en el laberinto.
     * 
     * @param name                  nombre del monstruo
     * @param intelligence       nivel de inteligencia del monstruo
     * @param strength            nivel de fuerza
     * @param health               puntos de vida iniciales
     */
    public Monster(String name, float intelligence, float strength) {
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
    }
    
    /**
     * 
     * @return true si el monstruo esta muerto, false en caso contrario.
     */
    public boolean dead() { return health <= 0; }
    
    /**
     * 
     * @return potencia de ataque calculada con Dice.intensity(strength)
     */
    public float attack() {
        return Dice.intensity(strength);
    }
    
    /**
     * 
     * @param receivedAttack
     * @return NADA AUN, CONTINUACION DEL METODO EN LA P3
     */
    public boolean defend(float receivedAttack){
       throw new UnsupportedOperationException(); 
    }
    
    /**
     * Cambia la posición del monstruo en el laberinto.
     * @param row
     * @param col 
     */
    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    /**
     * Resta un punto de salud al monstruo.
     */
    public void gotWounded() { health--; }
    
    // ======================================
    // REPRESENTACIÓN TEXTUAL
    // ======================================
    @Override
    public String toString() { 
        return "Monster{" + "name= " + name + ", intelligence= " + intelligence + ", strength= " + strength + ", health= " + health + ", row= " + row + ", col= " + col + '}';
    }
}