package lk.ijse.gdse71.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.dto.TransactionDTO;
import lk.ijse.gdse71.dto.tm.TransactionTM;
import lk.ijse.gdse71.model.TransactionModel;

import java.sql.SQLException;
import java.util.Optional;

public class TransactionController {

    @FXML
    private TableView<TransactionTM> tblUsers;

    @FXML
    private TableColumn<TransactionTM, String> colUserId;

    @FXML
    private TableColumn<TransactionTM, String> colUserName;

    @FXML
    private TableColumn<TransactionTM, String> colRole;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtRole;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    TransactionModel transactionModel = new TransactionModel();

    @FXML
    public void initialize() {

        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        try {
            loadUserData();
            resetForm();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load user data.").show();
        }
    }

    private void loadUserData() throws SQLException {
        ObservableList<TransactionTM> transactionTMS = FXCollections.observableArrayList();
        for (TransactionDTO transactionDTO : transactionModel.getAllTransactions()) {
            transactionTMS.add(new TransactionTM(transactionDTO.getUserId(), transactionDTO.getUserName(), transactionDTO.getRole(),transactionDTO.getPasswordHash()));
        }
        tblUsers.setItems(transactionTMS);
    }

    private void resetForm() {
        txtUserId.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
        txtRole.setText("");

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void onTableClick(MouseEvent event) {
        TransactionTM selectedUser = tblUsers.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            txtUserId.setText(selectedUser.getUserName());
            txtUserName.setText(selectedUser.getUserId());
            txtRole.setText(selectedUser.getRole());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String userId = txtUserId.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String role = txtRole.getText();

        if (validateInputs(userName, password, role)) {
            TransactionDTO transactionDTO = new TransactionDTO(userId, userName, hashPassword(password), role);

            if (transactionModel.saveTransaction(transactionDTO)) {
                new Alert(Alert.AlertType.INFORMATION, "User saved successfully.").show();
                loadUserData();
                resetForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save user.").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String userId = txtUserId.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String role = txtRole.getText();

        if (validateInputs(userName, password, role)) {
            TransactionDTO transactionDTO = new TransactionDTO(userId, userName, hashPassword(password), role);

            if (transactionModel.updateTransaction(transactionDTO)) {
                new Alert(Alert.AlertType.INFORMATION, "User updated successfully.").show();
                loadUserData();
                resetForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update user.").show();
            }
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String userId = txtUserId.getText();

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this user?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            if (transactionModel.deleteTransaction(userId)) {
                new Alert(Alert.AlertType.INFORMATION, "User deleted successfully.").show();
                loadUserData();
                resetForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete user.").show();
            }
        }
    }

    private boolean validateInputs(String userName, String password, String role) {
        if (userName.isEmpty() || password.isEmpty() || role.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields are required.").show();
            return false;
        }
        return true;
    }

    private String hashPassword(String password) {
        // Simple placeholder for hashing. Replace with a secure hash algorithm.
        return Integer.toHexString(password.hashCode());
    }
}
