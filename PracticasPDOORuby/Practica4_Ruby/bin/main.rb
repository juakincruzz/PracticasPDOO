#!/usr/bin/env ruby
# frozen_string_literal: true

# Añadir la carpeta 'lib' al path de carga para encontrar los módulos
$LOAD_PATH.unshift(File.join(File.dirname(__FILE__), '..', 'lib'))

require 'Irrgarten/game'
require 'UI/text_ui'
require 'Controller/controller'

# --- Programa Principal ---

# 1. Crear el Modelo (juego con 2 jugadores, por ejemplo)
game = Irrgarten::Game.new(2)

# 2. Crear la Vista
ui = UI::TextUI.new

# 3. Crear el Controlador (pasándole Modelo y Vista)
controller = Controller::Controller.new(game, ui)

# 4. Arrancar el juego
controller.play