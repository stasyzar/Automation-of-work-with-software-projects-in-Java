package ua.edu.ukma;

public interface PaymentGateway {
    boolean processPayment(String customerName, double amount);
}
