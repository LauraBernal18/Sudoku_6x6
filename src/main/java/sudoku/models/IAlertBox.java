package sudoku.models;

/**
 * Interface that defines the structure for displaying alert boxes in the Sudoku application.
 * It provides methods for showing both general information alerts and warning alerts,
 * allowing different implementations for user notifications.
 *
 * @author  Martin Stivensson Alvarez
 * @version 1.0
 * @since   2025-10-17
 */

public interface IAlertBox {

    /**
     * Displays a standard informational alert box.
     *
     * @param title  the title of the alert window
     * @param message  the main content or message to be displayed
     * @param header  an optional header text (can be null)
     */
    void showAlertBox(String title, String message, String header);

    /**
     * Displays a warning alert box, typically used to notify the user
     * about invalid actions or potential issues in the game.
     *
     * @param title  the title of the alert window
     * @param message  the warning message to be displayed
     * @param header  an optional header text (can be null)
     */
    void showWarningAlertBox(String title, String message, String header);
}
