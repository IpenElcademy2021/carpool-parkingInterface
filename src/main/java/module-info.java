module com.example.loginpage {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.loginpage to javafx.fxml;
    exports com.example.loginpage;
}