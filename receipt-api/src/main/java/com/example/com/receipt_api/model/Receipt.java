package com.example.com.receipt_api.model;

import java.util.List;

public class Receipt {
    public static class Item {
        private String shortDescription;
        private String price;

        public String getShortDescription() { return shortDescription; }
        public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
        public String getPrice() { return price; }
        public void setPrice(String price) { this.price = price; }
    }

    private String retailer;
    private String purchaseDate;
    private String purchaseTime;
    private List<Item> items;
    private String total;

    public String getRetailer() { return retailer; }
    public void setRetailer(String retailer) { this.retailer = retailer; }
    public String getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(String purchaseDate) { this.purchaseDate = purchaseDate; }
    public String getPurchaseTime() { return purchaseTime; }
    public void setPurchaseTime(String purchaseTime) { this.purchaseTime = purchaseTime; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
    public String getTotal() { return total; }
    public void setTotal(String total) { this.total = total; }
}
