package sudoku.models;


import javafx.scene.control.Alert;

public class AlertBox implements IAlertBox{
    @Override
    public void showAlertBox(String title, String message, String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void showWarningAlertBox(String title, String message, String header){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
