package irrgarten;

import java.util.ArrayList;
/**
 * Representa el laberinto del juego como tres tablas paralelas:
 * <ul>
 *          <li> {@code monsters[r][c]}:    referencia al monstruo en la celda (r, c) o {@code null}. </li>  
 *          <li> {@code players[r][c]}:            referencia al jugador en la celda (r, c) o {@code null}. </li>
 *          <li> {@code cells[r][c]}:                 carácter con el estado visual de la celda: 
 *                  'X' (obstáculo), '-' (vacía), 'M' (monstruo)
 *                  'E' (salida), 'C' (combat: jugador y monstruo) </li>
 * </ul>
 * @author Joaquín Cruz Lorenzo
 */
public class Labyrinth {
    // ===============================
    // CONSTANTES DE CLASE
    // ===============================
    private static final char BLOCK_CHAR = 'X';
    private static final char EMPTY_CHAR = '-';
    private static final char MONSTER_CHAR = 'M';
    private static final char COMBAT_CHAR = 'C';
    private static final char EXIT_CHAR = 'E';
    private static final int ROW = 0;
    private static final int COL = 1;
    
    // ===============================
    // ATRIBUTOS DE INSTANCIA
    // ===============================
    private int nRows, nCols;
    private int exitRow, exitCol;
    
    private final Monster[][] monsters;
    private final Player[][] players;
    private final char[][] labyrinth;

    /**
     * Crea el laberinto e inicializa todas las celdas como vacías ('-'),
     * fijando la casilla de salida en {@code (exitRow, exitCol)} a 'E'.
     * 
     * @param nRows      número de filas
     * @param nCols       número de columnas
     * @param exitRow   fila de la salida
     * @param exitCol    columna de la salida
     */
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        
        monsters = new Monster[nRows][nCols];
        players = new Player[nRows][nCols];
        labyrinth = new char[nRows][nCols];
        
        for (int i = 0; i < nRows; i++)
            for (int j = 0; j < nCols; j++)
                labyrinth[i][j] = EMPTY_CHAR;
        
        labyrinth[exitRow][exitCol] = EXIT_CHAR;
    }
    
    // =============================
    // MÉTODOS A IMPLEMENTAR EN P2
    // =============================´T
    
    /**
     * @return {@code true} si hay un jugador en la casilla de salida.
     */
    public boolean haveAWinner() {
        return players[exitRow][exitCol] != null;
    }

    @Override
    public String toString() {
        return "Labyrinth{" + "nRows=" + nRows + ", nCols=" + nCols + ", exitRow=" + exitRow + ", exitCol=" + exitCol + ", monsters=" + monsters + ", players=" + players + ", labyrinth=" + labyrinth + '}';
    }
    
    /**
     * Añade un monstruo si la posición es válida y está vacía.
     * Marca la celda con 'M' y fija su posición.
     * 
     * @param row           fila destino
     * @param col            columna destino
     * @param monster  monstruo a colocar
     */
    public void addMonster(int row, int col, Monster monster) {
        if (posOK(row, col) && emptyPos(row, col)) {
            monsters[row][col] = monster;
            labyrinth[row][col] = MONSTER_CHAR;
            monster.setPos(row, col);
        }
    }
    
    /**
     * 
     * @param row
     * @param col
     * @return {@code true} si (row, col) está dentro del tablero.
     */
    private boolean posOK(int row, int col) {
        return row >= 0 && row < nRows && col >= 0 && col < nCols;
    }
    
    /**
     * 
     * @param row
     * @param col
     * @return {@code true} si (row, col) es vacío
     */
    private boolean emptyPos(int row, int col) {
        return labyrinth[row][col] == EMPTY_CHAR;
    }
    
    /**
     * 
     * @param row
     * @param col
     * @return {@code true} si (row, col) es un monstruo
     */
    private boolean monsterPos(int row, int col) {
        return labyrinth[row][col] == MONSTER_CHAR;
    }
    
    /**
     * 
     * @param row
     * @param col
     * @return {@code true} si (row, col) está en exit
     */
    private boolean exitPos(int row, int col) {
        return labyrinth[row][col] == EXIT_CHAR;
    }
    
    /**
     * 
     * @param row
     * @param col
     * @return {@code true} si (row, col) es combate
     */
    private boolean combatPos(int row, int col) {
        return labyrinth[row][col] == COMBAT_CHAR;
    }
    
    private boolean canStepOn(int row, int col) {
        return false;
    }
    
    /**
     * 
     * @param row
     * @param col
     * @return 
     */
    public ArrayList<Directions> validMoves(int row, int col) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * 
     * @param orientation
     * @param startRow
     * @param startCol
     * @param length 
     */
    public void addBlock(Orientation orientation, int startRow, int startCol, int length) {
        throw new UnsupportedOperationException(); 
    }
    
    /**
     * 
     * @param direction
     * @param player
     * @return 
     */
    public Monster putPlayer(Directions direction, Player player) {
        throw new UnsupportedOperationException(); 
    }
    
    /**
     * 
     * @param players 
     */
    public void spreadPlayers(ArrayList<Player> players) {
        throw new UnsupportedOperationException(); 
    }
}