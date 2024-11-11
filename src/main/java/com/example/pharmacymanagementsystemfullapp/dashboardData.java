package com.example.pharmacymanagementsystemfullapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dashboardData {
    private static final String URL = "jdbc:mysql://localhost:3306/pharmacy_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT YEAR(purchaseDate) AS Year, MONTH(purchaseDate) AS Month, WEEK(purchaseDate) AS Week, QUARTER(purchaseDate) AS Quarter, SUM(totalAmount) AS TotalIncome, COUNT(purchaseId) AS TotalTransactions FROM Purchases GROUP BY YEAR(purchaseDate), WEEK(purchaseDate) ORDER BY Year, Week";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int year = rs.getInt("Year");
                    int month = rs.getInt("Month");
                    int week = rs.getInt("Week");
                    int quarter = rs.getInt("Quarter");
                    double totalIncome = rs.getDouble("TotalIncome");
                    System.out.println("Year: " + year + ", Quarter: " + quarter + ", Month: " + month + ", Week: " + week + " - Total Income: $" + totalIncome);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
