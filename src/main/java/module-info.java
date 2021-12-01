module com.example.loginpage {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires json.simple;
    requires okhttp3;
    requires lombok;

    opens com.example.loginpage to javafx.fxml;
    exports com.example.loginpage;
}