package lk.ijse.gdse71.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


  //  package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashBoardController {

        @FXML
        private Button CustomerBtn;

        @FXML
        private Button Delivery;

        @FXML
        private Button Orderbtn;


        @FXML
        private Button Employee;

        @FXML
        private Button ExitDashBoard;

        @FXML
        private Button Inventory;

        @FXML
        private Button NextAbout;

        @FXML
        private Button ProductionBtn;

        @FXML
        private Button Supplier;

        @FXML
        private Button TransAction;

        @FXML
        private Button ViewDetails;

        @FXML
        private Label WeServe;

        @FXML
        private Label Wedeliver;

        @FXML
        private AnchorPane dashboard;

        @FXML
        private ImageView imgdb;

        @FXML
        private ImageView imgdb2;

        @FXML
        void CustomerOnAction(ActionEvent event) {
            navigation("/view/CustomerView.fxml");
        }

        @FXML
        void Delivery(ActionEvent event) {
//            navigation();
        }

        @FXML
        void Employee(ActionEvent event) {
            navigation("/view/Employee.fxml");
        }

        @FXML
        void ExitDashBoard(ActionEvent event) {
            navigation("/view/LoginPage.fxml");
        }

        @FXML
        void Inventory(ActionEvent event) {
            navigation("/view/Inventory.fxml");
        }

        @FXML
        void NextAbout(ActionEvent event) {
            navigation("/view/About.fxml");
        }

        @FXML
        void ProductionBtn(ActionEvent event) {
            navigation("/view/Production.fxml");
        }

        @FXML
        void Supplier(ActionEvent event) {
            navigation("/view/Supplier.fxml");
        }

        @FXML
        void TransAction(ActionEvent event) {

        }

        @FXML
        void ViewDetails(ActionEvent event) {
            navigation("/view/ProductView.fxml");
        }

        public void Orderbtn(ActionEvent actionEvent) {
//            navigation();
        }

        public void navigation(String path){
            try {
                dashboard.getChildren().clear();
                AnchorPane load = FXMLLoader.load(getClass().getResource(path));
                load.prefWidthProperty().bind(dashboard.widthProperty());
                load.prefHeightProperty().bind(dashboard.heightProperty());
                dashboard.getChildren().add(load);
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                e.printStackTrace();
            }
        }
    }


