package lk.ijse.gdse71.controller;

import lk.ijse.gdse71.dto.EmployeeDTO;
import lk.ijse.gdse71.dto.tm.EmployeeTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button GenerateReportBtn;
    @FXML
    public Label lblEmployeeId;
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtPhone;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtSalary;
    @FXML
    public TextField txtPosition;
    @FXML
    public TextField txtDepartment;
    @FXML
    private TableColumn<EmployeeTM, String> colEmployeeId;
    @FXML
    private TableColumn<EmployeeTM, String> colName;
    @FXML
    private TableColumn<EmployeeTM, String> colPhone;
    @FXML
    private TableColumn<EmployeeTM, String> colEmail;
    @FXML
    private TableColumn<EmployeeTM, String> colSalary;
    @FXML
    private TableColumn<EmployeeTM, String> colPosition;
    @FXML
    private TableColumn<EmployeeTM, String> colDepartment;
    @FXML
    private TableView<EmployeeTM> tblEmployee;

    private EmployeeModel employeeModel = new EmployeeModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setting up table column to cell factory value
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load employee data").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextEmployeeId();
        loadTableData();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtSalary.setText("");
        txtPosition.setText("");
        txtDepartment.setText("");
    }

    private void loadTableData() throws SQLException {
        ArrayList<EmployeeDTO> employeeDTOS = employeeModel.getAllEmployees();
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

        for (EmployeeDTO employeeDTO : employeeDTOS) {
            EmployeeTM employeeTM = new EmployeeTM(
                    employeeDTO.getEmployeeId(),
                    employeeDTO.getName(),
                    employeeDTO.getPhone(),
                    employeeDTO.getEmail(),
                    employeeDTO.getSalary(),
                    employeeDTO.getPosition(),
                    employeeDTO.getDepartment()
            );
            employeeTMS.add(employeeTM);
        }

        tblEmployee.setItems(employeeTMS);
    }

    public void loadNextEmployeeId() throws SQLException {
        String nextEmployeeId = employeeModel.getNextEmployeeId();
        lblEmployeeId.setText(nextEmployeeId);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String employeeId = lblEmployeeId.getText();
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String salaryText = txtSalary.getText();
        String position = txtPosition.getText();
        String department = txtDepartment.getText();

        if (employeeId.isEmpty() || name.isEmpty() || phone.isEmpty() || email.isEmpty() || salaryText.isEmpty() || position.isEmpty() || department.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
            return;
        }

        double salary = 0.0;
        try {
            salary = Double.parseDouble(salaryText);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid salary amount!").show();
            return;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO(employeeId, name, phone, email, salary, position, department);

        boolean isSaved = employeeModel.saveEmployee(employeeDTO);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Employee saved!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save employee!").show();
        }
    }

    @FXML
    public void onClickTable(MouseEvent event) {
        EmployeeTM employeeTM = tblEmployee.getSelectionModel().getSelectedItem();
        if (employeeTM != null) {
            lblEmployeeId.setText(employeeTM.getEmployeeId());
            txtName.setText(employeeTM.getName());
            txtPhone.setText(employeeTM.getPhone());
            txtEmail.setText(employeeTM.getEmail());
            txtSalary.setText(String.valueOf(employeeTM.getSalary()));
            txtPosition.setText(employeeTM.getPosition());
            txtDepartment.setText(employeeTM.getDepartment());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String employeeId = lblEmployeeId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = employeeModel.deleteEmployee(employeeId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee deleted!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete employee!").show();
            }
        }
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String employeeId = lblEmployeeId.getText();
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String salaryText = txtSalary.getText();
        String position = txtPosition.getText();
        String department = txtDepartment.getText();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || salaryText.isEmpty() || position.isEmpty() || department.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
            return;
        }

        double salary = 0.0;
        try {
            salary = Double.parseDouble(salaryText);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid salary amount!").show();
            return;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO(employeeId, name, phone, email, salary, position, department);

        boolean isUpdated = employeeModel.updateEmployee(employeeDTO);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Employee updated!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update employee!").show();
        }
    }

    @FXML
    public void resetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    public void EmpGenaerateReport(ActionEvent actionEvent) {
    }

    public void EmpReport(ActionEvent actionEvent) {
    }
}
