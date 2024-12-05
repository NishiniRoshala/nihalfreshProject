package lk.ijse.gdse71.model;


import lk.ijse.gdse71.dto.QualityControlDTO;
import lk.ijse.gdse71.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QualityControlModel {

    public String getNextQualityControlId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select q_id from quality_control order by q_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last QualityControl ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("Q%03d", newIdIndex); // Return the new QId in format Qnnn
        }
        return "Q001"; // Return the default QId if no data is found
    }

    public boolean saveQualityControl(QualityControlDTO qualityControlDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into quality_control values (?,?,?,?,?,?)",
                qualityControlDTO.getQId(),
                qualityControlDTO.getBatchId(),
                qualityControlDTO.getInspectorId(),
                qualityControlDTO.getProductId(),
                qualityControlDTO.getStatus(),
                qualityControlDTO.getRemarks()
        );
    }

    public ArrayList<QualityControlDTO> getAllQualityControlRecords() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from quality_control");

        ArrayList<QualityControlDTO> qualityControlDTOS = new ArrayList<>();

        while (rst.next()) {
            QualityControlDTO qualityControlDTO = new QualityControlDTO(
                    rst.getString(1),  // QId
                    rst.getString(2),  // ProductID
                    rst.getString(3),  // BatchID
                    rst.getString(4),  // InspectorID
                    rst.getString(5),  // Status
                    rst.getString(6)   // Remarks
            );
            qualityControlDTOS.add(qualityControlDTO);
        }
        return qualityControlDTOS;
    }

    public boolean updateQualityControl(QualityControlDTO qualityControlDTO) throws SQLException {
        return CrudUtil.execute(
                "update quality_control set product_id=?, batch_id=?, inspector_id=?, status=?, remarks=? where q_id=?",
                qualityControlDTO.getProductId(),
                qualityControlDTO.getBatchId(),
                qualityControlDTO.getInspectorId(),
                qualityControlDTO.getStatus(),
                qualityControlDTO.getRemarks(),
                qualityControlDTO.getQId()
        );
    }

    public boolean deleteQualityControl(String qId) throws SQLException {
        return CrudUtil.execute("delete from quality_control where q_id=?", qId);
    }

    public QualityControlDTO findQualityControlById(String qId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from quality_control where q_id=?", qId);

        if (rst.next()) {
            return new QualityControlDTO(
                    rst.getString(1),  // QId
                    rst.getString(2),  // ProductID
                    rst.getString(3),  // BatchID
                    rst.getString(4),  // InspectorID
                    rst.getString(5),  // Status
                    rst.getString(6)   // Remarks
            );
        }
        return null;
    }
}
