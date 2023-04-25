module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.mail;
    requires java.sql;
    requires org.jsoup;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}