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
 * @version 2.4
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
        //Create the model and organize the cells
        board = new SudokuBoard();
        celdas = new TextField[][] {
                {cell00, cell01, cell02, cell03, cell04, cell05},
                {cell10, cell11, cell12, cell13, cell14, cell15},
                {cell20, cell21, cell22, cell23, cell24, cell25},
                {cell30, cell31, cell32, cell33, cell34, cell35},
                {cell40, cell41, cell42, cell43, cell44, cell45},
                {cell50, cell51, cell52, cell53, cell54, cell55}
        };

        //Generate first board
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
    // Create the confirmation alert
    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    confirmacion.setTitle("Confirmar nuevo juego");
    confirmacion.setHeaderText(null);
    confirmacion.setContentText("¿Deseas comenzar un nuevo Sudoku?\nSe perderá el progreso actual.");

    // Show the alert and save the pressed button
    javafx.scene.control.ButtonType respuesta = confirmacion.showAndWait().orElse(javafx.scene.control.ButtonType.CANCEL);


    /*"Displays the confirmation window (showAndWait()),
    waits for the user to press a button (such as OK or CANCEL),
    and stores the button that the user chose in the variable response.
    " The method showAndWait() returns a value of type Optional<ButtonType>.
    This means that it may or may not contain a value,
    depending on whether the user pressed something.
    For example:If the player presses OK,
    it returns ButtonType.OK.If they press Cancel,
    it returns ButtonType.CANCEL.If they close the window without clicking,
    the Optional has no value.
    That is why .orElse(ButtonType.CANCEL) is used:"If nothing was pressed,
    assume it was CANCEL.".*/


    // If the user presses ACCEPT, generate a new board
    if (respuesta == javafx.scene.control.ButtonType.OK) {
        SudokuGenerator generador = new SudokuGenerator();
        int[][] nuevoTablero = generador.generate();
        board.setBoard(nuevoTablero);

        // Clean colors
        limpiarColoresCeldas();

        mostrarTablero();
        ayudasDisponibles=3;
        lblMensaje.setText("Nuevo Sudoku generado.");
    }
    // If you press CANCEL or close the window, continue with the same game
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

                //ASSIGN KEYBOARD LISTENER TO THE 36 CELLS
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

                AlertBox alertBox = new AlertBox();
                alertBox.showWarningAlertBox("Número inválido", "Por favor ingresa un número entre 1 y 6.", null);

                // Highlight cell with error in red
                celda.setStyle("-fx-background-color: #ffb3b3; -fx-border-color: red; -fx-border-width: 2;");

                // Wait 1.5 seconds and clear colors
                PauseTransition pausa = new PauseTransition(Duration.seconds(1.5));
                pausa.setOnFinished(e -> {
                    if (!celda.getStyle().contains("yellow")) {
                        celda.setStyle("");
                    }
                });
                pausa.play();

                celda.clear();

                return;
            }

            if (SudokuValidator.isValid(board.getBoard(), fila, col, valor)) {
                board.setCell(fila, col, valor);
            }
            else {

                AlertBox alertBox = new AlertBox();
                alertBox.showWarningAlertBox("Movimiento no válido", "Ese número rompe las reglas del Sudoku.", null);

                celda.setStyle("-fx-background-color: #ffb3b3; -fx-border-color: red; -fx-border-width: 2;");

                PauseTransition pausa = new PauseTransition(Duration.seconds(1.5));
                pausa.setOnFinished(e -> {
                    if (!celda.getStyle().contains("yellow")) {
                        celda.setStyle("");
                    }
                });
                pausa.play();

                celda.clear();
            }
        } catch (NumberFormatException e) {

            AlertBox alertBox = new AlertBox();
            alertBox.showWarningAlertBox("Entrada inválida", "Solo puedes escribir números.", null);

            celda.setStyle("-fx-background-color: #ffb3b3; -fx-border-color: red; -fx-border-width: 2;");

            PauseTransition pausa = new PauseTransition(Duration.seconds(1.5));
            pausa.setOnFinished(ev -> {
                if (!celda.getStyle().contains("yellow")) {
                    celda.setStyle("");
                }
            });
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
                    AlertBox alertBox = new AlertBox();
                    alertBox.showWarningAlertBox("Incompleto", "Aún hay celdas vacías.", null);
                    return;
                }
            }
        }

        if (SudokuValidator.isBoardValid(matriz)) {
            lblMensaje.setText("¡Felicitaciones! Sudoku correcto.");
            AlertBox alertBox = new AlertBox();
            alertBox.showAlertBox("Correcto", "¡Felicitaciones! Sudoku completo.", null);

        } else {
            lblMensaje.setText("El Sudoku tiene errores.");
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
        // Check if there are still aids available
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

        //Find an empty cell
        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 6; col++) {
                if (tablero[fila][col] == 0) {

                    //Find a valid number from 1 to 6
                    for (int num = 1; num <= 6; num++) {
                        if (SudokuValidator.isValid(tablero, fila, col, num)) {

                            ayudasDisponibles--;

                            // Highlight the cell (visual suggestion only)
                            TextField celda = celdas[fila][col];
                            celda.setStyle("-fx-background-color: yellow;");

                            // Show message on the label
                            lblMensaje.setText("Sugerencia: En la celda (" + (fila + 1) + "," + (col + 1) + ") podrías probar el número " + num);

                            // Show alert to the user
                            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Sugerencia de ayuda");
                            alerta.setHeaderText(null);
                            alerta.setContentText("En la celda (" + (fila + 1) + "," + (col + 1) +
                                                    ") puedes probar el número " + num + ".\n" +
                                                    "Te quedan " + ayudasDisponibles + " ayudas.");
                            alerta.showAndWait();


                            return; // Solo una sugerencia por clic
                        }
                    }
                }
            }
        }

        // If there are no empty cells or suggestions
        Alert sinAyuda = new Alert(Alert.AlertType.INFORMATION);
        sinAyuda.setTitle("Ayuda");
        sinAyuda.setHeaderText(null);
        sinAyuda.setContentText("No hay más sugerencias disponibles.");
        sinAyuda.showAndWait();
    }


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


        AlertBox alertBox = new AlertBox();
        alertBox.showAlertBox("Instrucciones del Sudoku 6x6", instrucciones, null);


    }


    /**
     * Restores the original color (white) to all cells.
     * Used when starting a new game or when you want to clear visual aids.*/

    private void limpiarColoresCeldas() {
        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 6; col++) {
                // Remove any applied style
                celdas[fila][col].setStyle("");
            }
        }
    }


}
