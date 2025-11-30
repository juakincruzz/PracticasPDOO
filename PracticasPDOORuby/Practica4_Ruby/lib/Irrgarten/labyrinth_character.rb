# frozen_string_literal: true

module Irrgarten
  class LabyrinthCharacter

    # Similares a los getters públicos y protected de Java
    attr_reader :name, :row, :col, :intelligence, :strength, :health

    # Constructor principal (equivalente al primero de Java)
    def initialize(name, intelligence, strength, health)
      @name = name
      @intelligence = intelligence
      @strength = strength
      @health = health
      @row = -1
      @col = -1
      # NOTA: Aquí NO inicializamos barajas de cartas (CardDecks) en Ruby
    end

    # Metodo copy: Sustituye al constructor de copia de Java
    # Se usa para copiar el estado de otro objeto en 'this' (self)
    def copy(other)
      @name = other.name
      @intelligence = other.intelligence
      @strength = other.strength
      @health = other.health
      @row = other.row
      @col = other.col
    end

    # Equivalente a dead()
    def dead
      @health <= 0
    end

    # Equivalente a setPos
    def set_pos(row, col)
      @row = row
      @col = col
    end

    # Equivalente a toString()
    def to_s
      "#{@name}[H:#{@health}, I:#{@intelligence}, S:#{@strength}, Pos:(#{@row},#{@col})]"
    end

    # --- Métodos 'protected' ---
    # En Ruby usamos la palabra clave protected para restringir visibilidad
    protected

    # Equivalente a gotWounded()
    def got_wounded
      @health -= 1
    end

    # Setter para la salud (equivalente a setHealth)
    # Lo usamos como 'other.health = val' o 'self.health = val'
    def health=(val)
      @health = val
    end

    # --- Métodos abstractos ---
    # Simulamos 'abstract' lanzando una excepción si no se sobrescriben
    public

    def attack
      raise NotImplementedError
    end

    def defend(received_attack)
      raise NotImplementedError
    end
  end
end