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
public class Monster extends LabyrinthCharacter {
    // ===============================
    // CONSTANTE DE CLASE
    // ===============================
    
    // Salud inicial por defecto para todos los monstruos.
    private static final int INITIAL_HEALTH = 5;
    
    // ELIMINO todos los atributos de instancia repetidos. 
    // Ya que los usamos en LabyrinthCharacter
    // ===============================
    // ATRIBUTOS DE INSTANCIA
    // ===============================
    // private String name;
    // private float intelligence, strength, health;
    // private int row = -1, col = -1; // No colocado inicialmente.
    
    // =================================
    // MÉTODOS PÚBLICOS
    // =================================
    
    /**
     * Constructor del Monstruo
     * Llama al constructor de la superclase con sus valores específicos.
     */
    public Monster(String name, float intelligence, float strength) {
        super(name, intelligence, strength, INITIAL_HEALTH);
    }
    
    /**
     * Calcula la potencia de ataque del monstruo.
     * <p> 
     * Se obtiene aplicando {@link Dice#intensity(float)} a su fuerza.
     * </p>
     * @return intensidad del ataque
     */
    public float attack() {
        return Dice.intensity(getStrength());
    }
    
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
            float defensiveEnergy = Dice.intensity(getIntelligence()); // Diagrama 1.2: intensity(intelligence)
            
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
    
    @Override
    public String toString() { 
        return "Monster{" + super.toString() + "}";
    }
    
    // LISTA DE MÉTODOS SUPRIMIDOS YA QUE SE USAN AHORA EN LabyrinthCharacter
    // public boolean dead() { return health <= 0; }
    // public void setPos(int row, int col) {
    //    this.row = row;
    //    this.col = col;
    // }
   // private void gotWounded() { health--; }
}