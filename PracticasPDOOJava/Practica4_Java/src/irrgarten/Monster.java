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
    private static final int INITIAL_HEALTH = 5;
    
    // ===============================
    // ATRIBUTOS DE INSTANCIA
    // ===============================
    private String name;
    private float intelligence, strength, health;
    private int row = -1, col = -1; // No colocado inicialmente.
    
    // =================================
    // MÉTODOS PÚBLICOS
    // =================================
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
        this.health = INITIAL_HEALTH;
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
     * Cambia la posición del monstruo en el laberinto.
     * @param row fila en la que se colocará
     * @param col columna en la que se colocará
     */
    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    @Override
    public String toString() { 
        return "Monster{" + "name= " + name + ", intelligence= " + intelligence + ", strength= " + strength + ", health= " + health + ", row= " + row + ", col= " + col + '}';
    }
    
    // ======================================
    // P3 - Métodos públicos a implementar
    // ======================================
    /**
     * Gestiona la defensa del monstruo ante un ataque.
     * <p>
     * Si el monstruo está vivo, calcula su defensa (con Dice.intensity).
     * Si el ataque supera a la defensa, recibe danio (gotWounded).
     * </p>
     * @param receivedAttack Ataque recibido
     * @return true si el monstruo muere a causa del ataque o ya estaba muerto,
     * false en caso contrario.
     */
    public boolean defend(float receivedAttack){ 
        boolean isDead = this.dead(); // Diagrama 1.1: dead()
        
        // Diagrama opt: [!isDead]
        if (!isDead) {
            float defensiveEnergy = Dice.intensity(intelligence); // Diagrama 1.2: intensity(intelligence)
            
            // Diagrama opt: [defensiveEnergy < receivedAttack]
            if (defensiveEnergy < receivedAttack) {
                gotWounded(); // Diagrama 1.3: gotWounded()
                isDead = dead(); // Diagrama 1.4: dead()
            }
        }
        // Devuelve true si estaba muerto o si ha muerto ahora, 
        // false si ha sobrevivido al golpe
        return isDead;
    }
    
    
    // ======================================
    // MÉTODOS PRIVADOS
    // ======================================
    
    /**
     * Resta un punto de salud al monstruo.
     */
    private void gotWounded() { health--; }
}