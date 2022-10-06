module com.example.cryptojavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cryptojavafx to javafx.fxml;
    exports com.example.cryptojavafx;
}