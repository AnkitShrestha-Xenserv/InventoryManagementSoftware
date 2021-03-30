import java.sql.Timestamp;

/**
 * Created by Ankit on 3/28/2021.
 */
public class StoreReportModel {
    private String item;
    private Double quantity;
    private Double price;
    private String type;
    private Timestamp timestamp;
    private String buyerName;
    private String buyerAddress;
    private String buyerPhoneNumber;

    StoreReportModel(String item, Double quantity, Double price, String type, Timestamp timestamp, String buyerName, String buyerAddress, String buyerPhoneNumber){
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.timestamp = timestamp;
        this.buyerName = buyerName;
        this.buyerAddress = buyerAddress;
        this.buyerPhoneNumber = buyerPhoneNumber;
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
        return timestamp;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public String getBuyerPhoneNumber() {
        return buyerPhoneNumber;
    }
}