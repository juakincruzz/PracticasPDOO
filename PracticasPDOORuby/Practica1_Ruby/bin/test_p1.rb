# frozen_string_literal: true

#!/usr/bin/env ruby
# frozen_string_literal: true
$LOAD_PATH.unshift(File.expand_path("../lib", __dir__))

# Test de la primera parte del proyecto Irrgarten
require "Irrgarten/directions"
require "Irrgarten/orientation"
require "Irrgarten/game_character"
require "Irrgarten/weapon"
require "Irrgarten/shield"
require "Irrgarten/dice"
require "Irrgarten/game_state"

include Irrgarten

# Muestra de los valores de los enumerados Directions, Orientation y GameCharacter
puts "=== Enumerados ==="
p Directions::ALL
p Orientation::ALL
p GameCharacter::ALL
puts

# Prueba la creacion y uso de Weapon y Shield
puts "=== Weapon & Shield ==="
w1 = Weapon.new(2.0, 5)
w2 = Weapon.new(1.5, 0)
s1 = Shield.new(3.0, 4)
s2 = Shield.new(1.0, 1)

puts w1  # W[2.0, 5]
puts w2  # W[1.5, 0]
puts s1  # S[3.0, 4]
puts s2  # S[1.0, 1]

puts "w1.attack -> #{w1.attack}"
puts "w2.attack -> #{w2.attack}"
puts "s1.protect -> #{s1.protect}"
puts "s2.protect -> #{s2.protect}"
puts "s2.protect -> #{s2.protect}" # ya 0 usos
puts

# Pruebas de los metodos de la clase Dice con 100 iteraciones
puts "=== Dice (100 iteraciones) ==="
n = 100

# Aqui lo que se hace es que imprime una lista del 0 a 9 diciendo cuantas veces ha salido cada numero
# por ejemplo si el 0 sale 10 veces saldra "0: 10"
pos = Array.new(10, 0)
n.times { pos[Dice.random_pos(10)] += 1 }
puts "random_pos(10): " + pos.each_with_index.map { |c,i| "#{i}:#{c}" }.join(", ")

# Seleccion de jugador inicial entre 3 jugadores
who = [0,0,0]
n.times { who[Dice.who_starts(3)] += 1 }
puts "who_starts(3): 0=#{who[0]}, 1=#{who[1]}, 2=#{who[2]}"

# Estadisticas de resurrecion de jugadores
resurrects = n.times.count { Dice.resurrect_player }
puts "resurrect_player ~30%: #{resurrects}/#{n}"

# Recompensas de poder de armas, escudos y salud
wr = n.times.map { Dice.weapons_reward }
puts "weapons_reward rango: [#{wr.min}, #{wr.max}] (esperado [0,2])"

sr = n.times.map { Dice.shields_reward }
puts "shields_reward rango: [#{sr.min}, #{sr.max}] (esperado [0,3])"

hr = n.times.map { Dice.health_reward }
puts "health_reward rango: [#{hr.min}, #{hr.max}] (esperado [0,5])"

# Rango de poder de armas y escudos
wp = n.times.map { Dice.weapon_power }
sp = n.times.map { Dice.shields_power }
printf "weapon_power [min=%.3f, max=%.3f) esperado [0,3)\n", wp.min, wp.max
printf "shield_power [min=%.3f, max=%.3f) esperado [0,2)\n", sp.min, sp.max

# Rangos de usos restantes
ul = n.times.map { Dice.uses_left }
puts "uses_left rango: [#{ul.min}, #{ul.max}] (esperado [0,5])"

# Rangos de intensidad
intv = n.times.map { Dice.intensity(5.0) }
printf "intensity(5.0) [min=%.3f, max=%.3f) esperado [0,5)\n", intv.min, intv.max

# Estadisticas de descarte de armas/escudos con distintos usos restantes
(0..5).each do |uses|
  t = n.times.count { Dice.discard_element(uses) }
  puts "discard_element(#{uses}) -> true aprox #{t}/#{n}"
end

puts
puts "=== discard en Weapon/Shield ==="
puts "w1.discard? #{w1.discard}"
puts "s1.discard? #{s1.discard}"

puts
puts "=== GameState ==="
# Prueba de la clase GameState
gs = GameState.new(
  "Laberinto(5x5)",
  "P0(1,1), P1(3,2)",
  "M0(2,4)",
  0,
  false,
  "Comienza la partida"
)
puts gs
puts "Jugador actual: #{gs.current_player}"
puts "¿Hay ganador? #{gs.winner}"
