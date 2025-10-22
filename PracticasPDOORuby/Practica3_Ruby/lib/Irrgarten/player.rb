# frozen_string_literal: true
require_relative 'weapon'
require_relative 'shield'
require_relative 'dice'

module Irrgarten
  class Player
    # --- Constantes y Atributos de Lectura ---
    MAX_WEAPONS = 2
    MAX_SHIELDS = 3
    INITIAL_HEALTH = 10
    HITS2LOSE = 3

    attr_reader :row, :col, :name, :name, :intelligence, :strength, :health

    # ======================================================
    # MÉTODOS PÚBLICOS
    # ======================================================
    # Constructor
    # @param number Identificador del jugador (0, 1, 2, ...)
    # @param intelligence Nivel de inteligencia del jugador
    # @param strength Nivel de fuerza del jugador
    # @return Objeto de la clase Player
    def initialize(number, intelligence, strength)
      @number = number.to_s[0]
      @intelligence = intelligence.to_f
      @strength = strength.to_f
      @name = "Player#{@number}"
      @health = INITIAL_HEALTH.to_f
      @row = @col = -1
      @consecutive_hits = 0
      @weapons = []
      @shields = []
    end

    # Resucita al jugador, dejándolo como al principio
    # @return Nada (modifica el estado del jugador)
    def resurrect
      @weapons.clear
      @shields.clear
      @health = INITIAL_HEALTH
      @consecutive_hits = 0
    end

    # Establece la posición del jugador en el laberinto
    # @param row Fila de la posición
    # @param col Columna de la posición
    # @return Nada (modifica el estado del jugador)
    def set_pos(row, col) = (@row, @col = row.to_i, col.to_i)

    # Indica si el jugador está muerto
    # @return true si el jugador está muerto (salud <= 0), false en caso contrario
    def dead? = @health <= 0

    # Calcula el ataque del jugador (fuerza + armas)
    # @return Valor del ataque del jugador
    def attack
      @strength + sum_weapons
    end

    # Defiende al jugador de un ataque recibido
    # @param received_attack Valor del ataque recibido
    # @return Nada (modifica el estado del jugador)
    def defend(received_attack)
      manage_hit(received_attack)
    end

    # Representación en cadena del jugador
    # @return Cadena con la información del jugador
    def to_s
      "Player{nombre = '#{@name}', inteligencia = '#{@intelligence}', fuerza = '#{@strength}', salud = '#{@health}', posicion = (#{@row}, #{@col}), armas = '#{@weapons}', escudos = '#{@shields}'"
    end

    # ======================================================
    # P3 - Métodos públicos a implementar
    # ======================================================

    # Decide la dirección final del movimiento del jugador.
    # Si la dirección preferida es válida, se devuelve.
    # Si no es válida, pero hay movimientos válidos, se devuelve el primero de la lista de válidos.
    # Si no hay movimientos válidos, se devuelve la preferida aunque sea inválida.
    # @param direction
    # @param valid_moves
    # @return Dirección final
    def move(direction, valid_moves)
      size = valid_moves.length # Diagrama 1.1: size()
      contained = valid_moves.include?(direction) # Diagrama 1.2: contains(direction)

      # Diagrama alt: [(size > 0) && (!contained)]
      if size > 0 && !contained
        @first_element = valid_moves[0] # Diagrama 1.3: get(0)

        return @first_element # Diagrama 1.4: return first_element
      else
         direction # Diagrama 1.5: return direction
      end
    end

    # Recibe las recompensas (armas, escudo y salud) al ganar un combate.
    # Delega en Dice para obtener las cantidades y en los métodos privados
    # receive_weapon y receive_shield para gestionarles
    def receive_reward
      w_reward = Dice.weapons_reward # Diagrama 1.1: weapons_reward
      s_reward = Dice.shields_reward # Diagrama 1.2: shields_reward

      # Diagrama loop: [1, wReward]
      w_reward.times do
        wnew = new_weapon # Diagrama 1.3: new_weapon

        receive_weapon(wnew) # Diagrama 1.4: receive_weapon(wnew)
      end

      # Diagrama loop: [1, sReward]
      s_reward.times do
        snew = new_shield # Diagrama 1.5: new_shield

        receive_shield(snew) # Diagrama 1.6: receive_shield(snew)
      end

      extra_health = Dice.health_reward # Diagrama 1.7: health_reward

      @health += extra_health # Diagrama: health += extra_health
    end

    # =====================================================
    # MÉTODOS PRIVADOS
    # =====================================================
    private

    # Calcula la energía defensiva del jugador (inteligencia + escudos)
    # @return Valor de la energía defensiva del jugador
    def defensive_energy
      @intelligence + sum_shields
    end

    # Resetea el contador de golpes consecutivos
    # @return Nada (modifica el estado del jugador)
    def reset_hits
      @consecutive_hits = 0
    end

    # Incrementa el contador de golpes consecutivos
    # @return Nada (modifica el estado del jugador)
    def inc_consecutive_hits
      @consecutive_hits += 1
    end

    # Suma el poder de ataque de todas las armas del jugador
    # @return Suma del poder de ataque de las armas
    def sum_weapons
      @weapons.sum {|w| w.attack}
    end

    # Suma la protección de todos los escudos del jugador
    # @return Suma de la protección de los escudos
    def sum_shields
      @shields.sum {|s| s.protect}
    end

    # Crea un nuevo arma con poder y usos aleatorios
    # @return Nuevo objeto de la clase Weapon
    def new_weapon
      Weapon.new(Dice.weapon_power, Dice.uses_left)
    end

    # Crea un nuevo escudo con poder y usos aleatorios
    # @return Nuevo objeto de la clase Shield
    def new_shield
      Shield.new(Dice.shields_power, Dice.uses_left)
    end

    # El jugador ha sido herido (pierde 1 punto de salud)
    # @return Nada (modifica el estado del jugador)
    def got_wounded
      @health -= 1
    end

    # ======================================================
    # P3 - Métodos privados a implementar
    # ======================================================

    # Gestiona la recepción de un arma.
    # Itera sobre las armas actuales, descartando (eliminando) las que
    # ya no tengan usos. Si tras la limpieza hay espacio (< MAX_WEAPONS), se aniade la nueva arma.
    # @param w
    def receive_weapon(w)
      # Diagrama loop: [for all] + 1.1 + 1.2 + 1.3
      # delete_if itera y elimina si el bloque es true
      @weapons.delete_if { |wi| wi.discard }

      size = @weapons.length # Diagrama 1.4: size()

      # Diagrama opt: [size < MAX_WEAPONS]
      if (size < MAX_WEAPONS)
        @weapons << w # Diagrama 1.5: add(w)
      end
    end

    # Gestiona la recepción de un escudo.
    # Itera sobre los escudos actuales, descartando (eliminando) los que
    # ya no tengan usos. Si tras la limpieza hay espacio (< MAX_SHIELDS), se aniade el nuevo escudo.
    # @param s
    def receive_shield(s)
      # Diagrama loop: [for all] + 1.1 + 1.2 + 1.3
      # delete_if itera y elimina si el bloque es true
      @shields.delete_if { |si| si.discard }

      size = @shields.length # Diagrama 1.4: size()

      # Diagrama opt: [size < MAX_SHIELDS]
      if (size < MAX_SHIELDS)
        @shields << s # Diagrama 1.5: add(s)
      end
    end

    # Gestiona la lógica de un impacto recibido.
    # Compara la defensa del jugador con el ataque. Si es superado,
    # recibe daño y acumula impactos. Si resiste, reinicia impactos.
    # Comprueba si el jugador pierde (por vida o por impactos consecutivos).
    # @param received_attack
    # @return true si el jugador pierde, false en caso contrario
    def manage_hit(received_attack)
      defense = defensive_energy # Diagrama 1.1: defensive_energy()

      # Diagrama alt: [defense < received_attack]
      if defense < received_attack
        got_wounded # Diagrama 1.2: got_wounded()
        inc_consecutive_hits # Diagrama 1.3: inc_consecutive_hits()
      else
        reset_hits # Diagrama 1.4: reset_hits()
      end

      # Diagrama alt: [( (consecutive_hits >= HITS2LOSE) || (dead?))]
      if (@consecutive_hits >= HITS2LOSE) || dead?
        reset_hits # Diagrama 1.5: reset_hits()
        lose = true # Diagrama: lose = true
      else
        lose = false # Diagrama: lose = false
      end

      lose # Diagrama 2: return lose
    end
  end
end
