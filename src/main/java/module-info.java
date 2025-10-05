module sudoku {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens sudoku to javafx.fxml;
    opens sudoku.models to javafx.fxml;
    opens sudoku.controllers to javafx.fxml;
    exports sudoku;
}