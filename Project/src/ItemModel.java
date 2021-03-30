import java.util.Comparator;

// Created on: 7/25/2020

public class ItemModel {
     private String storeName;
     private String item;
     private Double quantity;
     private Double price;
     private String type;

    ItemModel(String storeName,String item, Double quantity, Double price, String type){
        this.storeName = storeName;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
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

    public static Comparator<ItemModel> PriceComparatorL2H = new Comparator<ItemModel>() {
        @Override
        public int compare(ItemModel o1, ItemModel o2) {
            double price1 = o1.getPrice();
            double price2 = o2.getPrice();

            return Double.compare(price1, price2);
        }
    };

    public static Comparator<ItemModel> PriceComparatorH2L = new Comparator<ItemModel>() {
        @Override
        public int compare(ItemModel o1, ItemModel o2) {
            double price1 = o1.getPrice();
            double price2 = o2.getPrice();

            return Double.compare(price2, price1);
        }
    };
}