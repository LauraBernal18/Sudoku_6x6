package sudoku.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the welcome view (initial window) of the Sudoku game.
 * This class is responsible for loading and displaying the instructions
 * window before the game starts.
 *
 * Implements the Singleton pattern to ensure that only one instance
 * of the welcome window exists at a time.
 *
 * @author Laura Bernal
 * @version 1.2
 * @since 2025-2
 */

public class WelcomeView extends Stage {

    /**
     * Constructor that loads the FXML file corresponding to the instructions
     * view and displays it as the main scene of the application.
     *
     * @throws IOException if the FXML file cannot be loaded correctly.
     */
    public WelcomeView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/sudoku/fxml/instructions-view.fxml")
        );

        //to open the file, read it
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("Sudoku Game");
        this.getIcons().add(new Image(
                getClass().getResourceAsStream("/sudoku/images/logo.png")
        ));
        this.show();
    }



    /**
     * Returns the single instance of the WelcomeView class.
     * If it doesn't exist yet, it creates a new one.
     *
     * @return the unique instance of WelcomeView.
     * @throws IOException if there is a problem loading the FXML file.
     * @see WelcomeViewHolder
     */
    public static WelcomeView getInstance() throws IOException{
        if (WelcomeView.WelcomeViewHolder.INSTANCE == null) {
            WelcomeView.WelcomeViewHolder.INSTANCE = new WelcomeView();
        }
        return WelcomeView.WelcomeViewHolder.INSTANCE;
    }


    /**
     * Static nested class used to implement the Singleton pattern.
     * This ensures that only one instance of the class is created.
     */
    private static class WelcomeViewHolder {
        private static WelcomeView INSTANCE = null;
    }
}

