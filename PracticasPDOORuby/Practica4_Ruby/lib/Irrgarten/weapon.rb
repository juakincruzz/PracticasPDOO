# frozen_string_literal: true

require_relative 'combat_element'

module Irrgarten
  # Herencia: Weapon hereda de CombatElement
  class Weapon < CombatElement
    # Constructor
    # @param power [Float] potencia del arma
    # @param uses [Integer] número de usos del arma
    # Llama al constructor de la clase padre CombatElement
    # con super(power, uses)
    def initialize(power, uses)
      super(power, uses)
    end

    # Devuelve la potencia del arma si aún tiene usos,
    # decrementando en uno el número de usos
    # @return [Float] potencia del arma o 0.0 si no tiene usos restantes
    # Llama al metodo produce_effect de la clase padre CombatElement
    def attack()
      produce_effect
    end

    # Devuelve una representacion en texto del arma y los usos restantes
    # @return [String] representacion de manera legible del arma
    # Llama al metodo to_s de la clase padre CombatElement
    def to_s
      "W" + super.to_s
    end
  end
end
