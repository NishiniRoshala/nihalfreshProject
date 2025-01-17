package lk.ijse.gdse71.model;

import lk.ijse.gdse71.dto.OrderDetailDTO;
import lk.ijse.gdse71.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailModel {


    private final ProductionModel productionModel = new ProductionModel();


    public boolean saveOrderDetailsList(ArrayList<OrderDetailDTO> orderDetailDTOS) throws SQLException {

        for (OrderDetailDTO orderDetailDTO : orderDetailDTOS) {

            boolean isOrderDetailSaved = saveOrderDetail(orderDetailDTO);
            if (!isOrderDetailSaved) {

                return false;
            }


            boolean isProductionUpdated = ProductionModel.reduceQty(orderDetailDTO);
            if (!isProductionUpdated) {
                return false;
            }
        }

        return true;
    }


    private boolean saveOrderDetail(OrderDetailDTO orderDetailDTO) throws SQLException {

        return CrudUtil.execute(
                "insert into orderdetail values (?,?,?,?)",
                orderDetailDTO.getOrderId(),
                orderDetailDTO.getItemId(),
                orderDetailDTO.getQuantity(),
                orderDetailDTO.getPrice()
        );
    }
}
