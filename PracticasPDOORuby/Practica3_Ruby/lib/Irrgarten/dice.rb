# frozen_string_literal: true
module Irrgarten
  class Dice
    MAX_USES = 5
    MAX_INTELLIGENCE = 10
    MAX_STRENGTH = 10
    RESURRECT_PROB = 0.3
    WEAPONS_REWARD = 2
    SHIELDS_REWARD = 3
    HEALTH_REWARD = 5
    MAX_ATTACK = 3
    MAX_SHIELD = 2

    # Genera una posicion aleatoria entre 0 y max-1
    # @param max [Integer] valor maximo
    # @return [Integer] posicion aleatoria
    def self.random_pos(max)
      rand(0...max)
    end

    # Selecciona aleatoriamente el jugador que comienza
    # @param nplayers [Integer] numero de jugadores
    # @return [Integer] indice del jugador que empieza
    def self.who_starts(nplayers)
      rand(0...nplayers)
    end

    # Genera un valor aleatorio para la intelgencia del jugador
    # @param ninguno
    # @return [Float] valor de inteligencia
    def self.random_intelligence()
      rand * MAX_INTELLIGENCE
    end

    # Genera un valor aleatorio para la fuerza del jugador
    # @param ninguno
    # @return [Float] valor de fuerza
    def self.random_strength()
      rand * MAX_STRENGTH
    end

    # Probabilidad de resucitar al jugador
    # @param ninguno
    # @return [Boolean] true si resucita, false en caso contrario
    def self.resurrect_player()
      rand < RESURRECT_PROB
    end

    # Genera un valor aleatorio para la recompensa de armas
    # @param ninguno
    # @return [Integer] valor de recompensa de armas
    def self.weapons_reward()
      rand(0..WEAPONS_REWARD)
    end

    # Genera un valor aleatorio para la recompensa de escudos
    # @param ninguno
    # @return [Integer] valor de recompensa de escudos
    def self.shields_reward()
      rand(0..SHIELDS_REWARD)
    end

    # Genera un valor aleatorio para la recompensa de salud
    # @param ninguno
    # @return [Integer] valor de recompensa de salud
    def self.health_reward()
      rand(0..HEALTH_REWARD)
    end

    # Genera un valor aleatorio para el poder del arma
    # @param ninguno
    # @return [Float] valor de poder del arma
    def self.weapon_power()
      rand * MAX_ATTACK
    end

    # Genera un valor aleatorio para el poder del escudo
    # @param ninguno
    # @return [Float] valor de poder del escudo
    def self.shields_power()
      rand * MAX_SHIELD
    end

    # Genera un valor aleatorio para los usos que le quedan al elemento
    # @param ninguno
    # @return [Integer] usos restantes
    def self.uses_left()
      rand(0..MAX_USES)
    end

    # Calcula la intensidad de un ataque o proteccion en funcion de la competencia del jugador
    # @param competence [Float] valor de competencia
    # @return [Float] intensidad calculada
    def self.intensity(competence)
      rand * competence
    end

    # Probabilidad de descartar un elemento en funcion de los usos que le quedan
    # @param uses_left [Integer] usos que le quedan al elemento
    # @return [Boolean] true si se descarta, false en caso contrario
    def self.discard_element(uses_left)
      p = 1.0 - (uses_left.to_f / MAX_USES)
      rand < p
    end
  end
end
