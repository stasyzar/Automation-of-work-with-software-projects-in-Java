package ua.edu.ukma;

import java.util.List;

public interface InventoryService {
    boolean isInStock(List<String> items);
}
