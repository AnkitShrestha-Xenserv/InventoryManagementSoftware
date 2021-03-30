import java.sql.Timestamp;

// Created on: 3/28/2021
public class UserReportModel{
    private String item;
    private Double quantity;
    private Double price;
    private String type;
    private String storeName;
    private String timestamp;

    UserReportModel(String item, Double quantity, Double price, String type,String storeName, Timestamp timestamp){
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.storeName = storeName;
        this.timestamp = timestamp.toString();
    }

    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getStoreName() {
        return storeName;
    }

    public String getItem() {
        return item;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public Timestamp getTimestamp() {
        return Timestamp.valueOf(timestamp);
    }
}