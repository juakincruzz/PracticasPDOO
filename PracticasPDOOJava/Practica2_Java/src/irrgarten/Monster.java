package irrgarten;

/**
 * Representa un monstruo en el laberinto.
 * <p>
 * Cada monstruo posee atributos básicos (nombre, inteligencia, fuerza y salud),
 * además de una posición (row y column) dentro del {@link labyrinth}
 * </p>
 * 
 * @autor Joaquín Cruz Lorenzo
 */
public class Monster {
    // ===============================
    // CONSTANTE DE CLASE
    // ===============================
    
    // Salud inicial por defecto para todos los monstruos.
    public static int INITIAL_HEALTH = 5;
    
    // ===============================
    // ATRIBUTOS DE INSTANCIA
    // ===============================
    private String name;
    private float intelligence, strength, health;
    private int row = -1, col = -1; // No colocado inicialmente.
    
    // ===============================
    // CONSTRUCTOR
    // ===============================
    
    /**
     * Crea un nuevo monstruo sin colocar en el laberinto.
     * <p> 
     * La posición inicia se establece en (-1, -1) indicando que
     * aún no está colocado en el laberinto.
     * </p>
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
     * Comprueba si el monstruo está muerto.
     * @return true si el monstruo esta muerto, false en caso contrario.
     */
    public boolean dead() { return health <= 0; }
    
    /**
     * Calcula la potencia de ataque del monstruo.
     * <p> 
     * Se obtiene aplicando {@link Dice#intensity(float)} a su fuerza.
     * </p>
     * @return intensidad del ataque
     */
    public float attack() {
        return Dice.intensity(strength);
    }
    
    /**
     * Gestiona la defensa del monstruo frente a un ataque recibido.
     * <p> En P2 no la implemento, la implemento en P3 </p>
     * @param receivedAttack
     * @return 
     */
    public boolean defend(float receivedAttack){
       throw new UnsupportedOperationException(); 
    }
    
    /**
     * Cambia la posición del monstruo en el laberinto.
     * @param row fila en la que se colocará
     * @param col columna en la que se colocará
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