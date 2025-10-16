package sudoku.models;

/**
 * Provides validation logic for Sudoku 6x6 grids.

 * This class ensures that numbers placed on the board
 * follow Sudoku rules: no repeated numbers in any row,
 * column, or 2x3 sub-grid.

 * It is used both during puzzle generation and while
 * the player is entering values.
 *
 * @author  Martin Alvarez,Laura Bernal
 * @version 1.2
 * @since   2025-10
 */

public class SudokuValidator {

    /**
     * Checks if a specific number can be placed in the given cell
     * without violating Sudoku rules (no duplicates in row, column, or block).
     *
     * @param board the 6x6 Sudoku board
     * @param row   the target row index (0–5)
     * @param col   the target column index (0–5)
     * @param num   the number to validate (1–6)
     * @return {@code true} if the number is valid, {@code false} otherwise
     */

    public static boolean isValid(int[][] board, int row, int col, int num) {

        // Verificar si el número ya está en la fila
        for (int c = 0; c < 6; c++) {
            if (board[row][c] == num) {
                return false;
            }
        }

        // Verificar si el número ya está en la columna
        for (int r = 0; r < 6; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }

        // Verificar el bloque 2x3
        int startRow = (row / 2) * 2; // fila de inicio del bloque
        int startCol = (col / 3) * 3; // columna de inicio del bloque

        for (int r = startRow; r < startRow + 2; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (board[r][c] == num) {
                    return false;
                }
            }
        }

        return true; // El número cumple todas las reglas
    }


    /**
     * Checks whether the entire Sudoku board is valid.

     * Ensures there are no duplicate values across rows,
     * columns, or 2x3 sub-grids.
     *
     * @param board the 6x6 Sudoku board
     * @return {@code true} if the board is valid, {@code false} otherwise
     */

    public static boolean isBoardValid(int[][] board) {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                int num = board[row][col];
                if (num != 0) { // Si hay un número en la celda
                    // Borra temporalmente el número para verificar
                    board[row][col] = 0;
                    // Comprueba si sigue siendo válido
                    if (!isValid(board, row, col, num)) {
                        // Restaura y devuelve false
                        board[row][col] = num;
                        return false;
                    }
                    // Restaura el número
                    board[row][col] = num;
                }
            }
        }
        return true;
    }
}
