package lk.ijse.gdse71.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailDTO {
    private String orderId;
    private String itemId;
    private int quantity;
    private double price;
}
