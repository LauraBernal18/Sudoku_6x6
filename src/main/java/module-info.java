module com.example.sudoku_6x6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens sudoku to javafx.fxml;
    exports sudoku;
}