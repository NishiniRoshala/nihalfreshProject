package lk.ijse.gdse71.controller;

import lk.ijse.gdse71.dto.CustomerDTO;
import lk.ijse.gdse71.dto.tm.CustomerTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    public Label lblCustomerId;
    @FXML
    private Button GenerateReportBtn;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Label cID;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtNic;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private TableColumn<CustomerTM, String> colCustomerId;
    @FXML
    private TableColumn<CustomerTM, String> colEmail;
    @FXML
    private TableColumn<CustomerTM, String> colName;
    @FXML
    private TableColumn<CustomerTM, String> colNic;
    @FXML
    private TableColumn<CustomerTM, String> colPhone;
    @FXML
    private TableView<CustomerTM> tblCustomer;

    private final CustomerModel customerModel = new CustomerModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableColumns();
        try {
            refreshPage();
        } catch (SQLException e) {
            showErrorAlert("Error initializing the page: " + e.getMessage());
        }
    }

    private void initializeTableColumns() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    private void refreshPage() throws SQLException {
//        loadNextCustomerId();
        loadTableData();
        resetInputFields();
        enableSaveMode();
    }

    private void resetInputFields() {
//        lblCustomerId.setText("");
        txtName.clear();
        txtNic.clear();
        txtEmail.clear();
        txtPhone.clear();
    }

    private void enableSaveMode() {
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void enableUpdateDeleteMode() {
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

    private void loadTableData() throws SQLException {
        ArrayList<CustomerDTO> customerDTOs = customerModel.getAllCustomers();
        ObservableList<CustomerTM> customerTMs = FXCollections.observableArrayList();

        for (CustomerDTO customerDTO : customerDTOs) {
            customerTMs.add(new CustomerTM(
                    customerDTO.getName(),
                    customerDTO.getNic(),
                    customerDTO.getEmail(),
                    customerDTO.getPhone()
            ));
        }
        tblCustomer.setItems(customerTMs);
    }

//    private void loadNextCustomerId() throws SQLException {
//        try {
//            String nextCustomerID = customerModel.getNextCustomerID();
//            lblCustomerId.setText(nextCustomerID);
//        } catch (Exception e) {
//            showErrorAlert("Failed to load next Customer ID: " + e.getMessage());
//        }
//    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        try {
            CustomerDTO customerDTO = getCustomerInput();
            if (customerModel.saveCustomer(customerDTO)) {
                refreshPage();
                showInfoAlert("Customer saved successfully!");
            } else {
                showErrorAlert("Failed to save customer.");
            }
        } catch (Exception e) {
            showErrorAlert("Error saving customer: " + e.getMessage());
        }
    }

    @FXML
    private void onClickTable(MouseEvent event) {
        CustomerTM selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            populateCustomerDetails(selectedCustomer);
            enableUpdateDeleteMode();
        }
    }

    private void populateCustomerDetails(CustomerTM customerTM) {
        txtName.setText(customerTM.getName());
        txtNic.setText(customerTM.getNic());
        txtEmail.setText(customerTM.getEmail());
        txtPhone.setText(customerTM.getPhone());
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        try {
//            String customerId = lblCustomerId.getText();
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> response = confirmationAlert.showAndWait();

            if (response.isPresent() && response.get() == ButtonType.YES) {
                if (customerModel.deleteCustomer(txtNic.getText())) {
                    refreshPage();
                    showInfoAlert("Customer deleted successfully!");
                } else {
                    showErrorAlert("Failed to delete customer.");
                }
            }
        } catch (SQLException e) {
            showErrorAlert("Error d eleting customer: " + e.getMessage());
        }
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        try {
            CustomerDTO customerDTO = getCustomerInput();
            if (customerModel.updateCustomer(customerDTO)) {
                refreshPage();
                showInfoAlert("Customer updated successfully!");
            } else {
                showErrorAlert("Failed to update customer.");
            }
        } catch (Exception e) {
            showErrorAlert("Error updating customer: " + e.getMessage());
        }
    }

    @FXML
    private void resetOnAction(ActionEvent event) {
        try {
            refreshPage();
        } catch (SQLException e) {
            showErrorAlert("Error resetting the page: " + e.getMessage());
        }
    }

    @FXML
    private void GenerateReport(ActionEvent event) {
        // Add logic to generate a customer report
        showInfoAlert("Feature not implemented yet.");
    }

    private CustomerDTO getCustomerInput() {
        return new CustomerDTO(
                txtName.getText(),
                txtNic.getText(),
                txtEmail.getText(),
                txtPhone.getText()
        );
    }

    private void showInfoAlert(String message) {
        new Alert(Alert.AlertType.INFORMATION, message).show();
    }

    private void showErrorAlert(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }
}
