package sudoku.controllers;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import sudoku.models.SudokuBoard;
import sudoku.models.SudokuGenerator;
import sudoku.models.SudokuValidator;



public class SudokuController {

    // Referencias al tablero y celdas
    @FXML private GridPane gridPane;

    // 36 TextField (ya definidos en tu FXML)
    @FXML private TextField cell00, cell01, cell02, cell03, cell04, cell05;
    @FXML private TextField cell10, cell11, cell12, cell13, cell14, cell15;
    @FXML private TextField cell20, cell21, cell22, cell23, cell24, cell25;
    @FXML private TextField cell30, cell31, cell32, cell33, cell34, cell35;
    @FXML private TextField cell40, cell41, cell42, cell43, cell44, cell45;
    @FXML private TextField cell50, cell51, cell52, cell53, cell54, cell55;

    // Botones e interfaz
    @FXML private Button btnNuevoJuego;
    @FXML private Button btnVerificar;
    @FXML private Button btnAyuda;
    @FXML private Label lblMensaje;
    @FXML private TextArea txtInstrucciones;

    // Modelo del tablero
    private SudokuBoard board;
    private TextField[][] celdas;

    // Inicialización
    @FXML
    public void initialize() {
        board = new SudokuBoard();
        celdas = new TextField[][] {
                {cell00, cell01, cell02, cell03, cell04, cell05},
                {cell10, cell11, cell12, cell13, cell14, cell15},
                {cell20, cell21, cell22, cell23, cell24, cell25},
                {cell30, cell31, cell32, cell33, cell34, cell35},
                {cell40, cell41, cell42, cell43, cell44, cell45},
                {cell50, cell51, cell52, cell53, cell54, cell55}
        };

        cargarNuevoJuego();

        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 6; col++) {
                TextField celda = celdas[fila][col];

                // Crear copias locales (efectivamente finales)
                int f = fila;
                int c = col;

                celda.setOnKeyReleased(e -> manejarEntrada(celda, f, c));
            }
        }



    }

    // Generar nuevo tablero
    @FXML
    private void cargarNuevoJuego() {
        SudokuGenerator generador = new SudokuGenerator();
        int[][] nuevoTablero = generador.generate();
        board.setBoard(nuevoTablero);
        mostrarTablero();
        System.out.println(SudokuValidator.isBoardValid(board.getBoard()));

        lblMensaje.setText("Nuevo Sudoku generado.");
    }

    // Mostrar los valores del modelo en los TextField
    private void mostrarTablero() {
        int[][] matriz = board.getBoard();

        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 6; col++) {
                TextField celda = celdas[fila][col];
                int valor = matriz[fila][col];

                if (valor != 0) {
                    celda.setText(String.valueOf(valor));
                    celda.setEditable(false);
                }
                else {
                    celda.setText("");
                    celda.setEditable(true);
                }


            }
        }
    }

    // Manejar entrada del jugador
    private void manejarEntrada(TextField celda, int fila, int col) {
        String texto = celda.getText();

        if (texto.isEmpty()) {
            board.setCell(fila, col, 0);
            return;
        }

        try {
            int valor = Integer.parseInt(texto);

            if (valor < 1 || valor > 6) {
                mostrarAlerta("Número inválido", "Por favor ingresa un número entre 1 y 6.");
                celda.clear();
                return;
            }

            if (SudokuValidator.isValid(board.getBoard(), fila, col, valor)) {
                board.setCell(fila, col, valor);
            } else {
                mostrarAlerta("Movimiento no válido", "Ese número rompe las reglas del Sudoku.");
                celda.clear();
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Entrada inválida", "Solo puedes escribir números.");
            celda.clear();
        }
    }

    // Verificar si el Sudoku está completo y correcto
    @FXML
    public void verificarSudoku(ActionEvent event) {
        int[][] matriz = board.getBoard();

        for (int[] fila : matriz) {
            for (int valor : fila) {
                if (valor == 0) {
                    mostrarAlerta("Incompleto", "Aún hay celdas vacías.");
                    return;
                }
            }
        }

        if (SudokuValidator.isBoardValid(matriz)) {
            lblMensaje.setText("¡Felicitaciones! Sudoku correcto.");
            mostrarAlerta("Correcto", "¡Felicitaciones! Sudoku completo.");
        } else {
            lblMensaje.setText("El Sudoku tiene errores.");
            mostrarAlerta("Error", "Hay números que no cumplen las reglas.");
        }
    }

    // Mostrar ayuda (basica, sin IA)
    @FXML
    private void pedirAyuda() {
        mostrarAlerta("Ayuda", "Busca celdas donde solo haya un número posible según las reglas.");
    }

    // Mostrar mensajes
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
