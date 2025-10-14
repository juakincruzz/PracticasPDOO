# frozen_string_literal: true
require_relative 'directions'
require_relative 'orientation'
require_relative "dice"

module Irrgarten
  class Labyrinth
    BLOCK_CHAR = 'X'
    EMPTY_CHAR = '-'
    MONSTER_CHAR = 'M'
    COMBAT_CHAR = 'C'
    EXIT_CHAR = 'E'
    ROW = 0
    COL = 1

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

    def have_a_winner?
      !@players[@exit_row][@exit_col].nil?
    end

    def to_s
      @labyrinth.map { |row| row.join("\t") }.join("\n")
    end

    def add_monster(row, col, monster)
      if pos_ok(row, col) && empty_pos(row, col)
        @labyrinth[row][col] = MONSTER_CHAR
        @monsters[row][col] = monster
        monster.set_pos(row, col)
      end
    end

    private
    def pos_ok?(row, col)
      row.between?(0, @n_rows - 1) && col.between?(0, @n_cols - 1)
    end

    def empty_pos?(row, col)
      pos_ok?(row, col) && @labyrinth[row][col] == EMPTY_CHAR
    end

    def monster_pos(row, col)
      pos_ok?(row, col) && @labyrinth[row][col] == MONSTER_CHAR
    end

    def exit_pos?(row, col)
      pos_ok?(row, col) && @labyrinth[row][col] == EXIT_CHAR
    end

    def combat_pos?(row, col)
      pos_ok?(row, col) && @labyrinth[row][col] == COMBAT_CHAR
    end

    def can_step_on?(row, col)
      pos_ok?(row, col) && empty_pos?(row, col) || combat_pos?(row, col) || exit_pos?(row, col)
    end

    def update_old_pos(row, col)
      if pos_ok?(row, col)
        @labyrinth[row][col] = combat_pos?(row, col) ? MONSTER_CHAR : EXIT_CHAR
      end
    end

    def dir_2_pos(row, col, direction)
      case direction
      when Directions::UP then [row - 1, col]
      when Directions::DOWN then [row + 1, col]
      when Directions::LEFT then [row, col - 1]
      when Directions::RIGHT then [row, col + 1]
      else [row, col]
      end
    end

    def random_empty_pos
      loop do
        row = Dice.random_pos(@n_rows)
        col = Dice.random_pos(@n_cols)
        return [row, col] if empty_pos?(row, col)
      end
    end

    # ======================================================
    # P3
    # ======================================================
    def spread_player(players) = raise NotImplementedError, "P3"
    def put_player(direction, player) = raise NotImplementedError, "P3"
    def addBlock(orientation, start_row, start_col, length) = raise NotImplementedError, "P3"
    def valid_moves(row, col) = raise NotImplementedError, "P3"
  end
end

