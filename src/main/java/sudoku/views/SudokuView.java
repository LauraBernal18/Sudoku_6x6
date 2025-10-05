package sudoku.views;

import sudoku.controllers.SudokuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SudokuView extends Stage {

    private SudokuController sudokuController;

    public SudokuView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/sudoku/sudoku-view.fxml")
        );
        Parent root = loader.load();
        this.sudokuController = loader.getController();

        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("Sudoku 6x6");
        this.show();
    }

    public SudokuController getSudokuController() {
        return sudokuController;
    }

    public static SudokuView getInstance() throws IOException {
        if (SudokuViewHolder.INSTANCE == null) {
            SudokuViewHolder.INSTANCE = new SudokuView();
        }
        return SudokuViewHolder.INSTANCE;
    }

    private static class SudokuViewHolder {
        private static SudokuView INSTANCE = null;
    }
}

