module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;

    opens Controller to javafx.fxml;
    opens Model to javafx.fxml;

    exports Controller;
    exports Model;
}
