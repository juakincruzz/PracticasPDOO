# frozen_string_literal: true

require_relative '../Irrgarten/game' # Necesita el modelo Game
require_relative '../UI/text_ui'     # Necesita la vista TextUI

# Módulo para el controlador del juego
module Controller
  # Clase Controller: Coordina el modelo (Game) y la vista (TextUI).
  class Controller
    # Constructor
    # @param game [Irrgarten::Game] Instancia del juego (modelo).
    # @param ui [UI::TextUI] Instancia de la interfaz de usuario (vista).
    def initialize(game, ui)
      @game = game
      @ui = ui
    end

    # Inicia y ejecuta el bucle principal del juego.
    def play
      end_game = false

      # Bucle principal: mientras no termine el juego
      until end_game
        # 1. Mostrar estado
        @ui.show_game(@game.get_game_state)

        # 2. Pedir movimiento
        direction = @ui.next_move

        # 3. Ejecutar siguiente paso en el modelo
        end_game = @game.next_step(direction)
      end

      # 4. Mostrar estado final (pantalla de ganador)
      @ui.show_game(@game.get_game_state)
    end
  end
end