package sudoku.views;

import sudoku.controllers.SudokuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Represents the main game window (view) for the Sudoku 6x6 application.

 * This class is responsible for loading the Sudoku board interface from
 * its FXML file, creating a JavaFX scene, and linking it to its corresponding
 * SudokuController. It also implements the Singleton pattern to ensure
 * that only one game window exists at a time.
 *
 * @author Laura Bernal
 * @version 1.1
 * @since 2025-2
 * @see sudoku.controllers.SudokuController
 */
public class SudokuView extends Stage {

    private SudokuController sudokuController;


    /**
     * Constructor that loads the Sudoku game view from its FXML file
     * and initializes the corresponding controller.
     *
     * @throws IOException if the FXML file cannot be found or loaded correctly.
     */
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





    /**
     * Returns the  SudokuController instance associated with this view.

     * This allows external classes (like sudoku.controllers.WelcomeController)
     * to access the controller and communicate with the game logic if needed.
     *
     * @return the controller instance that manages the Sudoku board.
     */
    public SudokuController getSudokuController() {

        return sudokuController;
    }

    /**
     * Returns the single instance of the SudokuView class.

     * If no instance exists, a new one is created. This method ensures that
     * only one Sudoku game window can be open at a time.
     *
     * @return the unique instance of SudokuView.
     * @throws IOException if there is an issue loading the FXML file.
     */
    public static SudokuView getInstance() throws IOException {
        if (SudokuViewHolder.INSTANCE == null) {
            SudokuViewHolder.INSTANCE = new SudokuView();
        }
        return SudokuViewHolder.INSTANCE;
    }

    /**
     * Internal static class used to hold the unique instance of {@link SudokuView}.
     * This is part of the Singleton design pattern, ensuring that only one
     * instance of this class is ever created.
     */
    private static class SudokuViewHolder {
        private static SudokuView INSTANCE = null;
    }
}

