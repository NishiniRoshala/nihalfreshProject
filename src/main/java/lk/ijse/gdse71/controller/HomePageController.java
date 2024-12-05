package lk.ijse.gdse71.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomePageController {

    @FXML
    private Button LoginPage;

    @FXML
    void loginOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
        Parent root = loader.load();
        LoginPage.getScene().setRoot(root);
    }
}
