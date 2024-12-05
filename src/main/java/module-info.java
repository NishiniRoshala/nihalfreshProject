module nihalfreshfruit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;

    opens lk.ijse.gdse71.dto.tm to javafx.fxml;
    opens lk.ijse.gdse71.controller to javafx.fxml;
    exports lk.ijse.gdse71;
}
