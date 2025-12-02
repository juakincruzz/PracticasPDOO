package irrgarten;

import java.util.ArrayList;
import java.util.Iterator; // Nueva implementación

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
public class Player extends LabyrinthCharacter {
    // ==================================
    // CONSTANTES DE CLASE
    // ==================================
    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELDS = 3;
    private static final int INITIAL_HEALTH = 10;
    private static final int HITS2LOSE = 3;
    
    private char number;
    private int consecutiveHits = 0;
    
    private ArrayList<Weapon> weapons = new ArrayList();
    private ArrayList<Shield> shields = new ArrayList();
    
    // ELIMINO todos los atributos de instancia repetidos. 
    // Ya que los usamos en LabyrinthCharacter
    // ==================================
    // ATRIBUTOS DE INSTANCIA 
    // ==================================
    // private String name; 
    // private float intelligence, strength, health;
    // private int row, col, 
    
    // ===============================================
    // MÉTODOS PÚBLICOS
    // ===============================================
    
    /**
     * Constructor principal del jugador.
     * @param number
     * @param intelligence
     * @param strength 
     */
    public Player(char number, float intelligence, float strength) {
        super ("Player " + number, intelligence, strength, INITIAL_HEALTH);
        this.number = number;
    }
    
    public Player(Player other) {
        // Llamo al constructor de copia de la superclase (LabyrinthCharacter)
        super(other);
        
        // Copiamos los atributos PROPIOS de Player
        this.number = other.number;
        this.consecutiveHits = other.consecutiveHits;
        
        // Copia de armas y escudos
        for (Weapon w : weapons) {
            this.weapons.add(new Weapon(w));
        }
        
        for (Shield s : shields) {
            this.shields.add(new Shield(s));
        }
    }
    
    /**
     * Restaura al jugador al estado inicial.
     * <ul>
     *          <li> Reinicia salud e impactos consecutivos. </li>
     * </ul>
     */
    public void resurrect() {
        setHealth(INITIAL_HEALTH); // setHealth es protected de LabyrinthCharacter.
        this.consecutiveHits = 0;
    }

    public char getNumber() { return number; }

    
    
    /**
     * Calucla la potencia de ataque del jugador.
     * <p> La fórmula básica es {@code strength + sumWeapons()} </p>
     * @return  potencia de ataque total
     */
    public float attack() {
        return getStrength() + sumWeapons();
    }
    
    /**
     * Inicia la defensa ante un ataque recibido.
     * <p> Lo dejamos sin implementar en P2. Se nos dará información en P3 </p>
     * @param receivedAttack intensidad del ataque recibido
     * @return 
     */
    public boolean defend(float receivedAttack) {
        // Diagrama 1.1: manageHit(received attack)
        return manageHit(receivedAttack);
    }
    
    @Override
    public String toString() {
        String baseState = super.toString();
        String playerState = "Hits: " + consecutiveHits + "\n" + " Weapons: " + weapons.toString() + "\n" + " Shields: " + shields.toString();
        
        return baseState + playerState;
    }
    
    // ==================================
    // P3 - Métodos públicos a implementar
    // ==================================
    /**
     * Decide la dirección final del movimiento del jugador
     * <p>
     * Si la dirección preferida es válida, se devuelve.
     * Si no lo es, pero hay movimientos válidos, se devuelve el primero de la lista de no válidos
     * Si no hay movimientos válidos, se devuelve la preferida (aunque sea inválida)
     * </p>
     * @param direction Dirección preferida
     * @param validMoves Array de direcciones válidas ñ
     * @return Dirección final
     */
    public Directions move(Directions direction, Directions[] validMoves){ 
        int size = validMoves.length; // Diagrama 1.1: size()
        
        // Diagrama 1.2: contains(directions)
        boolean contained = false; 
        for (Directions d : validMoves) {
            if (d == direction) {
                contained = true;
                break;
            }
        }
        
        // Diagrama alt: [(size > 0) && (!contained)]
        if (size > 0 && !contained) {
            // Diagrama 1.3: get(0)
            // Diagrama 1.4: firstElement
            return validMoves[0];
        } else {
            // Diagrama 1.5: direction (return)
            return direction;
        }
    }
    
    /**
     * Recibe las recompensas (armas, escudos, salud) al ganar un combate. 
     * Delega en Dice para obtener las cantidades y en los métodos privados 
     * receiveWeapon y receiveShield para gestionarlas
     */
    public void receiveReward() { 
        int wReward = Dice.weaponsReward(); // Diagrama 1.1: weaponsReward
        int sReward = Dice.shieldsReward(); // Diagrama 1.2: shieldsReward
        
        // Diagrama loop: (1, wReward)
        for (int i = 1; i <= wReward; i++){
            Weapon wnew = newWeapon(); // Diagrama 1.3: newWeapon()
            receiveWeapon(wnew); // Diagrama 1.4: receiveWeapon(w: Weapon = wnew)
        }
        
        // Diagrama loop: (1, sReward)
        for (int i = 1; i <= sReward; i++){
            Shield snew = newShield(); // Diagrama 1.5: newShield()
            receiveShield(snew); // Diagrama 1.6: receiveShield(s: Shield = snew)
        }
        
        int extraHealth = Dice.healthReward(); // Diagrama 1.7: healthReward
        
       
        setHealth(getHealth() + extraHealth);
    }
    
    
    
    // ==========================================================
    // MÉTODOS PRIVADOS
    // ==========================================================
    /**
     * Suma la potencia de todas las armas equipadas (consumiendo sus usos)
     * @return suma total de ataque de armas activas
     */
    protected float sumWeapons() {
        float total = 0.0f;
        
        for (Weapon w : weapons) total += w.attack();
        
        return total;
    }
    
    /**
     * Suma la capacidad de protección de todos los escudos (consumiendo sus usos)
     * @return  protección total ofrecida por los escudos
     */
    protected float sumShields() {
        float total = 0.0f;
        
        for (Shield s : shields) total += s.protect();
        
        return total;
    }
    
    /**
     * Calcula la energía defensiva del jugador.
     * <p> {@code defensiveEnergy = intelligence + sumShields()} </p>
     * @return  valor de energía defensiva
     */
    protected float defensiveEnergy() {
        return getIntelligence() + sumShields();
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
    /**
     *  Gestiona la recepción de un arma.
     * <p>
     * Se itera sobre las armas actuales, descartando (eliminando) las que 
     * ya no tengan usos. Si tras la limpia hay espacio (< MAX_WEAPONS),
     * se aniade la nueva arma.
     * </p>
     * @param w Arma a recibir
     */
    private void receiveWeapon(Weapon w) {
        // Diagrama: loop for all
        Iterator<Weapon> it = weapons.iterator();
        
        while (it.hasNext()){
            Weapon wi = it.next(); // Diagrama 1.1: next()
            
            // Diagrama opt [discard]
            if (wi.discard()) { // Diagrama 1.2: discard()
                 it.remove(); // Diagrama 1.3: remove(wi)
            }
        }
        
        // Diagrama 1.4: int size()
        int size = weapons.size();
        
        // Diagrama opt [size < MAX_WEAPONS]
        if(size < MAX_WEAPONS){
            weapons.add(w); // Diagrama 1.5: add(w)
        }
    }
    
    /**
     * Gestiona la recepción de un escudo.
     * <p> 
     * Se itera sobre los escudos actuales, descartando (eliminando) los que ya no tengan usos.
     * Si tras la limpieza hay espacio (<   MAX_SHIELDS), se aniade el nuevo escudo.
     * </p>
     * @param s Escudo a recibir. 
     */
    private void receiveShield(Shield s) { 
        // Diagrama: loop [for all]
        Iterator<Shield> it = shields.iterator();
        
        while(it.hasNext()) {
            Shield si = it.next(); // Diagrama 1.1: next()
            
            // Diagrama opt [discard]
            if(si.discard()){ // Diagrama 1.2: discard()
                it.remove(); // Diagrama 1.3: remove()
            }
        }
        
        // Diagrama 1.4: size()
        int size = shields.size();
        
        // Diagrama opt [size < MAX_SHIELDS]
        if (size < MAX_SHIELDS){
            shields.add(s); // Diagrama 1.5: add(s)
        }
    }
    
    /**
     * Gestiona la lógica de un impacto recibido.
     * <p>
     * Compara la defensa del jugador con el ataque. Si es superado, 
     * recibe danio y acumula impactos. Si resiste, reinicia impactos.
     * Comprueba si el jugador pierde (por vida o por impactos consecutivos)
     * </p>
     * @param receivedAttack Ataque recibido
     * @return true si el jugador pierde, false si sobrevive
     */
    private boolean manageHit(float receivedAttack) { 
         // Diagrama 1.1.1: defensiveEnergy()
        float defense = defensiveEnergy();
        boolean lose = false; // Variable local para gestionar el resultado.
        
        // Diagrama alt: [defense < receivedAttack]
        if (defense < receivedAttack){
            gotWounded() ; // Diagrama 1.1.2: gotWounded()
            incConsecutiveHits(); // Diagrama 1.1.3: incConsecutiveHits()
        } else {
            resetHits();
        }
        
        // Diagrama alt: [((consecutiveHits == HITS2LOSE) || (dead()))]
        if ( (consecutiveHits == HITS2LOSE) || (dead()) ) {
            resetHits(); // Diagrama 1.1.5: resetHits()
            
            // Diagrama lose = true
            lose = true;
        } 
        
        return lose;
    }
    
    // LISTA DE MÉTODOS SUPRIMIDOS YA QUE SE USAN AHORA EN LabyrinthCharacter
    // public int getRow() { return row; }
    // public int getCol() { return col; }
    // public char getNumber() { return number; }
    // public void setPos(int row, int col) {
    //    this.row = row;
    //    this.col = col;
    // }
    // private void gotWounded() { health--; }
    // public boolean dead() { return health <= 0; }
}
