# frozen_string_literal: true

require_relative 'labyrinth_character'
require_relative 'dice'

module Irrgarten
  class Monster < LabyrinthCharacter
    # --- Constantes y Atributos de Lectura ---
    INITIAL_HEALTH = 5

    attr_reader :row, :col

    # ======================================================
    # MÉTODOS PÚBLICOS
    # ======================================================

    # Constructor
    # @param name Nombre del monstruo
    # @param intelligence Nivel de inteligencia del monstruo
    # @param strength Nivel de fuerza del monstruo
    # @return Objeto de la clase Monster
    def initialize(name, intelligence, strength)
      # Llama al constructor de la clase padre LabyrinthCharacter
      super(name, intelligence, strength, INITIAL_HEALTH)
    end

    # Calcula el ataque del monstruo (fuerza + dado de intensidad)
    # @return Valor del ataque del monstruo
    def attack
      Dice.intensity(@strength)
    end



    # ======================================================
    # P3 - Métodos públicos a implementar

    # Gestiona la defensa del monstruo ante un ataque.
    # Si el monstruo está vivo, calcula su defensa (con Dice.intensity).
    # Si el ataque supera la defensa, recibe daño (got_wounded).
    # @param received_attack
    # @return true si el monstruo muere a causa del ataque o ya estaba muerto, false en caso contrario
    def defend(received_attack)
      is_dead = dead # Diagrama 1.1: dead()

      # Diagrama opt: [!is_dead]
      unless is_dead # == if !is_dead
        defensive_energy = Dice.intensity(@intelligence) # Diagrama 1.2: intensity(intelligence)

        # Diagrama opt: [defensive_energy < received_attack]
        if defensive_energy < received_attack
          got_wounded # Diagrama 1.3: got_wounded()

          is_dead = dead # Diagrama 1.4: dead()
        else
          is_dead = false # No muere el monstruo
        end
      end

      is_dead # Diagrama 1.5: is_dead
    end

    # Representación en String del monstruo
    # @return String con la representación del monstruo
    def to_s
      "Monster " + super
    end
  end
end

