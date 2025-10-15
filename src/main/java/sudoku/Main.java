package sudoku;

import sudoku.views.WelcomeView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class of the Sudoku application.
 * This class serves as the entry point of the program and initializes
 * the JavaFX application. It launches the initial window of the game
 * through the WelcomeView class, which displays the instructions screen.
 *
 * @author Laura Valentina Bernal
 * @version 1.1
 * @since 2025-2
 */
public class Main extends Application {

    /**
     * The main entry point for all JavaFX applications.
     *
     * This method is automatically executed when the program starts.
     * It loads and displays the WelcomeView, which shows
     * the initial instructions window.
     *
     * @param stage the primary stage (window) provided by the JavaFX runtime.
     * @throws IOException if there is a problem loading the FXML file of the welcome view.
     */
    @Override
    public void start(Stage stage) throws IOException {
        WelcomeView welcomeView = WelcomeView.getInstance();
        welcomeView.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


/* -----------------------
   Main class of the Sudoku application.
   Clase principal de la aplicación Sudoku.

   This class serves as the entry point...
   Esta clase actúa como punto de entrada del programa e inicializa
   la aplicación JavaFX. Lanza la ventana inicial del juego
   mediante la clase WelcomeView, que muestra la pantalla de instrucciones.

   The main entry point for all JavaFX applications.
   → Punto de entrada principal para todas las aplicaciones JavaFX.

   This method is automatically executed...
   → Este metodo se ejecuta automáticamente cuando el programa inicia.
     Carga y muestra la ventana de bienvenida (WelcomeView).
*/
