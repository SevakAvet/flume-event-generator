package com.sevak_avet;

import com.sevak_avet.flume.FlumeClient;
import com.sevak_avet.generator.Product;
import com.sevak_avet.generator.ProductGenerator;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws IOException {
        LocalDate date = LocalDate.now();
        ProductGenerator generator = new ProductGenerator(date,
                Paths.get(Main.class.getResource("/adjectives.txt").getFile()),
                Paths.get(Main.class.getResource("/nouns.txt").getFile()),
                Paths.get(Main.class.getResource("/categories.txt").getFile()),
                Paths.get(Main.class.getResource("/geodata/GeoLite2-City-Blocks-IPv4.csv").getFile())
        );

//        try (FlumeClient client = new FlumeClient("localhost", 10001)) {
        try (FlumeClient client = new FlumeClient("35.193.68.92", 80)) {
            int totalDays = 14;
            int productsPerDay = 100;

            for (int i = 0; i < totalDays; i++) {
                for (int j = 0; j < productsPerDay; j++) {
                    Product product = generator.next();

                    // Send csv line to flume
//                    System.out.println(product);
                    client.send(product.toString());

                    if(j % 100 == 0) {
                        System.out.println("Day "+ i + ", progress: " + (j * 100.0 / productsPerDay));
                        Thread.sleep(2000);
                    }
                }

                date = date.plusDays(1);
                generator.setDate(date);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
