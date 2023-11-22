module com.bd.interfaz_bd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.bd.interfaz_bd to javafx.fxml;
    exports com.bd.interfaz_bd;
}