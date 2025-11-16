package com.example.karenderya;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SalesData {

    // Stores every saved sale
    public static ObservableList<SaleRecord> salesList = FXCollections.observableArrayList();

    // Tracks total income
    public static double totalIncome = 0;

    // Add sale
    public static void addSale(String item, double price) {
        salesList.add(new SaleRecord(item, price));
        totalIncome += price;
    }

    // Sale record class
    public static class SaleRecord {
        private final String item;
        private final double price;

        public SaleRecord(String item, double price) {
            this.item = item;
            this.price = price;
        }

        public String getItem() { return item; }
        public double getPrice() { return price; }
    }
}
