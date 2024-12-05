package lk.ijse.gdse71.model;



import lk.ijse.gdse71.util.CrudUtil;
import lk.ijse.gdse71.dto.EmployeeDTO;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class EmployeeModel {


    public String getNextEmployeeId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select employee_id from employee order by employee_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last employee ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("E%03d", newIdIndex); // Return the new employee ID in format Ennn
        }
        return "E001"; // Return the default employee ID if no data is found
    }


    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into employee values (?,?,?,?,?,?,?)",
                employeeDTO.getEmployeeId(),
                employeeDTO.getName(),
                employeeDTO.getPhone(),
                employeeDTO.getEmail(),
                employeeDTO.getSalary(),
                employeeDTO.getPosition(),
                employeeDTO.getDepartment()
        );
    }


    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from employee");

        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();

        while (rst.next()) {
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    rst.getString(1),  // Employee ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // Phone
                    rst.getString(4),  // Email
                    rst.getDouble(5),  // Salary
                    rst.getString(6),  // Position
                    rst.getString(7)   // Department
            );
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }


    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return CrudUtil.execute(
                "update employee set name=?, phone=?, email=?, salary=?, position=?, department=? where employee_id=?",
                employeeDTO.getName(),
                employeeDTO.getPhone(),
                employeeDTO.getEmail(),
                employeeDTO.getSalary(),
                employeeDTO.getPosition(),
                employeeDTO.getDepartment(),
                employeeDTO.getEmployeeId()
        );
    }


    public boolean deleteEmployee(String employeeId) throws SQLException {
        return CrudUtil.execute("delete from employee where employee_id=?", employeeId);
    }


    public EmployeeDTO findById(String employeeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from employee where employee_id=?", employeeId);

        if (rst.next()) {
            return new EmployeeDTO(
                    rst.getString(1),  // Employee ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // Phone
                    rst.getString(4),  // Email
                    rst.getDouble(5),  // Salary
                    rst.getString(6),  // Position
                    rst.getString(7)   // Department
            );
        }
        return null;
    }
}
