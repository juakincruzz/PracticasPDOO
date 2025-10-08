# frozen_string_literal: true
require_relative 'weapon'
require_relative 'shield'
require_relative 'dice'

module Irrgarten
  class Player
    MAX_WEAPONS = 2
    MAX_SHIELDS = 3
    INITIAL_HEALTH = 10
    HITS2LOSE = 3

    attr_reader :row, :col, :name

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

    def resurrect
      @weapons.clear
      @shields.clear
      @health = INITIAL_HEALTH
      @consecutive_hits = 0
    end

    def set_pos(row, col) = (@row, @col = row.to_i, col.to_i)

    def dead? = @health <= 0

    def attack
      @strength + sum_weapons
    end

    def defend(received_attack)
      manage_hit(received_attack)
    end

    def new_weapon
      Weapon.new(Dice.weapon_power, Dice.uses_left)
    end

    def new_shield
      Shield.new(Dice.shields_power, Dice.uses_left)
    end

    def defensive_energy
      @intelligence + sum_shields
    end

    def reset_hits
      @consecutive_hits = 0
    end

    def got_wounded
      @health -= 1
    end

    def inc_consecutive_hits
      @consecutive_hits += 1
    end

    def sum_weapons
      @weapons.sum {|w| w.attack}
    end

    def sum_shields
      @shields.sum {|s| s.defend}
    end

    # --- P3: Se dejan en blanco porque son P3 ---
    def receive_weapon(w) = (raise NotImplementedError, "P3")
    def receive_shield(s) = (raise NotImplementedError, "P3")
    def receive_reward = (raise NotImplementedError, "P3")
    def manage_hit(received_attack) = (raise NotImplementedError, "P3")
    def move(direction, valid_moves) = (raise NotImplementedError, "P3")

    def to_s
      "Player{nombre = '#{@name}', inteligencia = '#{@intelligence}', fuerza = '#{@strength}', salud = '#{@health}', posicion = (#{@row}, #{@col}), armas = '#{@weapons}', escudos = '#{@shields}'"
    end
  end
end
