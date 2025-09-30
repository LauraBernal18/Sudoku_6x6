module com.example.sudoku_6x6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sudoku_6x6 to javafx.fxml;
    exports com.example.sudoku_6x6;
}