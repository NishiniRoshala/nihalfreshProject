package lk.ijse.gdse71.model;


import lk.ijse.gdse71.dto.inventoryDTO;
import lk.ijse.gdse71.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class inventoryModel {

    // Generate the next inventory ID
    public String getNextInventoryId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select inventory_id from inventory order by inventory_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last inventory ID
            String substring = lastId.substring(1); // Extract numeric part
            int i = Integer.parseInt(substring); // Convert to integer
            int newIdIndex = i + 1; // Increment by 1
            return String.format("I%03d", newIdIndex); // Format as Innn
        }
        return "I001"; // Default ID if no records exist
    }

    // Save a new inventory record
    public boolean saveInventory(inventoryDTO inventoryDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into inventory values (?,?,?,?)",
                inventoryDTO.getInventoryID(),
                inventoryDTO.getProductID(),
                inventoryDTO.getQuantityInStock(),
                inventoryDTO.getReorderQuantity()
        );
    }

    // Retrieve all inventory records
    public ArrayList<inventoryDTO> getAllInventory() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from inventory");

        ArrayList<inventoryDTO> inventoryList = new ArrayList<>();

        while (rst.next()) {
            inventoryDTO inventoryDTO = new inventoryDTO(
                    rst.getString(1),  // Inventory ID
                    rst.getString(2),  // Product ID
                    rst.getInt(3),     // Quantity in Stock
                    rst.getInt(4)      // Reorder Quantity
            );
            inventoryList.add(inventoryDTO);
        }
        return inventoryList;
    }

    // Update an existing inventory record
    public boolean updateInventory(inventoryDTO inventoryDTO) throws SQLException {
        return CrudUtil.execute(
                "update inventory set product_id=?, quantity_in_stock=?, reorder_quantity=? where inventory_id=?",
                inventoryDTO.getProductID(),
                inventoryDTO.getQuantityInStock(),
                inventoryDTO.getReorderQuantity(),
                inventoryDTO.getInventoryID()
        );
    }

    // Delete an inventory record by ID
    public boolean deleteInventory(String inventoryId) throws SQLException {
        return CrudUtil.execute("delete from inventory where inventory_id=?", inventoryId);
    }

    // Retrieve all inventory IDs
    public ArrayList<String> getAllInventoryIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select inventory_id from inventory");

        ArrayList<String> inventoryIds = new ArrayList<>();

        while (rst.next()) {
            inventoryIds.add(rst.getString(1));
        }
        return inventoryIds;
    }

    // Find a specific inventory record by ID
    public inventoryDTO findById(String inventoryId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from inventory where inventory_id=?", inventoryId);

        if (rst.next()) {
            return new inventoryDTO(
                    rst.getString(1),  // Inventory ID
                    rst.getString(2),  // Product ID
                    rst.getInt(3),     // Quantity in Stock
                    rst.getInt(4)      // Reorder Quantity
            );
        }
        return null;
    }
}
