package ua.edu.ukma;

import java.util.Arrays;
import java.util.List;

public class SecondHandStore {

    private static final double BASE_PRICE_PER_KILO = 500.0;

    public double calculatePrice(double weight, int day) {
        if(day < 1 || day > 7) {
            throw new IllegalArgumentException("Day must be between 1 and 7");
        }
        if(weight<=0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
         double discount = 1.0 - ((day - 1) * 0.10);
        return weight * BASE_PRICE_PER_KILO * discount;
    }

    public List<String> getAvailableCategories () {
        return Arrays.asList("Clothing", "Electronics", "Books", "Furniture", "Toys");
    }

    public String getPrinterPath() {
        return "C:\\POS\\printers";
    }
}
