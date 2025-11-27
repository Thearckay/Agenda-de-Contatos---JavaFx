module com.thearckay.projetoagenda {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;
    requires java.desktop;


    opens com.thearckay.projetoagenda to javafx.fxml;
    opens com.thearckay.projetoagenda.controller;
    exports com.thearckay.projetoagenda;
}