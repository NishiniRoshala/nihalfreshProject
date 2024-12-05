package lk.ijse.gdse71.controller;


import lk.ijse.gdse71.dto.inventoryDTO;
import lk.ijse.gdse71.dto.tm.InventoryTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.model.inventoryModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnReset;

    @FXML
    public Label lblInventoryId;
    @FXML
    public TextField txtProductId;
    @FXML
    public TextField txtQuantityInStock;
    @FXML
    public TextField txtReorderQuantity;

    @FXML
    private TableColumn<InventoryTM, String> colInventoryId;
    @FXML
    private TableColumn<InventoryTM, String> colProductId;
    @FXML
    private TableColumn<InventoryTM, Integer> colQuantityInStock;
    @FXML
    private TableColumn<InventoryTM, Integer> colReorderQuantity;

    @FXML
    private TableView<InventoryTM> tblInventory;

    inventoryModel inventoryModel = new inventoryModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set table columns to map to InventoryTM properties
        colInventoryId.setCellValueFactory(new PropertyValueFactory<>("inventoryID"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productID"));
        colQuantityInStock.setCellValueFactory(new PropertyValueFactory<>("quantityInStock"));
        colReorderQuantity.setCellValueFactory(new PropertyValueFactory<>("reorderQuantity"));

        try {
            refreshPage();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load inventory data").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextInventoryId();
        loadTableData();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtProductId.setText("");
        txtQuantityInStock.setText("");
        txtReorderQuantity.setText("");
    }

    private void loadTableData() throws SQLException {
        ArrayList<inventoryDTO> inventoryList = inventoryModel.getAllInventory();
        ObservableList<InventoryTM> inventoryTMs = FXCollections.observableArrayList();

        for (inventoryDTO inventory : inventoryList) {
            InventoryTM inventoryTM = new InventoryTM(
                    inventory.getInventoryID(),
                    inventory.getProductID(),
                    inventory.getQuantityInStock(),
                    inventory.getReorderQuantity()
            );
            inventoryTMs.add(inventoryTM);
        }

        tblInventory.setItems(inventoryTMs);
    }

    private void loadNextInventoryId() throws SQLException {
        String nextInventoryId = inventoryModel.getNextInventoryId();
        lblInventoryId.setText(nextInventoryId);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) throws SQLException {
        String inventoryId = lblInventoryId.getText();
        String productId = txtProductId.getText();
        int quantityInStock = Integer.parseInt(txtQuantityInStock.getText());
        int reorderQuantity = Integer.parseInt(txtReorderQuantity.getText());

        inventoryDTO inventory = new inventoryDTO(inventoryId, productId, quantityInStock, reorderQuantity);

        boolean isSaved = inventoryModel.saveInventory(inventory);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Inventory saved successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save inventory.").show();
        }
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String inventoryId = lblInventoryId.getText();
        String productId = txtProductId.getText();
        int quantityInStock = Integer.parseInt(txtQuantityInStock.getText());
        int reorderQuantity = Integer.parseInt(txtReorderQuantity.getText());

      inventoryDTO inventory = new inventoryDTO(inventoryId, productId, quantityInStock, reorderQuantity);

        boolean isUpdated = inventoryModel.updateInventory(inventory);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Inventory updated successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update inventory.").show();
        }
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String inventoryId = lblInventoryId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean isDeleted = inventoryModel.deleteInventory(inventoryId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Inventory deleted successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete inventory.").show();
            }
        }
    }

    @FXML
    public void resetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    public void onClickTable(MouseEvent event) {
        InventoryTM selectedInventory = tblInventory.getSelectionModel().getSelectedItem();
        if (selectedInventory != null) {
            lblInventoryId.setText(selectedInventory.getInventoryID());
            txtProductId.setText(selectedInventory.getProductID());
            txtQuantityInStock.setText(String.valueOf(selectedInventory.getQuantityInStock()));
            txtReorderQuantity.setText(String.valueOf(selectedInventory.getReorderQuantity()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
