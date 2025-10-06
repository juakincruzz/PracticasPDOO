# frozen_string_literal: true
module Irrgarten
  class Weapon
    # Constructor
    # @param power [Float] potencia del arma
    # @param uses [Integer] numero de usos del arma
    def initialize(power, uses)
      @power = power.to_f
      @uses = uses.to_i
    end

    # Devuelve la potencia del arma si aun tiene usos,
    # decrementando en uno el numero de usos
    # @param ninguno
    # @return [Float] potencia del arma o 0.0 si no tiene usos restantes
    def attack()
      return 0.0 if @uses <= 0
      @uses -= 1
      @power
    end

    # Devuelve una representacion en texto del arma y los usos restantes
    # @param ninguno
    # @return [String] representacion de manera legible del arma
    def to_s
      return "W[" + @power.to_s + ", " + @uses.to_s + "]"
    end

    # Decide si se descarta el arma en funcion del numero de usos restantes
    # @param ninguno
    # @return [Boolean] true si se descarta, false en caso contrario
    def discard
      Dice.discard_element(@uses)
    end

    protected
    attr_reader :power, :uses
  end
end
