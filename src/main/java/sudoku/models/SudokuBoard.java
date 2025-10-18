package sudoku.models;

/**
 * Represents the 6x6 Sudoku game board.

 * This class stores the Sudoku grid in a 6x6 integer matrix and provides
 * basic getter and setter methods to access and update cell values.
 *
 * @author  Martin Alvarez, Laura Bernal
 * @version 1.3
 * @since   2025-2
 */

public class SudokuBoard {

    /** Two-dimensional array that stores the Sudoku grid values (6x6). */
    private int[][] board; // 6x6 board

    public SudokuBoard() {

        /**
         * Default constructor that initializes an empty Sudoku board (all zeros).
         */
        board = new int[6][6];
    }

    /**
     * Returns the value stored in a specific cell of the board.
     *
     * @param row the row index (0–5)
     * @param col the column index (0–5)
     * @return the integer value stored at the specified cell
     */

    public int getCell(int row, int col) {

        return board[row][col];
    }

    /**
     * Sets a specific value in a given cell of the board.
     *
     * @param row   the row index (0–5)
     * @param col   the column index (0–5)
     * @param value the value to assign (should be between 1 and 6, or 0 if empty)
     */

    public void setCell(int row, int col, int value) {

        board[row][col] = value;
    }

    /**
     * Returns the entire Sudoku board as a 6x6 matrix.
     *
     * @return a two-dimensional array representing the board
     */

    public int[][] getBoard() {

        return board;
    }

    /**
     * Replaces the current board with a new one.
     * This method copies all values from the provided matrix to ensure
     * that the internal state is updated safely.
     *
     * @param newBoard the new 6x6 integer matrix to replace the current board
     */
    public void setBoard(int[][] newBoard) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j] = newBoard[i][j];
            }
        }
    }
}
