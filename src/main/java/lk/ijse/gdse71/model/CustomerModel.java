package lk.ijse.gdse71.model;

import lk.ijse.gdse71.dto.CustomerDTO;
import lk.ijse.gdse71.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CustomerModel {

    public String getNextCustomerID() throws SQLException {
        // Query to get the last CustomerID in descending order
        ResultSet rst = CrudUtil.execute("SELECT CustomerID FROM customer ORDER BY CustomerID DESC LIMIT 1");

        // Check if the query returned a result
        if (rst.next()) {
            String lastId = rst.getString(1); // Retrieve the last CustomerID
            if (lastId != null && lastId.startsWith("C")) { // Ensure it starts with 'C' as expected
                try {
                    String substring = lastId.substring(1); // Extract the numeric part
                    int i = Integer.parseInt(substring); // Convert the numeric part to integer
                    int newIdIndex = i + 1; // Increment the number by 1
                    return String.format("C%03d", newIdIndex); // Format the new ID as Cnnn
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid CustomerID format in the database: " + lastId, e);
                }
            }
            throw new IllegalArgumentException("CustomerID does not follow the expected format: " + lastId);
        }

        // Return default ID if no data is found in the table
        return "C001";
    }


    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into customer values (?,?,?,?)",
//                customerDTO.getCustomerID(),
                customerDTO.getName(),
                customerDTO.getNic(),
                customerDTO.getEmail(),
                customerDTO.getPhone()
        );
    }


    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer");

        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            CustomerDTO customerDTO = new CustomerDTO(
//                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5)   // Phone
            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }


    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
//        return CrudUtil.execute(
//                "update customer set name=?, nic=?, email=?, phone=? where CustomerID=?",
//                customerDTO.getName(),
//                customerDTO.getNic(),
//                customerDTO.getEmail(),
//                customerDTO.getPhone()
////                customerDTO.getCustomerID()
//        );
        return true;
    }


    public boolean deleteCustomer(String customerId) throws SQLException {
        return CrudUtil.execute("delete from customer where CustomerID=?", customerId);
    }


    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select CustomerID from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }


    public CustomerDTO findById(String selectedCusID) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer where CustomerID=?", selectedCusID);

        if (rst.next()) {
            return new CustomerDTO(
//                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5)   // Phone
            );
        }
        return null;
    }
}