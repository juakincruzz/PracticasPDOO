# frozen_string_literal: true
module Irrgarten
  class GameState
    # @return [String] representacion del laberinto
    # @return [String] representacion de los jugadores
    # @return [String] representacion de los monstruos
    # @return [Integer] indice del jugador actual
    # @return [Boolean] indica si hay un ganador o no
    # @return [String] registo de eventos de la partida
    attr_reader :labyrinth, :players, :monsters, :current_player, :winner, :log

    # Inicializa el estado del juego con los datos proporcionados
    # @param labyrinth [Object] laberinto del juego
    # @param players [Object] jugadores de la partida
    # @param monsters [Object] monstruos de la partida
    # @param current_player [Integer] indice del jugador actual
    # @param winner [Boolean] indica si hay un ganador o no
    # @param log [Object] registro de eventos
    def initialize(labyrinth, players, monsters, current_player, winner, log)
      @labyrinth = labyrinth.to_s
      @players = players.to_s
      @monsters = monsters.to_s
      @current_player = current_player.to_i
      @winner = !!winner
      @log = log.to_s
    end

    # Representacion en texto del estado actual de la partida
    # @return [String] estado de la partida de manera legible
    def to_s
      return "Labyrinth: " + @labyrinth + "\n" + "Players: " + @players
             + "\n" + "Monsters: " + @monsters + "\n" + "Current Player: " + @current_player.to_s + "\n"
    end
  end
end
