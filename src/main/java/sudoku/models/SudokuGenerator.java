package sudoku.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Generates valid Sudoku 6x6 boards and prepares playable puzzles.

 * This class is responsible for creating a fully solved Sudoku grid
 * that follows the 6x6 Sudoku rules (2x3 sub-grids) and then removing
 * some cells to form a puzzle that the player can solve.
 *
 * @author  Martin Alvarez, Laura Bernal
 * @version 1.5
 * @since   2025-2
 */

public class SudokuGenerator {

    /** Random number generator used for shuffling and randomization. */

    private Random random = new Random();

    /**
     * Creates a new Sudoku puzzle.

     * The method first generates a complete valid Sudoku solution,
     * then removes selected cells to create a playable puzzle.
     *
     * @return a 6x6 integer matrix representing the Sudoku puzzle
     */

    public int[][] generate() {
        int[][] board = new int[6][6];
        fillBoard(board);
        removeCellsForPuzzle(board);
        return board;
    }

    /**
     * Recursively fills the Sudoku board with valid numbers following
     * Sudoku 6x6 rules (no repetition in row, column, or 2x3 box).
     *
     * @param board the 6x6 board to fill
     * @return {@code true} if the board was successfully filled, otherwise {@code false}
     */

    private boolean fillBoard(int[][] board) {
        Random random = new Random();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (board[row][col] == 0) {

                    int[] numeros = {1,2,3,4,5,6};


                    for (int num = 1; num <= 6; num++) {
                        int index = random.nextInt(6);
                        num = numeros[index];

                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (fillBoard(board))
                                return true;
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether a given number can be placed at a specific cell
     * without violating Sudoku 6x6 constraints.
     *
     * @param board the Sudoku board to check
     * @param row   the row index (0–5)
     * @param col   the column index (0–5)
     * @param num   the number to validate (1–6)
     * @return {@code true} if the number can be placed, otherwise {@code false}
     */

    private boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < 6; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        int boxRow = row / 2 * 2;
        int boxCol = col / 3 * 3;

        for (int r = 0; r < 2; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[boxRow + r][boxCol + c] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Removes selected cells from the filled Sudoku grid
     * to create a puzzle for the player to solve.

     * The method iterates through each 2x3 block, shuffles cell positions,
     * and keeps only two random numbers per block.
     *
     * @param board the fully solved 6x6 Sudoku board
     */

    private void removeCellsForPuzzle(int[][] board) {
        // Recorre los bloques 2x3
        for (int startRow = 0; startRow < 6; startRow += 2) {
            for (int startCol = 0; startCol < 6; startCol += 3) {

                // Guarda todas las posiciones (r, c) de este bloque
                ArrayList<int[]> positions = new ArrayList<>();
                for (int r = startRow; r < startRow + 2; r++) {
                    for (int c = startCol; c < startCol + 3; c++) {
                        positions.add(new int[]{r, c});
                    }
                }
                // Mezcla las posiciones
                for (int i = 0; i < positions.size(); i++) {
                    int randomIndex = random.nextInt(positions.size());
                    int[] temp = positions.get(i);
                    positions.set(i, positions.get(randomIndex));
                    positions.set(randomIndex, temp);
                }

                // Deja solo dos números
                for (int i = 2; i < positions.size(); i++) {
                    int[] pos = positions.get(i);
                    board[pos[0]][pos[1]] = 0; // borrar (dejar vacío)
                }
            }
        }
    }
}

