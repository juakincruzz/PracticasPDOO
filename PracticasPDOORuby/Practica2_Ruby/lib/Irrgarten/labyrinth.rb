# frozen_string_literal: true
require_relative 'directions'
require_relative 'orientation'
require_relative "dice"

module Irrgarten
  class Labyrinth
    # Atributos de clase
    BLOCK_CHAR = 'X'
    EMPTY_CHAR = '-'
    MONSTER_CHAR = 'M'
    COMBAT_CHAR = 'C'
    EXIT_CHAR = 'E'
    ROW = 0
    COL = 1

    # --- Métodos públicos ---
    # Constructor
    # @param n_rows Número de filas del laberinto
    # @param n_cols Número de columnas del laberinto
    # @param exit_row Fila de la salida del laberinto
    # @param exit_col Columna de la salida del laberinto
    # @return Objeto de la clase Labyrinth
    def initialize(n_rows, n_cols, exit_row, exit_col)
      @n_rows = n_rows
      @n_cols = n_cols
      @exit_row = exit_row
      @exit_col = exit_col

      @monsters = Array.new(n_rows) { Array.new(n_cols) }
      @players = Array.new(n_rows) { Array.new(n_cols) }
      @labyrinth = Array.new(n_rows) { Array.new(n_cols, EMPTY_CHAR) }
      @labyrinth[exit_row][exit_col] = EXIT_CHAR
    end

    # Indica si hay un ganador (un jugador en la salida)
    # @return true si hay un ganador, false en caso contrario
    def have_a_winner?
      !@players[@exit_row][@exit_col].nil?
    end

    # Representación en String del laberinto
    # @return String con la representación del laberinto
    def to_s
      @labyrinth.map { |row| row.join("\t") }.join("\n")
    end

    # Añade un monstruo en una posición concreta del laberinto
    # @param row Fila donde se quiere añadir el monstruo
    # @param col Columna donde se quiere añadir el monstruo
    # @param monster Objeto de la clase Monster a añadir
    # @return Nada (modifica el estado del laberinto)
    def add_monster(row, col, monster)
      if pos_ok?(row, col) && empty_pos?(row, col)
        @labyrinth[row][col] = MONSTER_CHAR
        @monsters[row][col] = monster
        monster.set_pos(row, col)
      end
    end

    # Genera una posición vacía aleatoria en el laberinto
    # @return Array con la fila y columna de una posición vacía aleatoria
    def random_empty_pos
      loop do
        row = Dice.random_pos(@n_rows)
        col = Dice.random_pos(@n_cols)
        return [row, col] if empty_pos?(row, col)
      end
    end

    # ======================================================
    # P3 - Métodos públicos a implementar
    # ======================================================
    def spread_player(players) = raise NotImplementedError, "P3"
    def put_player(direction, player) = raise NotImplementedError, "P3"
    def add_block(orientation, start_row, start_col, length) = raise NotImplementedError, "P3"
    def valid_moves(row, col) = raise NotImplementedError, "P3"


    # --- Métodos privados ---
    # Los siguientes métodos son de uso interno de la clase Labyrinth
    private

    # Comprueba si una posición está dentro de los límites del laberinto
    # @param row Fila de la posición a comprobar
    # @param col Columna de la posición a comprobar
    # @return true si la posición es válida, false en caso contrario
    def pos_ok?(row, col)
      row.between?(0, @n_rows - 1) && col.between?(0, @n_cols - 1)
    end

    # Comprueba si una posición está vacía
    # @param row Fila de la posición a comprobar
    # @param col Columna de la posición a comprobar
    # @return true si la posición está vacía, false en caso contrario
    def empty_pos?(row, col)
      pos_ok?(row, col) && @labyrinth[row][col] == EMPTY_CHAR
    end

    # Comprueba si una posición tiene un monstruo
    # @param row Fila de la posición a comprobar
    # @param col Columna de la posición a comprobar
    # @return true si la posición tiene un monstruo, false en caso contrario
    def monster_pos(row, col)
      pos_ok?(row, col) && @labyrinth[row][col] == MONSTER_CHAR
    end

    # Comprueba si una posición es la salida
    # @param row Fila de la posición a comprobar
    # @param col Columna de la posición a comprobar
    # @return true si la posición es la salida, false en caso contrario
    def exit_pos?(row, col)
      pos_ok?(row, col) && @labyrinth[row][col] == EXIT_CHAR
    end

    # Comprueba si una posición es de combate (monstruo debilitado)
    # @param row Fila de la posición a comprobar
    # @param col Columna de la posición a comprobar
    # @return true si la posición es de combate, false en caso contrario
    def combat_pos?(row, col)
      pos_ok?(row, col) && @labyrinth[row][col] == COMBAT_CHAR
    end

    # Comprueba si se puede pisar una posición (vacía, de combate o la salida)
    # @param row Fila de la posición a comprobar
    # @param col Columna de la posición a comprobar
    # @return true si se puede pisar la posición, false en caso contrario
    def can_step_on?(row, col)
      pos_ok?(row, col) && empty_pos?(row, col) || combat_pos?(row, col) || exit_pos?(row, col)
    end

    # Actualiza la posición antigua del jugador en el laberinto
    # @param row Fila de la posición antigua
    # @param col Columna de la posición antigua
    # @return Nada (modifica el estado del laberinto)
    def update_old_pos(row, col)
      if pos_ok?(row, col)
        @labyrinth[row][col] = combat_pos?(row, col) ? MONSTER_CHAR : EXIT_CHAR
      end
    end

    # Calcula la nueva posición a partir de una dirección
    # @param row Fila de la posición actual
    # @param col Columna de la posición actual
    # @param direction Dirección del movimiento (una de las constantes de Directions)
    # @return Array con la fila y columna de la nueva posición
    def dir_2_pos(row, col, direction)
      case direction
      when Directions::UP then [row - 1, col]
      when Directions::DOWN then [row + 1, col]
      when Directions::LEFT then [row, col - 1]
      when Directions::RIGHT then [row, col + 1]
      else [row, col]
      end
    end

    # ================================================
    # P3 - Métodos privados a implementar
    # ================================================
    def put_player_2d(old_row, old_col, row, col, player) = raise NotImplementedError, "P3"
  end
end

