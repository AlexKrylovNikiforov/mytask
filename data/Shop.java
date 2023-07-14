package mytask.data;

import java.util.List;
import java.util.Map;

public class Shop {

    private final Map<Product, Integer> warehouse;

    private List<Cashier> cashierList;

    public Shop(List<Cashier> cashierList, Map<Product, Integer> warehouse) {
        this.cashierList = cashierList;
        this.warehouse = warehouse;
    }

    public List<Cashier> getCashierList() {
        return cashierList;
    }
}

