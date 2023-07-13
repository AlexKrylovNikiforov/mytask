package mytask.data;

import java.util.List;
import java.util.Map;

public class Shop {

    private final Map<Product, Integer> warehouse;

    private List<Cashier> cashierList;

    public Shop(Map<Product, Integer> warehouse) {
        this.warehouse = warehouse;
    }

    public List<Cashier> getCashierList() {
        return cashierList;
    }

    public void setCashierList(List<Cashier> cashierList) {
        this.cashierList = cashierList;
    }
}
