/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author joaquin
 */
public class GameState {
    private final String labyrinth; // Representacion del laberinto.
    private final String players;   // Representacion de los jugadores.
    private final String monsters; // Representacion de los monstruos.
    private int currentPlayer; // Indice del jugador actual.
    private final boolean winner; // Ganador?
    private final String log; // Mensajes recientes.

    public GameState(String labyrinth, String players, String monsters, int currentPlayer, boolean winner, String log) {
        this.labyrinth = labyrinth;
        this.players = players;
        this.monsters = monsters;
        this.currentPlayer = currentPlayer;
        this.winner = winner;
        this.log = log;
    }

    public int getCurrentPlayer() { return currentPlayer; }
    public String getLabyrinth() { return labyrinth; }
    public String getPlayers() { return players; }
    public String getMonsters() { return monsters; }
    public boolean isWinner() { return winner; }
    public String getLog() { return log; }

    @Override
    public String toString() {
        return "GameState{" + "labyrinth=" + labyrinth + ", players=" + players + ", monsters=" + monsters + 
                    ", currentPlayer=" + currentPlayer + ", winner=" + winner + ", log=" + log + '}';
    }
}
