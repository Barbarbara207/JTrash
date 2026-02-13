module com.example.jtrash {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;



    opens com.example.jtrash to javafx.fxml;
    exports com.example.jtrash;
}