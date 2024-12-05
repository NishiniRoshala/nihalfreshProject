package lk.ijse.gdse71.dto.tm;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InventoryTM {
    private String inventoryID;
    private String productID;
    private int quantityInStock;
    private int reorderQuantity;      // Quantity at which reordering is triggered

    // Check if reorder is needed
    public boolean needsReorder() {
        return quantityInStock <= reorderQuantity;
    }

    // Reduce stock after an order
    public void reduceStock(int quantity) {
        if (quantity > quantityInStock) {
            throw new IllegalArgumentException("Insufficient stock available.");
        }
        quantityInStock -= quantity;
    }

    // Add stock after restocking
    public void addStock(int quantity) {
        quantityInStock += quantity;
    }
}
