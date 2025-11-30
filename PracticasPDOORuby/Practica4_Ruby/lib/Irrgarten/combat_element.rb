# frozen_string_literal: true

module Irrgarten
  # Clase padre para Weapon y Shield
  class CombatElement
    # 'effect' generaliza a power/protection
    # 'uses' es común a ambos
    attr_reader :effect, :uses

    def initialize(effect, uses)
      @effect = effect
      @uses = uses
    end

    # Metodo que produce el efecto (atacar o proteger)
    # Devuelve el valor del efecto si hay usos, o 0 si no los hay
    def produce_effect
      if @uses > 0
        @uses -= 1
        @effect
      else
        0.0
      end
    end

    # Metodo para descartar el elemento (usar Dice)
    def discard
      Dice.discard_element(@uses)
    end

    def to_s
      "[#{@effect}, #{@uses}]"
    end
  end
end

