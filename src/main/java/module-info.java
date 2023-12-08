module br.com.alievi.autopeca {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires dotenv.java;


    opens br.com.alievi.autopeca to javafx.fxml;
    exports br.com.alievi.autopeca;
    exports br.com.alievi.autopeca.controller;
    opens br.com.alievi.autopeca.controller to javafx.fxml;
    opens br.com.alievi.autopeca.model to javafx.base;

}