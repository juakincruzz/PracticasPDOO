package irrgarten;

import java.util.ArrayList;
import java.util.Iterator; // Nueva implementaciónń

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
        // Diagrama 1.1: manageHit(received attack)
        return manageHit(receivedAttack);
    }
    
    @Override
    public String toString() {
        return "Player{" + "name= " + name + ", number= " + number + ", intelligence=" + intelligence + ", strength= " + strength + ", health= " + health + 
                ", row= " + row + ", col= " + col + ", consecutiveHits= " + consecutiveHits + ", weapons= " + weapons + ", shields= " + shields + '}';
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
        
        // Diagrama health += extraHealth
        health += extraHealth;
    }
    
    
    
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
}
