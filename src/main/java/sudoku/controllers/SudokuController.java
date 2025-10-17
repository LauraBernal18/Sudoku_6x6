package sudoku.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import sudoku.models.SudokuBoard;
import sudoku.models.SudokuGenerator;
import sudoku.models.SudokuValidator;
import sudoku.models.AlertBox;

import javafx.scene.input.KeyEvent;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Controller class responsible for handling all user interactions
 * and managing the logic for the Sudoku 6x6 game.
 * <p>
 * It connects the user interface (FXML) with the model classes
 * {@link SudokuBoard}, {@link SudokuGenerator}, and {@link SudokuValidator}.
 * </p>
 *
 * @author Martin Alvarez, Laura Bernal
 * @version 2.3
 * @since 2025-2
 */


public class SudokuController {

    private int ayudasDisponibles = 3;

    /** Main grid pane of the Sudoku board. */
    @FXML private GridPane gridPane;

    /** 36 text fields representing Sudoku cells in a 6x6 grid. */
    @FXML private TextField cell00, cell01, cell02, cell03, cell04, cell05;
    @FXML private TextField cell10, cell11, cell12, cell13, cell14, cell15;
    @FXML private TextField cell20, cell21, cell22, cell23, cell24, cell25;
    @FXML private TextField cell30, cell31, cell32, cell33, cell34, cell35;
    @FXML private TextField cell40, cell41, cell42, cell43, cell44, cell45;
    @FXML private TextField cell50, cell51, cell52, cell53, cell54, cell55;

    /** Interface buttons and labels. */
    @FXML private Button btnNuevoJuego;
    @FXML private Button btnVerificar;
    @FXML private Button btnAyuda;
    @FXML private Label lblMensaje;
    // @FXML private TextArea txtInstrucciones;

    /** The logical Sudoku board model. */
    private SudokuBoard board;
    /** Matrix of text fields mapped to board positions. */
    private TextField[][] celdas;

    /**
     * Initializes the controller, creates the board model,
     * and prepares the first Sudoku game.
     */
    @FXML
    public void initialize() {
        //Crear el modelo y organizar las celdas
        board = new SudokuBoard();
        celdas = new TextField[][] {
                {cell00, cell01, cell02, cell03, cell04, cell05},
                {cell10, cell11, cell12, cell13, cell14, cell15},
                {cell20, cell21, cell22, cell23, cell24, cell25},
                {cell30, cell31, cell32, cell33, cell34, cell35},
                {cell40, cell41, cell42, cell43, cell44, cell45},
                {cell50, cell51, cell52, cell53, cell54, cell55}
        };

        //Generar primer tablero
        iniciarJuego();

    }

    /**
     * Starts a new Sudoku game when the application first loads.
     * It generates a new random board and displays it.
     */
    private void iniciarJuego() {

        SudokuGenerator generador = new SudokuGenerator();
        int[][] nuevoTablero = generador.generate();
        board.setBoard(nuevoTablero);
        mostrarTablero();
        lblMensaje.setText("Bienvenido al Sudoku 6x6");
    }


    /**
     * Handles the "New Game" button event.
     * Displays a confirmation alert before generating a new Sudoku board.
     *
     * @param event Action event triggered by the "New Game" button.
     */

    @FXML
private void nuevoJuego(ActionEvent event) {
    // Crear la alerta de confirmación
    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    confirmacion.setTitle("Confirmar nuevo juego");
    confirmacion.setHeaderText(null);
    confirmacion.setContentText("¿Deseas comenzar un nuevo Sudoku?\nSe perderá el progreso actual.");

    // Mostrar la alerta y guardar el botón presionado
    javafx.scene.control.ButtonType respuesta = confirmacion.showAndWait().orElse(javafx.scene.control.ButtonType.CANCEL);


    /*“Muestra la ventana de confirmación (showAndWait()),
    espera a que el usuario presione un botón (como ACEPTAR o CANCELAR),
     y guarda el botón que el usuario eligió en la variable respuesta.”

    El metodo showAndWait() devuelve un valor de tipo Optional<ButtonType>.
    Eso significa que puede o no contener un valor, dependiendo de si el usuario presionó algo.

    Por ejemplo:
    Si el jugador presiona Aceptar, devuelve ButtonType.OK.
    Si presiona Cancelar, devuelve ButtonType.CANCEL.
    Si cierra la ventana sin hacer clic, Optional no tiene valor.

    Por eso se usa el .orElse(ButtonType.CANCEL):
    “Si no presionó nada, asumir que fue CANCELAR”.*/


    // Si el usuario presiona ACEPTAR, generar un nuevo tablero
    if (respuesta == javafx.scene.control.ButtonType.OK) {
        SudokuGenerator generador = new SudokuGenerator();
        int[][] nuevoTablero = generador.generate();
        board.setBoard(nuevoTablero);
        mostrarTablero();
        ayudasDisponibles=3;
        lblMensaje.setText("Nuevo Sudoku generado.");
    }
    // Si presiona CANCELAR o cierra la ventana, continuar con el mismo juego
    else {
        lblMensaje.setText("Continuas con el juego actual.");
    }
}

    /**
     * Displays the Sudoku board in the text fields.
     * Fills in the generated numbers and enables event handling for editable cells.
     */
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

                //ASIGNAR ESCUCHA DE TECLADO A LAS 36 CELDAS
                final int f = fila;
                final int c = col;

                celda.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event){
                        manejarEntrada(celda,f,c);
                    }
                });


            }
        }
    }

    /**
     * Handles user input for each Sudoku cell, validating numbers
     * and ensuring they follow Sudoku rules.
     *
     * @param celda TextField where the user typed.
     * @param fila  Row index of the cell.
     * @param col   Column index of the cell.
     */
    private void manejarEntrada(TextField celda, int fila, int col) {
        String texto = celda.getText();

        try {
            int valor = Integer.parseInt(texto);

            if (valor < 1 || valor > 6) {
               /* mostrarAlerta("Número inválido", "Por favor ingresa un número entre 1 y 6.", celda);
                celda.clear();*/
                AlertBox alertBox = new AlertBox();
                alertBox.showWarningAlertBox("Número inválido", "Por favor ingresa un número entre 1 y 6.", null);

                // Resaltar celda con error en rojo
                celda.setStyle("-fx-background-color: #ffb3b3; -fx-border-color: red; -fx-border-width: 2;");

                // Esperar 1.5 segundos y limpiar colores
                PauseTransition pausa = new PauseTransition(Duration.seconds(1.5));
                pausa.setOnFinished(e -> limpiarColoresCeldas());
                pausa.play();

                celda.clear();

                return;
            }

            if (SudokuValidator.isValid(board.getBoard(), fila, col, valor)) {
                board.setCell(fila, col, valor);
            }
            else {
                /*mostrarAlerta("Movimiento no válido", "Ese número rompe las reglas del Sudoku.", celda);
                celda.clear();*/
                AlertBox alertBox = new AlertBox();
                alertBox.showWarningAlertBox("Movimiento no válido", "Ese número rompe las reglas del Sudoku.", null);

                celda.setStyle("-fx-background-color: #ffb3b3; -fx-border-color: red; -fx-border-width: 2;");

                PauseTransition pausa = new PauseTransition(Duration.seconds(1.5));
                pausa.setOnFinished(e -> limpiarColoresCeldas());
                pausa.play();

                celda.clear();
            }
        } catch (NumberFormatException e) {
           /* mostrarAlerta("Entrada inválida", "Solo puedes escribir números.", celda);
            celda.clear();*/
            AlertBox alertBox = new AlertBox();
            alertBox.showWarningAlertBox("Entrada inválida", "Solo puedes escribir números.", null);

            celda.setStyle("-fx-background-color: #ffb3b3; -fx-border-color: red; -fx-border-width: 2;");

            PauseTransition pausa = new PauseTransition(Duration.seconds(1.5));
            pausa.setOnFinished(ev -> limpiarColoresCeldas());
            pausa.play();

            celda.clear();
        }
    }

    /**
     * Verifies if the Sudoku board is completely and correctly filled.
     *
     * @param event Action event triggered by the "Verify" button.
     */
    @FXML
    public void verificarSudoku(ActionEvent event) {
        int[][] matriz = board.getBoard();

        for (int[] fila : matriz) {
            for (int valor : fila) {
                if (valor == 0) {
                    /*mostrarAlerta("Incompleto", "Aún hay celdas vacías.", null);*/
                    AlertBox alertBox = new AlertBox();
                    alertBox.showWarningAlertBox("Incompleto", "Aún hay celdas vacías.", null);
                    return;
                }
            }
        }

        if (SudokuValidator.isBoardValid(matriz)) {
            lblMensaje.setText("¡Felicitaciones! Sudoku correcto.");
            //mostrarAlerta("Correcto", "¡Felicitaciones! Sudoku completo.", null);
            AlertBox alertBox = new AlertBox();
            alertBox.showAlertBox("Correcto", "¡Felicitaciones! Sudoku completo.", null);

        } else {
            lblMensaje.setText("El Sudoku tiene errores.");
            //mostrarAlerta("Error", "Hay números que no cumplen las reglas.", null);
            AlertBox alertBox = new AlertBox();
            alertBox.showWarningAlertBox("Error", "Hay números que no cumplen las reglas.", null);
        }
    }

    /**
     * Provides a help suggestion to the player by highlighting
     * one empty cell with a valid possible number.
     * The suggested cell is highlighted for 3 seconds.
     */
    @FXML
    private void pedirAyuda() {
        // Verificar si aún quedan ayudas disponibles
        if (ayudasDisponibles <= 0) {
            Alert sinAyuda = new Alert(Alert.AlertType.INFORMATION);
            sinAyuda.setTitle("Ayuda");
            sinAyuda.setHeaderText(null);
            sinAyuda.setContentText("No te quedan ayudas disponibles.");
            lblMensaje.setText("Sin sugerencias");
            sinAyuda.showAndWait();
            return;
        }

        int[][] tablero = board.getBoard();

        // Buscar una celda vacía
        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 6; col++) {
                if (tablero[fila][col] == 0) {

                    // Buscar un número válido del 1 al 6
                    for (int num = 1; num <= 6; num++) {
                        if (SudokuValidator.isValid(tablero, fila, col, num)) {

                            ayudasDisponibles--;

                            // Resaltar la celda (solo sugerencia visual)
                            TextField celda = celdas[fila][col];
                            celda.setStyle("-fx-background-color: yellow;");

                            // Mostrar mensaje en la etiqueta
                            lblMensaje.setText("Sugerencia: En la celda (" + (fila + 1) + "," + (col + 1) + ") podrías probar el número " + num);

                            // Mostrar alerta al usuario
                            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Sugerencia de ayuda");
                            alerta.setHeaderText(null);
                            alerta.setContentText("En la celda (" + (fila + 1) + "," + (col + 1) +
                                                    ") puedes probar el número " + num + ".\n" +
                                                    "Te quedan " + ayudasDisponibles + " ayudas.");
                            alerta.showAndWait();

                            // Esperar 3 segundos y luego quitar el color
                            /*javafx.animation.PauseTransition pausa = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(3));
                            pausa.setOnFinished(e -> celda.setStyle(""));
                            pausa.play();
                               */

                            return; // Solo una sugerencia por clic
                        }
                    }
                }
            }
        }

        // Si no hay celdas vacías o sugerencias
        Alert sinAyuda = new Alert(Alert.AlertType.INFORMATION);
        sinAyuda.setTitle("Ayuda");
        sinAyuda.setHeaderText(null);
        sinAyuda.setContentText("No hay más sugerencias disponibles.");
        sinAyuda.showAndWait();
    }
    /*
    /**
     * Displays different types of alert messages to the user.
     * Optionally highlights a cell in red for 1 second if an error is related to that cell.
     *
     * @param titulo  Alert window title.
     * @param mensaje Text message shown in the alert.
     * @param celda   The text field to highlight (can be null).
     */
    /*private void mostrarAlerta(String titulo, String mensaje, TextField celda) {
        // Si se pasa una celda, resáltala en rojo
        if (celda != null) {
            celda.setStyle("-fx-background-color: #ffb3b3; -fx-border-color: red; -fx-border-width: 2;");
        }

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();

        // Después de cerrar la alerta, restaurar el estilo
        if (celda != null) {
            // Esperar 1 segundo antes de restaurar el color original
            PauseTransition pausa = new PauseTransition(Duration.seconds(1));
            pausa.setOnFinished(e -> celda.setStyle(""));
            pausa.play();
        }
    }*/

    /**
     * Displays the game instructions in a pop-up alert window.
     *
     * @param event Action event triggered by the "How to Play" button.
     */
    @FXML
    void onActionHowToPlayButton(ActionEvent event) {
        String instrucciones = """
            Cómo jugar Sudoku 6x6:

            1) Cada fila debe contener los números del 1 al 6 sin repetir.
            2) Cada columna debe contener los números del 1 al 6 sin repetir.
            3) Cada bloque de 2x3 debe tener también los números del 1 al 6 sin repetir.
            4) Usa solo números del 1 al 6.
            5) Si ingresas un número incorrecto, el sistema te avisará.
            6) Puedes pedir ayuda con el botón 'Ayuda'.
            7) Para comenzar una nueva partida, presiona 'Nuevo Juego'.
            """;

        /*Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Instrucciones del Sudoku 6x6");
        alerta.setHeaderText(null);
        alerta.setContentText(instrucciones);
        alerta.showAndWait();*/
        AlertBox alertBox = new AlertBox();
        alertBox.showAlertBox("Instrucciones del Sudoku 6x6", instrucciones, null);


    }


    /**
     * Restores the original color (white) to all cells.
     * Used when starting a new game or when you want to clear visual aids.*/

    private void limpiarColoresCeldas() {
        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 6; col++) {
                celdas[fila][col].setStyle(""); // Quita cualquier estilo aplicado
            }
        }
    }


}
