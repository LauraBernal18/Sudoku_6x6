package sudoku.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sudoku.views.SudokuView;

import java.io.IOException;

/**
 * Controller class for the welcome screen of the Sudoku game.

 * This class handles the events triggered in the initial window,
 * such as starting the game or exiting the application.
 * It connects the user interface (FXML) with the logic
 * that opens the main game window.
 *
 * @author Martin Alvarez, Laura Bernal
 * @version 1.2
 * @since 2025-2
 * @see sudoku.views.SudokuView
 */

public class WelcomeController {

    @FXML
    private TextField nicknameTextField;


    /**
     * Event handler for the "Start Game" button.
     * When the user clicks this button, a new instance of the {@link SudokuView}
     * class is created and displayed. The current welcome window is then closed.
     *
     * @param event the ActionEvent triggered when the button is clicked.
     * @throws IOException if there is an error loading the Sudoku game view (FXML file).
     */
    @FXML
    void onActionStartButton(ActionEvent event) throws IOException {
        SudokuView sudokuView = SudokuView.getInstance();
        sudokuView.show();

        //Close stage
        Node source =(Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();

    }

    /**
     * Event handler for the "Exit" button.
     * This method terminates the entire application when the user clicks the exit button.
     *
     * @param event the ActionEvent triggered when the button is clicked.
     * @throws IOException not thrown in this implementation, but kept for consistency.
     */
    @FXML
    void onActionExitButton(ActionEvent event) throws IOException {
        System.exit(0);
    }

}
