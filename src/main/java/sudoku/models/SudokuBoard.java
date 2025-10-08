package sudoku.models;

public class SudokuBoard {
    private int[][] board; // tablero 6x6

    public SudokuBoard() {
        board = new int[6][6];
    }

    // Obtener número en una posición
    public int getCell(int row, int col) {
        return board[row][col];
    }

    // Asignar número a una celda
    public void setCell(int row, int col, int value) {
        board[row][col] = value;
    }

    // Obtener el tablero completo
    public int[][] getBoard() {
        return board;
    }

    // Reemplazar el tablero
    public void setBoard(int[][] newBoard) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j] = newBoard[i][j];
            }
        }
    }
}
