# frozen_string_literal: true

require_relative 'player'
require_relative 'dice'

module Irrgarten
  class FuzzyPlayer < Player

    # Constructor a partir de otro jugador (copy)
    def initialize(other)
      # En Ruby llamamos a copy directamente, ya que initialize no tiene por qué llamar a super
      # si construimos el objeto entero nosotros.
      copy(other)
    end

    def move(direction, valid_moves)
      # 1. Calculamos la preferencia (lo que haría un jugador normal)
      # Llamamos al move del padre (Player)
      preference = super(direction, valid_moves)

      # 2. Usamos el dado para decidir
      Dice.next_step(preference, valid_moves, @intelligence)
    end

    def attack
      # Suma de armas + Dice.intensity(strength)
      sum_weapons + Dice.intensity(@strength)
    end

    def defensive_energy
      # Suma de escudos + Dice.intensity(intelligence)
      sum_shields + Dice.intensity(@intelligence)
    end

    def to_s
      "Fuzzy" + super
    end
  end
end