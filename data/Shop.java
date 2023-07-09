package mytask.data;

import java.util.Map;

public class Shop {
    private Long id;

    private Map<Product, Integer> warehouse;

    public Shop(Long id, Map<Product, Integer> warehouse) {
        this.id = id;
        this.warehouse = warehouse;
    }
}
