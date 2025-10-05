package sudoku;

import sudoku.views.SudokuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SudokuView sudokuView = SudokuView.getInstance();
        sudokuView.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

