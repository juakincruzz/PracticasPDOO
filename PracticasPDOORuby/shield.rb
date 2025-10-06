# frozen_string_literal: true
module Irrgarten
  class Shield
    # Constructor
    # @param protection [Float] nivel de proteccion del escudo
    # @param uses [Integer] numero de usos del escudo
    def initialize(protection, uses)
      @protection = protection.to_f
      @uses = uses.to_i
    end

    # Metodo que devuelve el nivel de proteccion del escudo
    # decrementando en uno el numero de usos
    # @param ninguno
    # @return [Float] nivel de proteccion del escudo o 0.0
    def protect()
      return 0.0 if @uses <= 0
      @uses -= 1
      @protection
    end

    # Metodo que devuelve una representacion en texto del escudo
    # @param ninguno
    # @return [String] representacion de manera legible del texto
    def to_s
      return "S[" + @protection.to_s + ", " + @uses.to_s + "]"
    end

    # Metodo que devuelve true o false si se tiene que descartar el escudo
    def discard
      Dice.discard_element(@uses)
    end

    protected
    attr_reader :protection, :uses
  end
end
