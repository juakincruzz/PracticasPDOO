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
    
    // 
}