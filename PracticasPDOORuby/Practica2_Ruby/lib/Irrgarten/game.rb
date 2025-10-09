# frozen_string_literal: true
require_relative 'player'
require_relative 'monster'
require_relative 'labyrinth'
require_relative 'game_state'
require_relative 'dice'

module Irrgarten
  class Game
    MAX_ROUNDS = 10

    def initialize(nplayers)
      @players = Array.new(nplayers) { |i| Player.new(i, Dice.random_intelligence, Dice.random_strength) }
      @monsters = []
      @log = ""
      @labyrinth = Labyrinth.new(10, 10, 0, 0)
      @current_player_index = Dice.who_starts(nplayers)

      # @labyrinth.spread_players(@players)
    end

    def finished?
      @labyrinth.have_a_winner?
    end

    def get_game_state
      GameState.new(@labyrinth.to_s, @players.map(&:to_s).join("\n"), @monsters.map(&:to_s).join("\n"), @current_player_index, finished?, @log)
    end

    def configure_labyrinth
      # Añadir algunos monstruos de ejemplo
      5.times do |i|
        monster = Monster.new("Monster#{i}", Dice.random_intelligence, Dice.random_strength)
        pos = @labyrinth.random_empty_pos
        @labyrinth.add_monster(pos[Labyrinth::ROW], pos[Labyrinth::COL], monster)
        @monsters << monster
      end
    end

    def next_player
      @current_player_index = (@current_player_index + 1) % @players.length
    end

    def log_player_won
      @log += "El jugador ha ganado la partida!\n"
    end

    def log_monster_won
      @log += "El monstruo ha ganado la partid!\n"
    end

    def log_resurrected
      @log += "El jugador ha resucitado\n"
    end

    def log_player_skip_turn
      @log += "El jugador pierde el turno por estar muerto\n"
    end

    def log_player_no_orders
      @log += "El jugador no siguio las indicaciones\n"
    end

    def log_no_monster
      @log += "El jugador se ha movido a una casilla vacia o no le ha sido posible moverse\n"
    end

    def log_rounds(rounds, max)
      @log += "Ronda #{rounds} de #{max}\n"
    end

    # ===========================================================
    # P3
    # ===========================================================
    def next_step(preferred_direction) = raise NotImplementedError, "P3"
    def actual_direction(preferred_direction) = raise NotImplementedError, "P3"
    def combat(monster) = raise NotImplementedError, "P3"
    def manage_reward(winner) = raise NotImplementedError, "P3"
    def manage_resurreection = raise NotImplementedError, "P3"
  end
end

