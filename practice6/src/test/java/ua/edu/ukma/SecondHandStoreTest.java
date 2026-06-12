package ua.edu.ukma;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class SecondHandStoreTest {

    private SecondHandStore store;

    @BeforeEach
    void setUpp() {
        store = new SecondHandStore();
    }

    @Test
    @Tag("fast")
    @DisplayName("Day 1 - No Discount")
    void testCalculatePriceDay1() {
        double price = store.calculatePrice(1.0, 1);
        assertEquals(500.0, price, "Must be 500.0 for day 1");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 8})
    @Tag("fast")
    @DisplayName("Invalid Day - Should Throw Exception")
    void testCalculatePriceInvalidDay(int day) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            store.calculatePrice(2.0, day);
        }, "Should throw IllegalArgumentException for invalid day: " + day);
    }

    @ParameterizedTest
    @CsvSource({
            "1.0, 2, 450.0",
            "2.0, 5, 600.0",
            "1.0, 7, 200.0"
    })
    @Tag("slow")
    @DisplayName("Price Drop Cycle - Various Days and Weights")
    void testPriceDropCycle(double weight, int day, double expectedTotal) {
        double actualPrice = store.calculatePrice(weight, day);
        assertEquals(expectedTotal, actualPrice, 0.01);
    }

    @TestFactory
    @Tag("fast")
    @DisplayName("Available Categories - Dynamic Tests")
    Stream<DynamicTest> testCategoriesDynamically() {
        List<String> requiredCategories = List.of("Clothing", "Electronics");
        List<String> actualCategories = store.getAvailableCategories();

        return requiredCategories.stream().map(category ->
                dynamicTest("Check:" + category, () -> {
                    assertTrue(actualCategories.contains(category), "Categoty " + category + " should be available");
                })
        );
    }

    @Test
    @Tag("slow")
    @DisplayName("POS Printer Path - Windows Only")
    void testPosPrinterPath() {
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        assumeTrue(isWindows, "Test is only relevant on Windows OS");

        String path = store.getPrinterPath();
        assertTrue(path.startsWith("C:\\"), "Printer path should start with C:\\ on Windows");
    }
}
