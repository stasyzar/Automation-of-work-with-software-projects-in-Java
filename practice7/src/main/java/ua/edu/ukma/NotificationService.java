package ua.edu.ukma;

public interface NotificationService {
    void sendReceipt(Order order);
    void sendPaymentFailure(Order order);
}
