package ua.edu.ukma;

public class OrderProcessor {
    private final InventoryService inventoryService;
    private final PaymentGateway paymentGateway;
    private final NotificationService notificationService;

    public OrderProcessor(InventoryService inventoryService, PaymentGateway paymentGateway, NotificationService notificationService) {
        this.inventoryService = inventoryService;
        this.paymentGateway = paymentGateway;
        this.notificationService = notificationService;
    }

    public boolean processOrder(Order order) {
        if (!inventoryService.isInStock(order.getItems())) {
            return false;
        }

        boolean paymentSuccess = paymentGateway.processPayment(order.getCustomerName(), order.getAmount());
        if (paymentSuccess) {
            notificationService.sendReceipt(order);
            if (order.getAmount() >= 1000.0) {
                order.setVip(true);
            }
            return true;
        } else {
            notificationService.sendPaymentFailure(order);
            return false;
        }
    }
}
