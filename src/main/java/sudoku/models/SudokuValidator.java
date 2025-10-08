package sudoku.models;

/**
 * Clase encargada de validar los números y el tablero del Sudoku 6x6.
 * Se usa tanto en la generación del Sudoku como durante el juego
 * para verificar que los números cumplan las reglas.
 */
public class SudokuValidator {

    /**
     * Verifica si un número puede colocarse en la posición indicada.
     * No se puede repetir en la fila, columna o bloque 2x3.
     *
     * @param board tablero del Sudoku
     * @param row fila donde se quiere colocar el número
     * @param col columna donde se quiere colocar el número
     * @param num número a validar
     * @return true si el número es válido, false si rompe una regla
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
     * Verifica si el tablero completo es válido.
     * Revisa que no haya duplicados en filas, columnas o bloques.
     *
     * @param board tablero del Sudoku 6x6
     * @return true si todo el tablero es válido, false si hay errores
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
