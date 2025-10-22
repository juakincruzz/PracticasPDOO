# frozen_string_literal: true
require_relative 'player'
require_relative 'monster'
require_relative 'labyrinth'
require_relative 'game_state'
require_relative 'dice'

module Irrgarten
  class Game
    # --- Constantes y Atributos de Lectura ---
    MAX_ROUNDS = 10

    # ===========================================================
    # MÉTODOS PÚBLICOS
    # ===========================================================

    # Constructor
    # @param nplayers Número de jugadores en la partida
    # @return Objeto de la clase Game
    def initialize(nplayers)
      @players = Array.new(nplayers) { |i| Player.new(i, Dice.random_intelligence, Dice.random_strength) }
      @monsters = []
      @log = ""
      @labyrinth = Labyrinth.new(10, 10, 0, 0)
      @current_player_index = Dice.who_starts(nplayers)

      # Llamada a un método privado para la configuración inicial
      configure_labyrinth

      # Distribuir jugadores en el laberinto
      # @labyrinth.spread_player(@players)
    end

    # Determina si la partida ha finalizado comprobando si hay un ganador en el laberinto.
    # @return [Boolean] true si hay un ganador, false en caso contrario
    def finished?
      @labyrinth.have_a_winner?
    end

    # Obtiene el estado actual del juego, incluyendo el laberinto, los jugadores, los monstruos,
    # el índice del jugador actual, si hay un ganador y el registro de eventos.
    # @return [GameState] Objeto que representa el estado actual del juego
    def get_game_state
      GameState.new(@labyrinth.to_s, @players.map(&:to_s).join("\n"), @monsters.map(&:to_s).join("\n"), @current_player_index, finished?, @log)
    end

    # ===========================================================
    # P3 - Métodos públicos a implementar
    # ==========================================================

    # Ejecuta el siguiente paso (turno) del juego.
    # @param preferred_direction Dirección de movimiento preferida
    # @return Si el juego ha terminado
    def next_step(preferred_direction)
      log = ""

      dead = @current_player.is_dead? # Diagrama 1.1: dead()

      # Diagrama alt: [!dead]
      unless dead # == if !dead
        direction = actual_direction(preferred_direction) # Diagrama 1.2: actualDirection(preferredDirection)

        # Diagrama opt: [direction != preferredDirection]
        if direction != preferred_direction
          log_player_no_orders # Diagrama 1.3: logPlayerNoOrders()
        end

        monster = @labyrinth.put_player(direction, @current_player) # Diagrama 1.4: putPlayer(direction, currentPlayer)

        # Diagrama alt: [monster == null]
        if monster == nil
          log_no_monster # Diagrama 1.5: logNoMonster()
        else
          winner = combat(monster) # Diagrama 1.6: combat(monster)

          manage_reward(winner) # Diagrama 1.7: manageReward(winner)
        end
      else
        manage_resurrection # Diagrama 1.8: manageResurrection()
      end

      end_game = finished? # Diagrama 1.9: finished()

      # Diagrama opt: [!endGame]
      unless end_game # == if !end_game
        next_player # Diagrama 1.10: nextPlayer()
      end

      end_game # Diagrama 1.11: return endGame
    end



    # ===========================================================
    # MÉTODOS PRIVADOS
    # ===========================================================
    private

    # Configura el laberinto inicial con monstruos y otras configuraciones necesarias.
    # Este método es llamado desde el constructor.
    # @return Nada (modifica el estado del laberinto y los monstruos)
    def configure_labyrinth
      # Añadir algunos monstruos de ejemplo
      5.times do |i|
        monster = Monster.new("Monster#{i}", Dice.random_intelligence, Dice.random_strength)
        pos = @labyrinth.random_empty_pos
        @labyrinth.add_monster(pos[Labyrinth::ROW], pos[Labyrinth::COL], monster)
        @monsters << monster
      end
    end

    # Actualiza el índice del jugador actual para pasar al siguiente jugador en la lista.
    # @return Nada (modifica el estado del juego)
    def next_player
      @current_player_index = (@current_player_index + 1) % @players.length
    end

    # Métodos de logging para registrar eventos importantes en el juego.
    def log_player_won
      @log += "El jugador ha ganado la partida!\n"
    end

    def log_monster_won
      @log += "El monstruo ha ganado la partid!\n"
    end

    def log_resurrected
      @log += "El jugador ha resucitado\n"
    end

    def log_player_skip_turn
      @log += "El jugador pierde el turno por estar muerto\n"
    end

    def log_player_no_orders
      @log += "El jugador no siguio las indicaciones\n"
    end

    def log_no_monster
      @log += "El jugador se ha movido a una casilla vacia o no le ha sido posible moverse\n"
    end

    def log_rounds(rounds, max)
      @log += "Ronda #{rounds} de #{max}\n"
    end

    # ===========================================================
    # P3 - Métodos privados a implementar
    # ===========================================================

    # Determina la dirección real del movimiento.
    # @param preferred_direction
    # @return Dirección final (la deseada, o la primera válida si la deseada no es posible)
    def actual_direction(preferred_direction)
      current_row = @current_player.row # Diagrama 1.1: getRow()
      current_col = @current_player.col # Diagrama 1.2: getCol()

      valid_moves = @labyrinth.valid_moves(current_row, current_col) # Diagrama 1.3: validMoves(currentRow, currentCol)

      output = @current_player.move(preferred_direction, valid_moves) # Diagrama 1.4: move(preferredDirection, validMoves)

      output # Diagrama: return output
    end

    # Realiza un asalto de combate entre el jugador actual y un monstruo
    # @param monster
    # @ return Ganador del combate (PLAYER O MONSTER)
    def combat(monster)
      rounds = 0
      winner = GameCharacter::PLAYER

      player_attack = @current_player.attack # Diagrama 1.1: attack()

      lose = monster.defend(player_attack) # Diagrama 1.2: defend(playerAttack)

      # Diagrama loop: [(!lose) && (rounds < MAX_ROUNDS)]
      while !lose && (rounds < MAX_ROUNDS)
        winner = GameCharacter::MONSTER
        rounds += 1

        monster_attack = monster.attack # Diagrama 1.3: attack()

        lose = @curren_player.defend(monster_attack) # Diagrama 1.4: defend(monsterAttack)

        # Diagrama opt: [!lose]
        unless lose # == if !lose
          player_attack = @current_player.attack # Diagrama 1.5: attack()

          winner = GameCharacter::PLAYER

          lose = monster.defend(player_attack) # Diagrama 1.6: defend(playerAttack)
        end
      end

      log_rounds(rounds, MAX_ROUNDS) # Diagrama 1.7: logRounds(rounds, MAX_ROUNDS)

      winner # Diagrama: return winner
    end

    # Gestiona la recompensa (si gana PLAYER) o el log (si gana MONSTER).
    # @param winner
    def manage_reward(winner)
      # Diagrama alt: [winner == GameCharacter.PLAYER]
      if winner == GameCharacter::PLAYER
        @current_player.receive_reward # Diagrama 1.1: receiveReard()

        log_player_won # Diagrama 1.2: logPlayerWon()
      else
        log_monster_won # Diagrama 1.3: logMonsterWon()
      end
    end

    # Gestiona la resurrección del jugador si está muerto al inicio del turno.
    def manage_resurrection
      resurrect = Dice.resurrect_player # Diagrama 1.1: resurrectPlayer()

      # Diagrama alt: [resurrect]
      if resurrect
        @current_player.resurrect # Diagrama 1.2: resurrect()

        log_resurrected # Diagrama 1.3: logResurrected()
      else
        log_player_skip_turn # Diagrama 1.4: logPlayerSkipTurn()
      end
    end
  end
end

