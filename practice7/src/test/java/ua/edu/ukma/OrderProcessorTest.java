package ua.edu.ukma;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderProcessorTest {

    @Mock
    private InventoryService inventoryService;

    @Mock
    private PaymentGateway paymentGateway;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private OrderProcessor orderProcessor;

    @Test
    void shouldProcessOrderSuccessfully(){
        List<String> items = Arrays.asList("Laptop", "Mouse", "Keyboard");
        Order order = new Order("John Doe", 1500.0, items);

        when(inventoryService.isInStock(items)).thenReturn(true);
        when(paymentGateway.processPayment("John Doe", 1500.0)).thenReturn(true);

        boolean result = orderProcessor.processOrder(order);

        assertThat(result).isTrue();

        verify(notificationService, times(1)).sendReceipt(order);
        verify(notificationService, never()).sendPaymentFailure(order);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(order.getCustomerName()).isEqualTo("John Doe");
        softly.assertThat(order.getAmount()).isEqualTo(1500.0);
        softly.assertThat(order.isVip()).isTrue();
        softly.assertAll();

        assertThat(order.getItems())
                .hasSize(3)
                .contains("Laptop", "Mouse")
                .doesNotContain("Smartphone");

    }

    @Test
    void shouldFailWhenOutOfStock(){
        List<String> items = Arrays.asList("Monitor");
        Order order = new Order("Ivan", 500.0, items);

        when(inventoryService.isInStock(items)).thenReturn(false);

        boolean result = orderProcessor.processOrder(order);

        assertThat(result).isFalse();

        verify(paymentGateway, never()).processPayment(eq("Ivan"), anyDouble());
        verify(notificationService, never()).sendReceipt(any());
    }

    @Test
    void shouldFailWhenPaymentDeclined(){
        List<String> items = Arrays.asList("Tablet");
        Order order = new Order("Alice", 300.0, items);

        when(inventoryService.isInStock(items)).thenReturn(true);
        when(paymentGateway.processPayment("Alice", 300.0)).thenReturn(false);

        boolean result = orderProcessor.processOrder(order);

        assertThat(result).isFalse();

        verify(notificationService).sendPaymentFailure(order);
        verify(notificationService, never()).sendReceipt(order);
    }

    @Test
    void weakTestForVipStatus_SurvivesMutation() {
        Order order = new Order("Test", 1500.0, Arrays.asList("Item"));
        when(inventoryService.isInStock(any())).thenReturn(true);
        when(paymentGateway.processPayment(eq("Test"), anyDouble())).thenReturn(true);

        orderProcessor.processOrder(order);

        assertThat(order.isVip()).isTrue();
    }

    @Test
    void strongTestForVipStatus_KillsMutation() {
        Order order = new Order("Test", 1000.0, Arrays.asList("Item"));
        when(inventoryService.isInStock(any())).thenReturn(true);
        when(paymentGateway.processPayment(eq("Test"), anyDouble())).thenReturn(true);

        orderProcessor.processOrder(order);

        assertThat(order.isVip()).isTrue();
    }
}
