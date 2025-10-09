package sudoku.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeView extends Stage {

    public WelcomeView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                //una clase tiene una ruta asociada, solo llegué al archivo, luego lo abro
                getClass().getResource("/sudoku/instrucciones-view.fxml")
        );

        //para abrir el archivo, leerlo
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("Sudoku Game");
        this.getIcons().add(new Image(
                getClass().getResourceAsStream("/sudoku/images/logo.png")
        ));
        this.show();
    }

    public static WelcomeView getInstance() throws IOException{
        if (WelcomeView.WelcomeViewHolder.INSTANCE == null) {
            WelcomeView.WelcomeViewHolder.INSTANCE = new WelcomeView();
        }
        return WelcomeView.WelcomeViewHolder.INSTANCE;
    }

    private static class WelcomeViewHolder {
        private static WelcomeView INSTANCE = null;
    }
}
