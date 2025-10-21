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
    public static final int ROW = 0;
    public static final int COL = 1;
    
    // ===============================
    // ATRIBUTOS DE INSTANCIA
    // ===============================
    private int nRows, nCols;
    private int exitRow, exitCol;
    
    private final Monster[][] monsters;
    private final Player[][] players;
    private final char[][] labyrinth;
    
    // ===============================
    // MÉTODOS PÚBLICOS
    // ===============================

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
    
    /**
     * @return {@code true} si hay un jugador en la casilla de salida.
     */
    public boolean haveAWinner() {
        return players[exitRow][exitCol] != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < nRows; i++) {
            for(int j = 0; j < nCols; j++)
                sb.append(labyrinth[i][j]).append("\t");
            
            sb.append("\n");
        }
        
        return sb.toString();
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
     * Este método se mantiene público, desviándose del UML original, por decisión de disenio
     * para permitir una configuración aleatoria del tablero desde la clase Game. Esta decisión 
     * fue consultada y aprobada por la profesora.
     * @return Array de dos enteros con la fila y columna de una casilla vacía.
     */
    public int[] randomEmptyPos() {
        while(true) {
            int r = Dice.randomPos(nRows);
            int c = Dice.randomPos(nCols);
            
            if(emptyPos(r, c)) return new int[]{r, c};
        }
    }
    
    // ==============================
    // P3 - Métodos públicos a implementar
    // ==============================
    public ArrayList<Directions> validMoves(int row, int col) { throw new UnsupportedOperationException(); }
    public void addBlock(Orientation orientation, int startRow, int startCol, int length) { throw new UnsupportedOperationException(); }
    public Monster putPlayer(Directions direction, Player player) { throw new UnsupportedOperationException(); }
    public void spreadPlayers(ArrayList<Player> players) { throw new UnsupportedOperationException(); }
    
    
    
    // ============================
    // MÉTODOS PRIVADOS
    // ============================
    
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
    
    /**
     * Indica si se puede pisar la celda: debe estar dentro del tablero y ser vacía
     * de monstruo o de salida (no muros).
     * @param row
     * @param col
     * @return 
     */
    private boolean canStepOn(int row, int col) {
        return false;
    }
    
    /**
     * Deja la celda abandonada en estado correcto.
     * Si estaba en combate ('C') pasa a 'M', en caso contrario pasa a '-'.
     * Solo actúa si la posición es válida.ñ
     * @param row
     * @param col 
     */
    private void updateOldPos(int row, int col) {
        if(!posOK(row, col)) return;
        
        if(labyrinth[row][col] == COMBAT_CHAR) labyrinth[row][col] = MONSTER_CHAR;
        else labyrinth[row][col] = EMPTY_CHAR;
        
        players[row][col] = null; // Por coherencia, ninguna referencia del jugador
    } 
    
    /**
     * Calcula la posición a la que se llegaría desde (row, col) avanzando una 
     * casilla en la {@code direction} indicada. No comprueba límites.
     * @param row
     * @param col
     * @param direction
     * @return par [row, col] resultante.
     */
    private int[] dir2Pos(int row, int col, Directions direction) {
        switch(direction) {
            case LEFT : return new int[]{row, col - 1};
            case RIGHT : return new int[]{row, col + 1};
            case UP : return new int[]{row - 1, col};
            case DOWN : return new int[]{row + 1, col};
            default : return new int[]{row, col};
        }
    }
    
    // ===========================================
    // P3 - Métodos privados a implementar
    // ===========================================
    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player) { throw new UnsupportedOperationException(); }
}