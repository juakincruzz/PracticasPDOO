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
  end
end

