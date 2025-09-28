module com.example.proyectosiafinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;


    opens com.example.proyectosiafinal to javafx.fxml;
    exports com.example.proyectosiafinal;
}