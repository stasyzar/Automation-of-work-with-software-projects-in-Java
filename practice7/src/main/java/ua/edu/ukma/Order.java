package ua.edu.ukma;

import java.util.List;

public class Order {
    private final String customerName;
    private final double amount;
    private final List<String> items;
    private boolean isVip;

    public Order(String customerName, double amount, List<String> items) {
        this.customerName = customerName;
        this.amount = amount;
        this.items = items;
        this.isVip = false;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getAmount() {
        return amount;
    }

    public List<String> getItems() {
        return items;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }
}
