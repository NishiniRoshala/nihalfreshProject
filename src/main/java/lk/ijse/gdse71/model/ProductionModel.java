package lk.ijse.gdse71.model;


import lk.ijse.gdse71.dto.OrderDetailDTO;
import lk.ijse.gdse71.dto.ProductionDTO;
import lk.ijse.gdse71.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductionModel {

    public static boolean reduceQty(OrderDetailDTO orderDetailDTO) {
        return false;
    }

    public String getNextProductId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select product_id from production order by product_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last Product ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("P%03d", newIdIndex); // Return the new Product ID in format Pnnn
        }
        return "P001"; // Return the default Product ID if no data is found
    }

    public boolean saveProduction(ProductionDTO productionDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into production values (?,?,?,?,?,?)",
                productionDTO.getProductId(),
                productionDTO.getQuantityProduced(),
                productionDTO.getProductionDate(),
                productionDTO.getProductionCost(),
                productionDTO.getSupervisorId(),
                productionDTO.getProductionLocation()
        );
    }

    public ArrayList<ProductionDTO> getAllProductions() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from production");

        ArrayList<ProductionDTO> productionDTOS = new ArrayList<>();

        while (rst.next()) {
            ProductionDTO productionDTO = new ProductionDTO(
                    rst.getString(1),  // ProductId
                    rst.getInt(2),     // QuantityProduced
                    rst.getDate(3),    // ProductionDate
                    rst.getDouble(4),  // ProductionCost
                    rst.getString(5),  // SupervisorId
                    rst.getString(6)   // ProductionLocation
            );
            productionDTOS.add(productionDTO);
        }
        return productionDTOS;
    }

    public boolean updateProduction(ProductionDTO productionDTO) throws SQLException {
        return CrudUtil.execute(
                "update production set quantity_produced=?, production_date=?, production_cost=?, supervisor_id=?, production_location=? where product_id=?",
                productionDTO.getQuantityProduced(),
                productionDTO.getProductionDate(),
                productionDTO.getProductionCost(),
                productionDTO.getSupervisorId(),
                productionDTO.getProductionLocation(),
                productionDTO.getProductId()
        );
    }

    public boolean deleteProduction(String productId) throws SQLException {
        return CrudUtil.execute("delete from production where product_id=?", productId);
    }

    public ArrayList<String> getAllProductIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select product_id from production");

        ArrayList<String> productIds = new ArrayList<>();

        while (rst.next()) {
            productIds.add(rst.getString(1));
        }

        return productIds;
    }

    public ProductionDTO findById(String productId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from production where product_id=?", productId);

        if (rst.next()) {
            return new ProductionDTO(
                    rst.getString(1),  // ProductId
                    rst.getInt(2),     // QuantityProduced
                    rst.getDate(3),    // ProductionDate
                    rst.getDouble(4),  // ProductionCost
                    rst.getString(5),  // SupervisorId
                    rst.getString(6)   // ProductionLocation
            );
        }
        return null;
    }
}
