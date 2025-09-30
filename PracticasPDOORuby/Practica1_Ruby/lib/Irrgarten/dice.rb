# frozen_string_literal: true
module Irrgarten
  class Dice
    MAX_USES = 5
    MAX_INTELLIGENCE = 10
    MAX_STRENGTH = 10
    RESURRECT_PROB = 0.3
    WEAPONS_REWARD = 2
    SHIELDS_REWARD = 3
    HEALTH_REWARD = 5
    MAX_ATTACK = 3
    MAX_SHIELD = 2

    def self.random_pos(max)
      rand(0...max)
    end

    def self.who_starts(nplayers)
      rand(0...nplayers)
    end

    def self.random_intelligence()
      rand * MAX_INTELLIGENCE
    end

    def self.random_strength()
      rand * MAX_STRENGTH
    end

    def self.resurrect_player()
      rand < RESURRECT_PROB
    end

    def self.weapons_reward()
      rand(0..WEAPONS_REWARD)
    end

    def self.shields_reward()
      rand(0..SHIELDS_REWARD)
    end

    def self.health_reward()
      rand(0..HEALTH_REWARD)
    end

    def self.weapon_power()
      rand * MAX_ATTACK
    end

    def self.shields_power()
      rand * MAX_SHIELD
    end

    def self.uses_left()
      rand(0..MAX_USES)
    end

    def self.intensity(competence)
      rand * competence
    end

    def self.discard_element(uses_left)
      p = 1.0 - (uses_left.to_f / MAX_USES)
      rand < p
    end
  end
end
