package lk.ijse.gdse71.dto;




import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class inventoryDTO {

    private String inventoryID;
    private String productID;
    private int quantityInStock;
    private int reorderQuantity;
}

