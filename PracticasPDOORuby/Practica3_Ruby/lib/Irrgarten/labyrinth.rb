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

    # Coloca al jugador / los jugadores en casilla/s vacía/s aleatoria/s.
    # @param players
    def spread_player(players)
      # Diagrama loop: [for all]
      players.each do |p| # Diagrama 1.1: next()
        random_empty_pos # Diagrama 1.2: random_empty_pos()
        put_player_2d(-1, -1, pos[ROW], pos[COL], p) # Diagrama 1.3: put_player_2d(-1, -1, row, col, player)
      end
    end

    # Mueve un jugador en una dirección.
    # @param direction
    # @param player
    # @return El monstruo en la nueva casilla, o nil
    def put_player(direction, player)
      old_row = player.row # Diagrama 1.1: getRow()
      old_col = player.col # Diagrama 1.2: getCol()

      new_pos = dir_2_pos(old_row, old_col, direction) # Diagrama 1.3: dir2Pos(oldRow, oldCol, direction)

      monster = put_player_2d(old_row, old_col, new_pos[ROW], new_pos[COL], player) # Diagrama 1.4: putPlayer2D(oldRow, oldCol, newPos[ROW], newPos[COL], player)

      monster # Diagrama 2: return monster
    end

    # Aniade un bloque de obstáculos (muros al laberinto).
    # @param orientation
    # @param start_row
    # @param start_col
    # @param length
    def add_block(orientation, start_row, start_col, length)
      # Diagrama alt: [(orientation == Orientation::VERTICAL)]
      if orientation == Orientation::VERTICAL
        inc_row = 1
        inc_col = 0
      else
        inc_row = 0
        inc_col = 1
      end

      row = start_row
      col = start_col

      # Diagrama loop: [( (posOK(row, col)) && (emptyPos(row, col)) && (length > 0) )]
      while pos_ok?(row, col) && empty_pos?(row, col) && length > 0
        @labyrinth[row][col] = BLOCK_CHAR # Diagrama 1.1: set(row, col, BLOCK_CHAR)

        length -= 1
        row += inc_row
        col += inc_col
      end
    end

    # Devuelve un array de direcciones válidas desde una casilla.
    # @param row
    # @param col
    # @return Array de direcciones a las que se puede mover
    def valid_moves(row, col)
      output = [] # Diagrama 1.1: new

      # Diagrama opt: [canStepOn?(row + 1, col)]
      if can_step_on?(row + 1, col)
        output << Directions::DOWN # Diagrama 1.2: add(Directions::DOWN)
      end

      # Diagrama opt: [canStepOn?(row - 1, col)]
      if can_step_on?(row -1, col)
        output << Directions::UP # Diagrama 1.3: add(Directions::UP)
      end

      # Diagrama opt: [canStepOn?(row, col + 1)]
      if can_step_on?(row, col + 1)
        output << Directions::RIGHT # Diagrama 1.4: add(Directions::RIGHT)
      end

      # Diagrama opt: [canStepOn?(row, col -1)]
      if can_step_on?(row, col -1)
        output << Directions::LEFT # Diagrama 1.5: add(Directions::LEFT)
      end

      output # Diagrama 1.6: return output
    end


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

    # Núcleo de la lógica de movimiento. Mueve un jugador de (old) a (new)
    # @param old_row
    # @param old_col
    # @param row
    # @param col
    # @param player
    # @return Monstruo en la nueva casilla, o nil
    def put_player_2d(old_row, old_col, row, col, player)
      output = nil # Diagrama: output = null

      # Diagrama opt: [canStepOn(row, col)]
      if can_step_on?(row, col)
        # Diagrama opt: [posOK(oldRow, oldCol)]
        if pos_ok?(old_row, old_col)
          p = @players[old_row][old_col] # Diagrama 1.1: get(oldRow, oldCol)

          # Diagrama opt: [p == player]
          if p == player
            update_old_pos(old_row, old_col) # Diagrama 1.2: updateOldPos(oldRow, oldCol)

            @players[old_row][old_col] = nil # Diagrama 1.3: set(oldRow, oldCol, null)
          end
        end

        monster_pos = monster_pos(row, col) # Diagrama 1.4: monsterPos(row, col)

        # Diagrama alt: [monsterPos]
        if monster_pos
          @labyrinth[row][col] = COMBAT_CHAR # Diagrama 1.5: set(row,col, COMBAT_CHAR)

          output = @monsters[row][col] # Diagrama 1.6: get(row, col)
        else
          number = player.number # Diagrama 1.7: getNumber()

          @labyrinth[row][col] = number # Diagrama 1.8: set(row, col, number)
        end

        @players[row][col] = player # Diagrama 1.9: set(row, col, player)

        player.set_pos(row, col) # Diagrama 1.10: setPos(row, col)
      end

      output # Diagrama: return output
    end
  end
end

