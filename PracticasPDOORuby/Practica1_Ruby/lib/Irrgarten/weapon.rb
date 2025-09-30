# frozen_string_literal: true
module Irrgarten
  class Weapon
    def initialize(power, uses)
      @power = power.to_f
      @uses = uses.to_i
    end

    def attack()
      return 0.0 if @uses <= 0
      @uses -= 1
      @power
    end

    def to_s
      return "W[" + @power.to_s + ", " + @uses.to_s + "]"
    end

    def discard
      Dice.discard_element(@uses)
    end

    protected
    attr_reader :power, :uses
  end
end
