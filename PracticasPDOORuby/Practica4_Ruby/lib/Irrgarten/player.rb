# frozen_string_literal: true

require_relative 'labyrinth_character'
require_relative 'dice'
require_relative 'weapon'
require_relative 'shield'

module Irrgarten
  class Player < LabyrinthCharacter

    # Constantes
    MAX_WEAPONS = 2
    MAX_SHIELDS = 3
    INITIAL_HEALTH = 10
    HITS2LOSE = 3

    # Atributos específicos de Player (los demás están en el padre)
    attr_reader :number, :consecutive_hits, :weapons, :shields

    def initialize(number, intelligence, strength)
      # 1. Inicializamos la parte del padre
      super("Player #{number}", intelligence, strength, INITIAL_HEALTH)

      # 2. Inicializamos la parte propia
      @number = number
      @consecutive_hits = 0
      @weapons = []
      @shields = []
    end

    # NUEVO P4: Metodo copy (sustituye al constructor de copia)
    def copy(other)
      # Copiamos la parte del padre
      super(other)

      # Copiamos la parte propia
      @number = other.number
      @consecutive_hits = other.consecutive_hits

      # Copia profunda de armas y escudos
      @weapons = []
      @shields = []

      other.weapons.each do |w|
        @weapons << Weapon.new(w.effect, w.uses)
      end

      other.shields.each do |s|
        @shields << Shield.new(s.effect, s.uses)
      end
    end

    def resurrect
      self.health = INITIAL_HEALTH
      @consecutive_hits = 0
      @weapons = []
      @shields = []
    end

    # Movimiento: delega en el padre para saber la posición,
    # pero mantiene la lógica de dirección
    def move(direction, valid_moves)
      size = valid_moves.size
      contained = valid_moves.include?(direction)

      if size > 0 && !contained
        first = valid_moves[0]
        return first
      else
        return direction
      end
    end

    def attack
      @strength + sum_weapons
    end

    def defend(received_attack)
      manage_hit(received_attack)
    end

    # Recepción de premios (sin CardDeck en Ruby)
    def receive_reward
      w_reward = Dice.weapons_reward
      s_reward = Dice.shields_reward

      w_reward.times do
        wnew = new_weapon
        receive_weapon(wnew)
      end

      s_reward.times do
        snew = new_shield
        receive_shield(snew)
      end

      extra_health = Dice.health_reward
      self.health += extra_health
    end

    def to_s
      "Player #{@number} " + super + "\n" +
        "  Weapons: #{@weapons.join(', ')}\n" +
        "  Shields: #{@shields.join(', ')}"
    end

    # --- Métodos privados/protegidos ---
    private

    def receive_weapon(w)
      @weapons.each do |wi|
        discard = wi.discard
        if discard
          @weapons.delete(wi)
        end
      end

      size = @weapons.size
      if size < MAX_WEAPONS
        @weapons << w
      end
    end

    def receive_shield(s)
      @shields.each do |si|
        discard = si.discard
        if discard
          @shields.delete(si)
        end
      end

      size = @shields.size
      if size < MAX_SHIELDS
        @shields << s
      end
    end

    def new_weapon
      Weapon.new(Dice.weapon_power, Dice.uses_left)
    end

    def new_shield
      Shield.new(Dice.shield_power, Dice.uses_left)
    end

    def sum_weapons
      sum = 0
      @weapons.each { |w| sum += w.attack }
      sum
    end

    def sum_shields
      sum = 0
      @shields.each { |s| sum += s.protect }
      sum
    end

    def defensive_energy
      @intelligence + sum_shields
    end

    def manage_hit(received_attack)
      defense = defensive_energy
      if defense < received_attack
        got_wounded
        inc_consecutive_hits
      else
        reset_hits
      end

      lose = false
      if @consecutive_hits == HITS2LOSE || dead
        reset_hits
        lose = true
      end
      lose
    end

    def reset_hits
      @consecutive_hits = 0
    end

    def inc_consecutive_hits
      @consecutive_hits += 1
    end

    # Hacemos estos métodos protegidos para que FuzzyPlayer pueda usarlos
    protected :sum_weapons, :sum_shields, :defensive_energy
  end
end