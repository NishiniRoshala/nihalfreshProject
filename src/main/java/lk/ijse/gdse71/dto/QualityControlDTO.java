package lk.ijse.gdse71.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QualityControlDTO {
    private String qId;          // Quality Inspection ID
    private String productId;    // Product ID being inspected
    private String batchId;      // Batch ID being inspected
    private String inspectorId;  // ID of the inspector
    private String status;       // Inspection status (e.g., Approved, Rejected)
    private String remarks;      // Remarks or additional comments
}
