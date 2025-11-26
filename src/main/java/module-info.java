module com.thearckay.projetoagenda {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;


    opens com.thearckay.projetoagenda to javafx.fxml;
    exports com.thearckay.projetoagenda;
}