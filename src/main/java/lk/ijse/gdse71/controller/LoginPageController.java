
package lk.ijse.gdse71.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.model.LogInPageModel;

import java.io.IOException;

public class LoginPageController {

    @FXML
    private AnchorPane bodyPane;

    @FXML
    private ImageView An1;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void LoginOnAction(ActionEvent event) {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        navigation("/view/Dashboard.fxml");
       LogInPageModel.cheakLogin();
    }

    public void navigation(String path){
        try {
            bodyPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(path));
            load.prefWidthProperty().bind(bodyPane.widthProperty());
            load.prefHeightProperty().bind(bodyPane.heightProperty());
            bodyPane.getChildren().add(load);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

}

