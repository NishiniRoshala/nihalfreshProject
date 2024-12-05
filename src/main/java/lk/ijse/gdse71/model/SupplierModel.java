package lk.ijse.gdse71.model;

import lk.ijse.gdse71.dto.SupplierDTO;
import lk.ijse.gdse71.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {

    // Get next supplier ID
    public String getNextSupplierId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select supplier_id from supplier order by supplier_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last supplier ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("S%03d", newIdIndex); // Return the new supplier ID in format Snnn
        }
        return "S001"; // Return the default supplier ID if no data is found
    }

    // Save supplier data
    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into supplier(supplier_id, supplier_name, contact_info, phone) values (?,?,?,?)",
                supplierDTO.getSupplierId(),
                supplierDTO.getSupplierName(),
                supplierDTO.getContactInfo(),
                supplierDTO.getPhone()
        );
    }

    // Get all suppliers
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier");

        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();

        while (rst.next()) {
            SupplierDTO supplierDTO = new SupplierDTO(
                    rst.getString(1),  // Supplier ID
                    rst.getString(2),  // Supplier Name
                    rst.getString(3),  // Contact Info
                    rst.getString(4)   // Phone
            );
            supplierDTOS.add(supplierDTO);
        }
        return supplierDTOS;
    }

    // Update supplier data
    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException {
        return CrudUtil.execute(
                "update supplier set supplier_name=?, contact_info=?, phone=? where supplier_id=?",
                supplierDTO.getSupplierName(),
                supplierDTO.getContactInfo(),
                supplierDTO.getPhone(),
                supplierDTO.getSupplierId()
        );
    }

    // Delete supplier data
    public boolean deleteSupplier(String supplierId) throws SQLException {
        return CrudUtil.execute("delete from supplier where supplier_id=?", supplierId);
    }

    // Get all supplier IDs
    public ArrayList<String> getAllSupplierIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select supplier_id from supplier");

        ArrayList<String> supplierIds = new ArrayList<>();

        while (rst.next()) {
            supplierIds.add(rst.getString(1));
        }

        return supplierIds;
    }

    // Find supplier by ID
    public SupplierDTO findById(String supplierId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier where supplier_id=?", supplierId);

        if (rst.next()) {
            return new SupplierDTO(
                    rst.getString(1),  // Supplier ID
                    rst.getString(2),  // Supplier Name
                    rst.getString(3),  // Contact Info
                    rst.getString(4)   // Phone
            );
        }
        return null;
    }
}
