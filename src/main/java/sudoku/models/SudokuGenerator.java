package sudoku.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SudokuGenerator {
    private Random random = new Random();

    public int[][] generate() {
        int[][] board = new int[6][6];
        fillBoard(board);
        removeCellsForPuzzle(board);
        return board;
    }

    // Llena el tablero completo respetando las reglas
    private boolean fillBoard(int[][] board) {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 6; num++) {
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

    // Reglas básicas del Sudoku 6x6
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

    // Elimina algunas celdas para que el jugador resuelva
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

                // Mezcla las posiciones y deja solo dos números
                Collections.shuffle(positions);
                for (int i = 2; i < positions.size(); i++) {
                    int[] pos = positions.get(i);
                    board[pos[0]][pos[1]] = 0; // borrar (dejar vacío)
                }
            }
        }
    }
}

