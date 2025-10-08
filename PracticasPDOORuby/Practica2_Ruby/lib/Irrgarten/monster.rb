# frozen_string_literal: true
module Irrgarten
  class Monster
    INITIAL_HEALTH = 5

    attr_reader :row, :col

    def initialize(name, intelligence, strength, health = INITIAL_HEALTH)
      @name = name.to_s
      @intelligence = intelligence.to_f
      @strength = strength.to_f
      @health = health.to_f
      @row = @col = nil
    end

    def dead?
      @health <= 0
    end

    def attack
      Dice.intensity(@strength)
    end

    def defend(received_attack)
      raise NotImplementedError, "P3"
    end

    def setPos(row, col)
      @row = row.to_i
      @col = col.to_i
    end

    def got_wounded
      @health -= 1
    end

    def to_s
      "Monster{name:'#{@name}', int:'#{@intelligence}', str:'#{@strength}', hp:'#{@health}, pos: (#{@row}, #{@col})}"
    end
  end
end

