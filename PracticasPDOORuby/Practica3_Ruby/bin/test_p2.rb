#!/usr/bin/env ruby
# frozen_string_literal: true

$LOAD_PATH.unshift(File.expand_path("../lib", __dir__))

require 'Irrgarten/monster'
require 'Irrgarten/player'
require 'Irrgarten/labyrinth'
require 'Irrgarten/game'
require 'Irrgarten/dice'

include Irrgarten

puts "========================================"
puts "=== PRUEBAS DE LA CLASE MONSTER ==="
puts "========================================"

monster = Monster.new("Drácula", 8.5, 9.0)
puts "Monstruo recién creado: #{monster}"
puts "Está muerto? #{monster.dead?}"
puts "Herido 2 veces: #{monster}"
puts "Ataque del monstruo: #{monster.attack.round(2)}"
monster.set_pos(3, 4)
puts "Posición actualizada: #{monster}"
puts

puts "========================================"
puts "=== PRUEBAS DE LA CLASE PLAYER ==="
puts "========================================"

player = Player.new(1, 7.2, 6.8)
puts "Jugador recién creado: #{player}"
puts "Está muerto? #{player.dead?}"
puts "Ataque (sin armas): #{player.attack.round(2)}"
player.set_pos(1, 1)
puts "Jugador herido una vez: #{player}"
player.resurrect
puts "Jugador resucitado: #{player}"
puts

puts "========================================"
puts "=== PRUEBAS DE LA CLASE LABYRINTH ==="
puts "========================================"

lab = Labyrinth.new(5, 6, 4, 5)
puts "--- Laberinto Inicial ---"
puts lab.to_s
puts
puts "Hay ganador? #{lab.have_a_winner?}"

lab.add_monster(2, 2, monster)
puts "--- Laberinto con Monstruo en (2,2) ---"
puts lab.to_s
puts
random_pos = lab.random_empty_pos
puts "Posición vacía aleatoria generada: #{random_pos}"
puts

puts "========================================"
puts "=== PRUEBAS DE LA CLASE GAME ==="
puts "========================================"

game = Game.new(2)
puts "Partida creada con 2 jugadores."
puts

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
puts "Fin del juego? #{game.finished?}"