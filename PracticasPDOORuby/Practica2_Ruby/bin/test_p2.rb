# frozen_string_literal: true

#!/usr/bin/env ruby
# frozen_string_literal: true
$LOAD_PATH.unshift(File.expand_path("../lib", __dir__))

# Cargo las clases necesarias para el test
require 'Irrgarten/monster'
require 'Irrgarten/player'
require 'Irrgarten/labyrinth'
require 'Irrgarten/game'
require 'Irrgarten/dice'
require 'Irrgarten/weapon'
require 'Irrgarten/shield'

include Irrgarten

# Pruebo la clase monstruo
puts "==============================="
puts "PRUEBA DE LA CLASE MONSTER"
puts "==============================="

monster = Monster.new("m1", 10, 5, 3)
puts "Monstruo creado: #{monster}"

# Pruebas de estado del monstruo con los métodos de la clase
puts "¿Está muerto?: #{monster.dead?}" # Debe salir false
monster.got_wounded
monster.got_wounded
puts "El monstruo ha sido herido dos veces: #{monster}"
puts "Ataque del monstruo: #{monster.attack.round(2)}" # Valor aleatorio
monster.set_pos(3, 4)
puts "Posición del monstruo actualizada: #{monster}"

#Pruebo la clase jugador
puts "==============================="
puts "PRUEBA DE LA CLASE PLAYER"
puts "==============================="

player = Player.new(0, 7.2, 6.8)
puts "Jugador creado: #{player}"

#Pruebas de estado del jugador con los métodos de la clase
puts "¿Está muerto?: #{player.dead?}" # Debe salir false
player.got_wounded
player.got_wounded
puts "El jugador " + player.name + " ha sido herido dos veces: #{player}"
puts "Ataque (sin armas): #{player.attack.round(2)}" # Valor aleatorio
puts "Energía defensiva (sin escudo): #{player.defensive_energy.round(2)}" # Valor aleatorio
player.set_pos(1, 1)
puts "Posición del jugador actualizada: #{player}"
puts "Golpes consecutivos conseguidos por el jugador: #{player.inc_consecutive_hits}"

#Pruebas de resurrección del jugador
player.resurrect
puts "Jugador resucitado: #{player}"
puts

# Pruebas de la clase Labyrinth
puts "==============================="
puts "PRUEBA DE LA CLASE LABYRINTH"
puts "==============================="

# Creación del labertinto
labyrinth = Labyrinth.new(5, 6, 4, 5) # 5x6 con salida en (4,5)
puts "---Laberinto creado---"
puts labyrinth.to_s
puts

# Pruebas de métodos
puts "¿Hay ganador?: #{labyrinth.have_a_winner?}" # Debe salir false
puts "¿Posición (2, 2) está vacía?: #{labyrinth.empty_pos?(2, 2)}" # Debe salir true
puts "¿Posición (4, 5) es la salida?: #{labyrinth.exit_pos?(4, 5)}" # Debe salir true
puts "¿Posición (10, 10) es correcta?: #{labyrinth.pos_ok?(10, 10)}" # Debe salir false

# Añadir un monstruo
labyrinth.add_monster(2, 2, monster)
puts "--- Laberinto con Monstruo en (2,2) ---"
puts labyrinth.to_s
puts
puts "Posición (2,2) está vacía? #{labyrinth.empty_pos?(2, 2)}" # false
puts "Posición (2,2) es de monstruo? #{labyrinth.monster_pos(2, 2)}" # true
random_pos = labyrinth.random_empty_pos
puts "Posición vacía aleatoria generada: #{random_pos}"
puts

# --- 5. Pruebas de la clase Game ---
puts "========================================"
puts "=== PRUEBAS DE LA CLASE GAME ==="
puts "========================================"

# Creación de una partida con 2 jugadores
game = Game.new(2)
puts "Partida creada con 2 jugadores."
puts

# Obtener y mostrar el estado inicial del juego
puts "--- Estado Inicial del Juego ---"
initial_state = game.get_game_state
puts "Laberinto:"
puts initial_state.labyrinth
puts "Jugadores:"
puts initial_state.players
puts "Monstruos:"
puts initial_state.monsters
puts "Jugador Actual: #{initial_state.current_player}"
puts "Hay ganador? #{initial_state.winner}"
puts "Log: '#{initial_state.log}'"
puts
puts "Fin del juego? #{game.finished?}" # Debería ser false
