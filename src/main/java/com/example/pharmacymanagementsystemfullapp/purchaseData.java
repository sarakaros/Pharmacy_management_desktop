package com.example.pharmacymanagementsystemfullapp;

import java.util.Date;

public class purchaseData {

    public class PurchaseData {
        private Integer purchaseId;
        private customerData customer;
        private medicineData medicine;
        private Integer quantityPurchased;
        private Date purchaseDate;
        private double totalAmount;
        private String categoryPurchased;

        public PurchaseData(Integer purchaseId, customerData customer, medicineData medicine, Integer quantityPurchased, Date purchaseDate, Double totalAmount, String categoryPurchased) {
            this.purchaseId = purchaseId;
            this.customer = customer;
            this.medicine = medicine;
            this.quantityPurchased = quantityPurchased;
            this.categoryPurchased = categoryPurchased;
            this.purchaseDate = purchaseDate;
            this.totalAmount = calculateTotalAmount();
        }

        private double calculateTotalAmount() {
            return this.medicine.getPrice() * this.quantityPurchased;
        }

        public Integer getPurchaseId() {
            return purchaseId;
        }

        public customerData getCustomer() {
            return customer;
        }

        public medicineData getMedicine() {
            return medicine;
        }

        public Integer getQuantityPurchased() {
            return quantityPurchased;
        }

        public Date getPurchaseDate() {
            return purchaseDate;
        }

        public String getCategoryPurchased() {
            return categoryPurchased;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void updateQuantity(Integer newQuantity) {
            this.quantityPurchased = newQuantity;
            this.totalAmount = calculateTotalAmount();
        }
    }

}
