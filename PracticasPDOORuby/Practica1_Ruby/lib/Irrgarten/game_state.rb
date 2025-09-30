# frozen_string_literal: true
module Irrgarten
  class GameState
    attr_reader :labyrinth, :players, :monsters, :current_player, :winner, :log

    def initialize(labyrinth, players, monsters, current_player, winner, log)
      @labyrinth = labyrinth.to_s
      @players = players.to_s
      @monsters = monsters.to_s
      @current_player = current_player.to_i
      @winner = !!winner
      @log = log.to_s
    end

    def to_s
      return "Labyrinth: " + @labyrinth + "\n" + "Players: " + @players
             + "\n" + "Monsters: " + @monsters + "\n" + "Current Player: " + @current_player.to_s + "\n"
    end
  end
end
