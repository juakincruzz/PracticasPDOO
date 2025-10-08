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

    end
  end
end
