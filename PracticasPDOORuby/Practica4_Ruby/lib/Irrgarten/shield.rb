# frozen_string_literal: true

require_relative 'combat_element'

module Irrgarten
  # Herencia: Shield hereda de CombatElement
  class Shield < CombatElement
    # Constructor
    # @param protection [Float] nivel de protección del escudo
    # @param uses [Integer] número de usos del escudo
    def initialize(protection, uses)
      super(protection, uses)
    end

    # Metodo que devuelve el nivel de proteccion del escudo
    # decrementando en uno el número de usos
    # @param ninguno
    # @return [Float] nivel de proteccion del escudo o 0.0
    def protect()
      produce_effect
    end

    # Metodo que devuelve una representación en texto del escudo
    # @return [String] representación de manera legible del texto
    def to_s
      "S" + super
    end
  end
end
