package sudoku.models;


import javafx.scene.control.Alert;

/**
 * Implementation of the {@link IAlertBox} interface that uses JavaFX
 * to display informational and warning alert dialogs in the Sudoku application.
 * <p>
 * This class provides a simple and reusable way to show messages to the user,
 * such as notifications, warnings, or validation alerts.
 * </p>
 *
 * @author  Martin Stivensson Alvarez
 * @version 1.0
 * @since   2025-10-17
 * @see     IAlertBox
 */
public class AlertBox implements IAlertBox{

    /**
     * Displays a standard informational alert dialog using JavaFX.
     * <p>
     * This method is typically used for non-critical messages such as
     * game instructions or success confirmations.
     * </p>
     *
     * @param title   the title of the alert window
     * @param message the main message to be displayed in the alert
     * @param header  an optional header text (can be null)
     */


    @Override
    public void showAlertBox(String title, String message, String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a warning alert dialog using JavaFX.
     * <p>
     * This method is typically used for alerts related to invalid inputs,
     * invalid moves, or other warning scenarios in the game.
     * </p>
     *
     * @param title   the title of the alert window
     * @param message the warning message to be displayed
     * @param header  an optional header text (can be null)
     */



    @Override
    public void showWarningAlertBox(String title, String message, String header){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
