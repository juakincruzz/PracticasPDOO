# frozen_string_literal: true
require_relative 'weapon'
require_relative 'shield'
require_relative 'dice'

module Irrgarten
  class Player
    # --- Constantes y Atributos de Lectura ---
    MAX_WEAPONS = 2
    MAX_SHIELDS = 3
    INITIAL_HEALTH = 10
    HITS2LOSE = 3

    attr_reader :row, :col, :name, :name, :intelligence, :strength, :health

    # ======================================================
    # MÉTODOS PÚBLICOS
    # ======================================================
    # Constructor
    # @param number Identificador del jugador (0, 1, 2, ...)
    # @param intelligence Nivel de inteligencia del jugador
    # @param strength Nivel de fuerza del jugador
    # @return Objeto de la clase Player
    def initialize(number, intelligence, strength)
      @number = number.to_s[0]
      @intelligence = intelligence.to_f
      @strength = strength.to_f
      @name = "Player#{@number}"
      @health = INITIAL_HEALTH.to_f
      @row = @col = -1
      @consecutive_hits = 0
      @weapons = []
      @shields = []
    end

    # Resucita al jugador, dejándolo como al principio
    # @return Nada (modifica el estado del jugador)
    def resurrect
      @weapons.clear
      @shields.clear
      @health = INITIAL_HEALTH
      @consecutive_hits = 0
    end

    # Establece la posición del jugador en el laberinto
    # @param row Fila de la posición
    # @param col Columna de la posición
    # @return Nada (modifica el estado del jugador)
    def set_pos(row, col) = (@row, @col = row.to_i, col.to_i)

    # Indica si el jugador está muerto
    # @return true si el jugador está muerto (salud <= 0), false en caso contrario
    def dead? = @health <= 0

    # Calcula el ataque del jugador (fuerza + armas)
    # @return Valor del ataque del jugador
    def attack
      @strength + sum_weapons
    end

    # Defiende al jugador de un ataque recibido
    # @param received_attack Valor del ataque recibido
    # @return Nada (modifica el estado del jugador)
    def defend(received_attack)
      manage_hit(received_attack)
    end

    # El jugador ha sido herido (pierde 1 punto de salud)
    # @return Nada (modifica el estado del jugador)
    def got_wounded
      @health -= 1
    end

    # Representación en cadena del jugador
    # @return Cadena con la información del jugador
    def to_s
      "Player{nombre = '#{@name}', inteligencia = '#{@intelligence}', fuerza = '#{@strength}', salud = '#{@health}', posicion = (#{@row}, #{@col}), armas = '#{@weapons}', escudos = '#{@shields}'"
    end

    # ======================================================
    # P3 - Métodos públicos a implementar
    # ======================================================
    def move(direction, valid_moves) = (raise NotImplementedError, "P3")
    def receive_reward = (raise NotImplementedError, "P3")

    # =====================================================
    # MÉTODOS PRIVADOS
    # =====================================================
    private

    # Calcula la energía defensiva del jugador (inteligencia + escudos)
    # @return Valor de la energía defensiva del jugador
    def defensive_energy
      @intelligence + sum_shields
    end

    # Resetea el contador de golpes consecutivos
    # @return Nada (modifica el estado del jugador)
    def reset_hits
      @consecutive_hits = 0
    end

    # Incrementa el contador de golpes consecutivos
    # @return Nada (modifica el estado del jugador)
    def inc_consecutive_hits
      @consecutive_hits += 1
    end

    # Suma el poder de ataque de todas las armas del jugador
    # @return Suma del poder de ataque de las armas
    def sum_weapons
      @weapons.sum {|w| w.attack}
    end

    # Suma la protección de todos los escudos del jugador
    # @return Suma de la protección de los escudos
    def sum_shields
      @shields.sum {|s| s.protect}
    end

    # Crea un nuevo arma con poder y usos aleatorios
    # @return Nuevo objeto de la clase Weapon
    def new_weapon
      Weapon.new(Dice.weapon_power, Dice.uses_left)
    end

    # Crea un nuevo escudo con poder y usos aleatorios
    # @return Nuevo objeto de la clase Shield
    def new_shield
      Shield.new(Dice.shields_power, Dice.uses_left)
    end

    # ======================================================
    # P3 - Métodos privados a implementar
    # ======================================================
    def receive_weapon(w) = (raise NotImplementedError, "P3")
    def receive_shield(s) = (raise NotImplementedError, "P3")
    def manage_hit(received_attack) = (raise NotImplementedError, "P3")
  end
end
