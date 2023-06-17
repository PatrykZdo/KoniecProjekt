module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jakarta.xml.bind;
    requires com.google.zxing;
    requires java.desktop;
    requires com.google.zxing.javase;

    opens com.example.demo to javafx.fxml;
    opens com.example.demo.Objekty to javafx.base;
    exports com.example.demo;
    exports com.example.demo.Database_Connect;
    opens com.example.demo.Database_Connect to javafx.fxml;
}