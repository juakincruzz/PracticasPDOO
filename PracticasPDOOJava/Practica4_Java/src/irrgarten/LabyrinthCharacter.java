/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;

/**
 * Representa la base abstracta para cualquier personaje (jugador o monstruo)
 * que se mueve e interactúa en el laberinto.
 * @author Joaquín Cruz Lorenzo
 */
public abstract class LabyrinthCharacter {
    // Atributos
    private String name;
    private float intelligence, strength, health;
    private int row, col;

    /**
     * Constructor principal de un personaje.
     * @param name              Nombre del personaje
     * @param intelligence   Inteligencia del personaje
     * @param strength        Fuerza del personaje
     * @param health           Salud del personaje 
     */
    public LabyrinthCharacter(String name, float intelligence, float strength, float health) {
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = health;
        this.row = -1; // Posición inicial inválida
        this.col = -1;  // Posición inicial inválida
    }
    
    /**
     * Constructor de copia
     * Crea un personaje copiando los atributos.
     * @param other Personaje a copiar
     */
    public LabyrinthCharacter(LabyrinthCharacter other) {
        this(other.name, other.intelligence, other.strength, other.health);
        this.row = other.row;
        this.col = other.col;
    }
    
    /**
     * Compueba si el personaje está muerto.
     * @return true Sí la salud es 0 o menor. 
     */
    public boolean dead() {
        return this.health <= 0;
    }

    /**
     * Devuelve la fila actual del personaje.
     * @return La fila
     */
    public int getRow() {
        return row;
    }

    /**
     * Devuelve la columna actual del personaje.
     * @return La columna
     */
    public int getCol() {
        return col;
    }

    /**
     * Devuelve la inteligencia. 
     * @return La inteligencia
     */
    protected float getIntelligence() {
        return intelligence;
    }

    /**
     * Devuelve la fuerza.
     * @return La fuerza
     */
    protected float getStrength() {
        return strength;
    }

    /**
     * Devuelve la salud.
     * @return La salud 
     */
    protected float getHealth() {
        return health;
    }

    /**
     * Establece la salud del personaje.
     * @param health Nuevo valor de salud
     */
    protected void setHealth(float health) {
        this.health = health;
    }
    
    /**
     * Establece la posición del personaje en el laberinto.
     * @param row La nueva fila
     * @param col La nueva columna
     */
    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    /**
     * Aplica daño al personaje
     * La salud no puede bajar de 0.
     */
    protected void gotWounded() {
        this.health--;
        
        if (this.health < 0)
            this.health = 0;
    }
    
    /**
     * Calcula la intensidad del ataque del personaje.
     * @return El valor del ataque
     */
    public abstract float attack();
    
    /**
     * Gestiona la defensa del personaje ante un ataque recibido
     * @param attack receivedAttack El valor del ataque recibido
     * @return true si el personaje ha muerto tras el ataque
     */
    public abstract boolean defend(float receivedAttack); // Aquí no sería receivedAttack?
}
