package com.sevak_avet.generator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by savetisyan on 20/12/2017
 */
public class Product {
    private String name;
    private Double price;
    private LocalDateTime dateTime;
    private String category;
    private String ipAddress;

    public Product(String name, Double price, LocalDateTime dateTime, String category, String ipAddress) {
        this.name = name;
        this.price = price;
        this.dateTime = dateTime;
        this.category = category;
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        String dateTime = this.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm"));
        return String.format("%s,%s,%.2f,%s,%s", dateTime, name, price, category, ipAddress);
    }
}
