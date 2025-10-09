package sudoku.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sudoku.views.SudokuView;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private TextField nicknameTextField;

    @FXML
    void onActionStartButton(ActionEvent event) throws IOException {
        SudokuView sudokuView = SudokuView.getInstance();
        sudokuView.show();

        //Close stage
        Node source =(Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        SudokuController sudokuController = sudokuView.getSudokuController();

    }

}
