package sudoku.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import sudoku.models.*;

import javafx.scene.input.KeyEvent;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Controller class responsible for handling all user interactions
 * and managing the logic for the Sudoku 6x6 game.

 * It connects the user interface (FXML) with the model classes
 * SudokuBoard, SudokuGenerator, SudokuValidator.
 *
 * @author Martin Alvarez, Laura Bernal
 * @version 2.4
 * @since 2025-2
 */


public class SudokuController {

    private int availableAids = 3;
    private final IAlertBox alertBox = new AlertBox();


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
    @FXML private Button btnNewGame;
    @FXML private Button btnVerify;
    @FXML private Button btnHelp;
    @FXML private Label lblMessage;

    /** The logical Sudoku board model. */
    private SudokuBoard board;
    /** Matrix of text fields mapped to board positions. */
    private TextField[][] cells;

    /**
     * Initializes the controller, creates the board model,
     * and prepares the first Sudoku game.
     */
    @FXML
    public void initialize() {
        //Create the model and organize the cells
        board = new SudokuBoard();
        cells = new TextField[][] {
                {cell00, cell01, cell02, cell03, cell04, cell05},
                {cell10, cell11, cell12, cell13, cell14, cell15},
                {cell20, cell21, cell22, cell23, cell24, cell25},
                {cell30, cell31, cell32, cell33, cell34, cell35},
                {cell40, cell41, cell42, cell43, cell44, cell45},
                {cell50, cell51, cell52, cell53, cell54, cell55}
        };

        //Generate first board
        startGame();

    }

    /**
     * Starts a new Sudoku game when the application first loads.
     * It generates a new random board and displays it.
     */
    private void startGame() {

        SudokuGenerator generator = new SudokuGenerator();
        int[][] newBoard = generator.generate();
        board.setBoard(newBoard);
        showBoard();
        lblMessage.setText("Bienvenido al Sudoku 6x6");
    }


    /**
     * Handles the "New Game" button event.
     * Displays a confirmation alert before generating a new Sudoku board.
     *
     * @param event Action event triggered by the "New Game" button.
     */

    @FXML
private void newGame(ActionEvent event) {
    // Create the confirmation alert
    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
    confirmation.setTitle("Confirmar nuevo juego");
    confirmation.setHeaderText(null);
    confirmation.setContentText("¿Deseas comenzar un nuevo Sudoku?\nSe perderá el progreso actual.");

    // Show the alert and save the pressed button
    ButtonType answer = confirmation.showAndWait().orElse(ButtonType.CANCEL);


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
    if (answer == ButtonType.OK) {
        SudokuGenerator generator = new SudokuGenerator();
        int[][] newBoard = generator.generate();
        board.setBoard(newBoard);

        // Clean colors
        clearCellColors();

        showBoard();
        availableAids =3;
        lblMessage.setText("Nuevo Sudoku generado.");
    }
    // If you press CANCEL or close the window, continue with the same game
    else {
        lblMessage.setText("Continuas con el juego actual.");
    }
}

    /**
     * Displays the Sudoku board in the text fields.
     * Fills in the generated numbers and enables event handling for editable cells.
     */
    private void showBoard() {
        int[][] matriz = board.getBoard();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                TextField cell = cells[row][col];
                int value = matriz[row][col];

                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setStyle("-fx-background-color: #D3D3D3;");
                    cell.setEditable(false);
                }
                else {
                    cell.setText("");
                    cell.setEditable(true);
                }

                //ASSIGN KEYBOARD LISTENER TO THE 36 CELLS
                final int f = row;
                final int c = col;

                cell.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event){
                        controlEntrance(cell,f,c);
                    }
                });


            }
        }
    }

    /**
     * Handles user input for each Sudoku cell, validating numbers
     * and ensuring they follow Sudoku rules.
     *
     * @param cell TextField where the user typed.
     * @param row  Row index of the cell.
     * @param col   Column index of the cell.
     */
    private void controlEntrance(TextField cell, int row, int col) {
        String text = cell.getText();

        if (text.isEmpty()) {
            board.setCell(row, col, 0);
            return; // evita mostrar alertas innecesarias
        }

        try {
            int value = Integer.parseInt(text);

            if (value < 1 || value > 6) {

                alertBox.showWarningAlertBox("Número inválido", "Por favor ingresa un número entre 1 y 6.", null);

                // Highlight cell with error in red
                cell.setStyle("-fx-background-color: #ffb3b3; -fx-border-color: red; -fx-border-width: 2;");

                // Wait 1.5 seconds and clear colors
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> {
                    if (!cell.getStyle().contains("yellow")) {
                        cell.setStyle("");
                    }
                });
                pause.play();

                cell.clear();

                return;
            }

            if (SudokuValidator.isValid(board.getBoard(), row, col, value)) {
                board.setCell(row, col, value);
            }
            else {

                alertBox.showWarningAlertBox("Movimiento no válido", "Ese número rompe las reglas del Sudoku.", null);

                cell.setStyle("-fx-background-color: #ffb3b3; -fx-border-color: red; -fx-border-width: 2;");

                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> {
                    if (!cell.getStyle().contains("yellow")) {
                        cell.setStyle("");
                    }
                });
                pause.play();

                cell.clear();
            }
        } catch (NumberFormatException e) {

            alertBox.showWarningAlertBox("Entrada inválida", "Solo puedes escribir números.", null);

            cell.setStyle("-fx-background-color: #ffb3b3; -fx-border-color: red; -fx-border-width: 2;");

            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(ev -> {
                if (!cell.getStyle().contains("yellow")) {
                    cell.setStyle("");
                }
            });
            pause.play();

            cell.clear();
        }
    }

    /**
     * Verifies if the Sudoku board is completely and correctly filled.
     *
     * @param event Action event triggered by the "Verify" button.
     */
    @FXML
    public void verifySudoku(ActionEvent event) {
        int[][] matrix = board.getBoard();

        for (int[] row : matrix) {
            for (int value : row) {
                if (value == 0) {
                    alertBox.showWarningAlertBox("Incompleto", "Aún hay celdas vacías.", null);
                    return;
                }
            }
        }

        if (SudokuValidator.isBoardValid(matrix)) {
            lblMessage.setText("¡Felicitaciones! Sudoku correcto.");
            alertBox.showAlertBox("Correcto", "¡Felicitaciones! Sudoku completo.", null);

        } else {
            lblMessage.setText("El Sudoku tiene errores.");
            alertBox.showWarningAlertBox("Error", "Hay números que no cumplen las reglas.", null);
        }
    }

    /**
     * Provides a help suggestion to the player by highlighting
     * one empty cell with a valid possible number.
     * The suggested cell is highlighted for 3 seconds.
     */
    @FXML
    private void askForHelp() {
        // Check if there are still aids available
        if (availableAids <= 0) {
            alertBox.showAlertBox("Ayuda","No te quedan ayudas disponibles",null);
            lblMessage.setText("Sin sugerencias");
            return;
        }

        int[][] boardToHelp = board.getBoard();

        //Find an empty cell
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (boardToHelp[row][col] == 0) {

                    //Find a valid number from 1 to 6
                    for (int num = 1; num <= 6; num++) {
                        if (SudokuValidator.isValid(boardToHelp, row, col, num)) {

                            availableAids--;

                            // Highlight the cell (visual suggestion only)
                            TextField cell = cells[row][col];
                            cell.setStyle("-fx-background-color: yellow;");

                            // Show message on the label
                            lblMessage.setText("Sugerencia: En la celda (" + (row + 1) + "," + (col + 1) + ") podrías probar el número " + num);

                            // Show alert to the user
                            alertBox.showAlertBox("Sugerencia de ayuda","En la celda (" + (row + 1) + "," + (col + 1) +
                                    ") puedes probar el número " + num + ".\n" +
                                    "Te quedan " + availableAids + " ayudas.", null);
                            return; // Solo una sugerencia por clic
                        }
                    }
                }
            }
        }

        // If there are no empty cells or suggestions
        alertBox.showAlertBox("Ayuda","No hay más sugerencias disponibles.",null);
    }


    /**
     * Displays the game instructions in a pop-up alert window.
     *
     * @param event Action event triggered by the "How to Play" button.
     */
    @FXML
    void onActionHowToPlayButton(ActionEvent event) {
        String instructions = """
            Cómo jugar Sudoku 6x6:

            1) Cada fila debe contener los números del 1 al 6 sin repetir.
            2) Cada columna debe contener los números del 1 al 6 sin repetir.
            3) Cada bloque de 2x3 debe tener también los números del 1 al 6 sin repetir.
            4) Usa solo números del 1 al 6.
            5) Si ingresas un número incorrecto, el sistema te avisará.
            6) Puedes pedir ayuda con el botón 'Ayuda' (tienes 3 ayudas por partida).
            7) Para comenzar una nueva partida, presiona 'Nuevo Juego'.
            """;


        AlertBox alertBox = new AlertBox();
        alertBox.showAlertBox("Instrucciones del Sudoku 6x6", instructions, null);


    }


    /**
     * Restores the original color (white) to all cells.
     * Used when starting a new game or when you want to clear visual aids.*/

    private void clearCellColors() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                // Remove any applied style
                cells[row][col].setStyle("");
            }
        }
    }


}
