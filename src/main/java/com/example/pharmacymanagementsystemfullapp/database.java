//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.pharmacymanagementsystemfullapp;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    public database() {
    }

    public static Connection connectDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pharmacy?useSSL=false", "root", "123456");
            return connect;
        } catch (Exception var1) {
            Exception e = var1;
            e.printStackTrace();
            return null;
        }
    }
}
