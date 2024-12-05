package lk.ijse.gdse71.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.dto.QualityControlDTO;
import lk.ijse.gdse71.dto.tm.QualityControlTM;
import lk.ijse.gdse71.model.QualityControlModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class QualityControlController implements Initializable {

    @FXML
    private Label lblQId;

    @FXML
    private TextField txtProductID;

    @FXML
    private TextField txtBatchID;

    @FXML
    private TextField txtInspectorID;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TextArea txtRemarks;

    @FXML
    private TableColumn<QualityControlTM, String> colQId;

    @FXML
    private TableColumn<QualityControlTM, String> colProductID;

    @FXML
    private TableColumn<QualityControlTM, String> colBatchID;

    @FXML
    private TableColumn<QualityControlTM, String> colInspectorID;

    @FXML
    private TableColumn<QualityControlTM, String> colStatus;

    @FXML
    private TableColumn<QualityControlTM, String> colRemarks;

    @FXML
    private TableView<QualityControlTM> tblQuality;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    private QualityControlModel qualityControlModel = new QualityControlModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colQId.setCellValueFactory(new PropertyValueFactory<>("qId"));
        colProductID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colBatchID.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        colInspectorID.setCellValueFactory(new PropertyValueFactory<>("inspectorId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRemarks.setCellValueFactory(new PropertyValueFactory<>("remarks"));

        cmbStatus.setItems(FXCollections.observableArrayList("Pending", "Approved", "Rejected"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load quality data").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextQId();
        loadTableData();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtProductID.clear();
        txtBatchID.clear();
        txtInspectorID.clear();
        txtRemarks.clear();
        cmbStatus.setValue(null);
    }

    private void loadTableData() throws SQLException {
        ArrayList<QualityControlDTO> qualityControlDTOS = qualityControlModel.getAllQualityControlRecords();

        ObservableList<QualityControlTM> qualityControlTMS = FXCollections.observableArrayList();

        for (QualityControlDTO qualityControlDTO : qualityControlDTOS) {
            QualityControlTM qualityControlTM = new QualityControlTM(
                    qualityControlDTO.getQId(),
                    qualityControlDTO.getProductId(),
                    qualityControlDTO.getBatchId(),
                    qualityControlDTO.getInspectorId(),
                    qualityControlDTO.getStatus(),
                    qualityControlDTO.getRemarks()
            );
            qualityControlTMS.add(qualityControlTM);
        }

        tblQuality.setItems(qualityControlTMS);
    }

    private void loadNextQId() throws SQLException {
        String nextQId = qualityControlModel.getNextQualityControlId();
        lblQId.setText(nextQId);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String qId = lblQId.getText();
        String productId = txtProductID.getText();
        String batchId = txtBatchID.getText();
        String inspectorId = txtInspectorID.getText();
        String status = cmbStatus.getValue();
        String remarks = txtRemarks.getText();

        if (status == null || status.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select a status").show();
            return;
        }

        QualityControlDTO qualityControlDTO = new QualityControlDTO(qId, productId, batchId, inspectorId, status, remarks);

        boolean isSaved = qualityControlModel.saveQualityControl(qualityControlDTO);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Quality record saved").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save quality record").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String qId = lblQId.getText();
        String productId = txtProductID.getText();
        String batchId = txtBatchID.getText();
        String inspectorId = txtInspectorID.getText();
        String status = cmbStatus.getValue();
        String remarks = txtRemarks.getText();

        if (status == null || status.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select a status").show();
            return;
        }

        QualityControlDTO qualityControlDTO = new QualityControlDTO(qId, productId, batchId, inspectorId, status, remarks);

        boolean isUpdated = qualityControlModel.updateQualityControl(qualityControlDTO);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Quality record updated").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update quality record").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String qId = lblQId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optional = alert.showAndWait();

        if (optional.isPresent() && optional.get() == ButtonType.YES) {
            boolean isDeleted = qualityControlModel.deleteQualityControl(qId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Quality record deleted").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete quality record").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        QualityControlTM qualityTM = tblQuality.getSelectionModel().getSelectedItem();
        if (qualityTM != null) {
            lblQId.setText(qualityTM.getQId());
            txtProductID.setText(qualityTM.getProductId());
            txtBatchID.setText(qualityTM.getBatchId());
            txtInspectorID.setText(qualityTM.getInspectorId());
            cmbStatus.setValue(qualityTM.getStatus());
            txtRemarks.setText(qualityTM.getRemarks());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
