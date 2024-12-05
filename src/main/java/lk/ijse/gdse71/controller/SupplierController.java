package lk.ijse.gdse71.controller;

import lk.ijse.gdse71.dto.SupplierDTO;
import lk.ijse.gdse71.dto.tm.SupplierTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    @FXML
    private Button GenerateReportBtn;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    public Label lblSupplierId;
    @FXML
    public TextField txtSupplierName;
    @FXML
    public TextField txtContactInfo;
    @FXML
    public TextField txtPhone;
    @FXML
    private TableColumn<SupplierTM, String> colSupplierId;
    @FXML
    private TableColumn<SupplierTM, String> colSupplierName;
    @FXML
    private TableColumn<SupplierTM, String> colContactInfo;
    @FXML
    private TableColumn<SupplierTM, String> colPhone;
    @FXML
    private TableView<SupplierTM> tblSupplier;

    private SupplierModel supplierModel = new SupplierModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set table column to cell factory value
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colContactInfo.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load supplier data").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextSupplierId();
        loadTableData();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtSupplierName.setText("");
        txtContactInfo.setText("");
        txtPhone.setText("");
    }

    private void loadTableData() throws SQLException {
        ArrayList<SupplierDTO> supplierDTOS = supplierModel.getAllSuppliers();
        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (SupplierDTO supplierDTO : supplierDTOS) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDTO.getSupplierId(),
                    supplierDTO.getSupplierName(),
                    supplierDTO.getContactInfo(),
                    supplierDTO.getPhone()
            );
            supplierTMS.add(supplierTM);
        }

        tblSupplier.setItems(supplierTMS);
    }

    private void loadNextSupplierId() throws SQLException {
        String nextSupplierId = supplierModel.getNextSupplierId();
        lblSupplierId.setText(nextSupplierId);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String supplierId = lblSupplierId.getText();
        String supplierName = txtSupplierName.getText();
        String contactInfo = txtContactInfo.getText();
        String phone = txtPhone.getText();

        SupplierDTO supplierDTO = new SupplierDTO(supplierId, supplierName, contactInfo, phone);

        boolean isSaved = supplierModel.saveSupplier(supplierDTO);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Supplier saved!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save supplier!").show();
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        SupplierTM supplierTM = tblSupplier.getSelectionModel().getSelectedItem();
        if (supplierTM != null) {
            lblSupplierId.setText(supplierTM.getSupplierId());
            txtSupplierName.setText(supplierTM.getSupplierName());
            txtContactInfo.setText(supplierTM.getContactInfo());
            txtPhone.setText(supplierTM.getPhone());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String supplierId = lblSupplierId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = supplierModel.deleteSupplier(supplierId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Supplier deleted!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete supplier!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String supplierId = lblSupplierId.getText();
        String supplierName = txtSupplierName.getText();
        String contactInfo = txtContactInfo.getText();
        String phone = txtPhone.getText();

        SupplierDTO supplierDTO = new SupplierDTO(supplierId, supplierName, contactInfo, phone);

        boolean isUpdated = supplierModel.updateSupplier(supplierDTO);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Supplier updated!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update supplier!").show();
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    public void UpdateSup(ActionEvent actionEvent) {
    }

    public void DeleteSup(ActionEvent actionEvent) {
    }

    public void SaveSup(ActionEvent actionEvent) {
    }
}
