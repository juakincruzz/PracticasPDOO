# frozen_string_literal: true

require 'io/console' # Para leer teclas sin pulsar Enter
require_relative '../Irrgarten/directions' # Necesitamos Directions

# Módulo para la interfaz de usuario
module UI
  # Clase TextUI: Vista textual del juego Irrgarten.
  class TextUI
    # Muestra el estado actual del juego en la consola.
    # @param game_state [Irrgarten::GameState] El estado del juego a mostrar.
    def show_game(game_state)
      puts '=================================================='
      puts '--- LABERINTO ---'
      puts game_state.labyrinth
      puts # Línea en blanco
      puts '--- JUGADORES ---'
      puts game_state.players
      puts # Línea en blanco
      puts '--- MONSTRUOS ---'
      puts game_state.monsters
      puts # Línea en blanco
      puts '--- LOG ---'
      puts game_state.log
      puts # Línea en blanco
      puts "Turno del Jugador: #{game_state.current_player}"

      if game_state.winner
        puts # Línea en blanco
        puts '¡¡¡ EL JUEGO HA TERMINADO !!!'
        puts "El jugador #{game_state.current_player} ha ganado."
      end
      puts '=================================================='
    end

    # Lee el próximo movimiento del jugador usando teclas individuales (w,a,s,d).
    # @return [Irrgarten::Directions] La dirección elegida.
    def next_move
      print 'Elige un movimiento (w:UP, a:LEFT, s:DOWN, d:RIGHT): '
      # STDIN.echo = false # Descomentar para ocultar la tecla pulsada
      # STDIN.raw! # Modo raw para leer tecla a tecla

      direction = nil
      loop do
        c = STDIN.getch.downcase # Lee un solo caracter y lo pasa a minúscula

        case c
        when 'w'
          direction = Irrgarten::Directions::UP
          puts c # <-- MOVER AQUÍ
          break
        when 'a'
          direction = Irrgarten::Directions::LEFT
          puts c # <-- MOVER AQUÍ
          break
        when 's'
          direction = Irrgarten::Directions::DOWN
          puts c # <-- MOVER AQUÍ
          break
        when 'd'
          direction = Irrgarten::Directions::RIGHT
          puts c # <-- MOVER AQUÍ
          break
          # Permitir salir con Ctrl+C
        when "\u0003"
          puts "\nSaliendo..."
          exit
        else
          # Suena un 'beep' o similar si la terminal lo soporta,
          # para indicar tecla inválida. No imprime nada.
          print "\a"
        end
      end

      # STDIN.cooked! # Volver al modo normal de la terminal
      # STDIN.echo = true
      # puts c # <-- QUITAR DE AQUÍ
      direction # Devuelve la dirección
    end
  end
end