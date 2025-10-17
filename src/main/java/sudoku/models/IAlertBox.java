package sudoku.models;

public interface IAlertBox {
    void showAlertBox(String title, String message, String header);
    void showWarningAlertBox(String title, String message, String header);
}
