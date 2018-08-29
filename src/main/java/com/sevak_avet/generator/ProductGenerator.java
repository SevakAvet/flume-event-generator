package com.sevak_avet.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by savetisyan on 20/12/2017
 */
public class ProductGenerator {
    private Random random;
    private List<String> namesList;

    private List<String> categoriesList;
    private List<String> ipAddresses;
    private LocalDate date;

    public ProductGenerator(LocalDate date, Path adjectives, Path nouns, Path categories, Path ipAddresses) throws IOException {
        random = new Random();
        this.date = date;

        List<String> adjectivesList = Files.readAllLines(adjectives);
        List<String> nounsList = Files.readAllLines(nouns);
        this.namesList = generateNames(adjectivesList, nounsList, 100);

        this.categoriesList = Files.readAllLines(categories);
        this.ipAddresses = Files.readAllLines(ipAddresses).stream()
                .skip(1)
                .map(x -> x.substring(0, x.indexOf(',')))
                .collect(Collectors.toList());
    }

    private List<String> generateNames(List<String> adjectives, List<String> nouns, int n) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String adjective = getRandom(adjectives);
            String noun = getRandom(nouns);
            String productName = String.format("%s %s", adjective, noun);
            result.add(productName);
        }
        return result;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    private <T> T getRandom(List<T> collection) {
        return collection.get(random.nextInt(collection.size()));
    }

    public Product next() {
        String productName = getRandom(namesList);
        double price = Math.max(0.99, Math.floor(random.nextGaussian() * 100));
        String category = getRandom(categoriesList);

        double randomSeconds = Math.abs(random.nextGaussian()) * 86399;
        LocalTime localTime = LocalTime.ofSecondOfDay((long) Math.max(0, Math.min(randomSeconds, 86399)));
        String ipAddress = getRandom(ipAddresses);

        return new Product(productName, price, LocalDateTime.of(this.date, localTime), category, ipAddress);
    }
}
