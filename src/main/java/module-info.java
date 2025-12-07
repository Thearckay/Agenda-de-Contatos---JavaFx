module com.thearckay.projetoagenda {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;
    requires java.desktop;
    requires javafx.base;
    requires javafx.graphics;
    requires org.kordamp.ikonli.javafx;


    opens com.thearckay.projetoagenda to javafx.fxml;
    opens com.thearckay.projetoagenda.controller;
    exports com.thearckay.projetoagenda;
}