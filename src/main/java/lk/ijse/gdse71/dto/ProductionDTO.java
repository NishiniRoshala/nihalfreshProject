package lk.ijse.gdse71.dto;


import lombok.*;

import java.util.Date;

@Getter
@Setter

@NoArgsConstructor
@ToString


public class ProductionDTO {
    private String productId;
    private int quantityProduced;
    private Date productionDate;
    private double productionCost;
    private String supervisorId;
    private String productionLocation;

    // Constructor
    public ProductionDTO(String productId, int quantityProduced, Date productionDate, double productionCost, String supervisorId, String productionLocation) {
        this.productId = productId;
        this.quantityProduced = quantityProduced;
        this.productionDate = productionDate;
        this.productionCost = productionCost;
        this.supervisorId = supervisorId;
        this.productionLocation = productionLocation;
    }

    // Getters and setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantityProduced() {
        return quantityProduced;
    }

    public void setQuantityProduced(int quantityProduced) {
        this.quantityProduced = quantityProduced;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public double getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(double productionCost) {
        this.productionCost = productionCost;
    }

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getProductionLocation() {
        return productionLocation;
    }

    public void setProductionLocation(String productionLocation) {
        this.productionLocation = productionLocation;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "ProductionDTO{" +
                "productId='" + productId + '\'' +
                ", quantityProduced=" + quantityProduced +
                ", productionDate=" + productionDate +
                ", productionCost=" + productionCost +
                ", supervisorId='" + supervisorId + '\'' +
                ", productionLocation='" + productionLocation + '\'' +
                '}';
    }
}
