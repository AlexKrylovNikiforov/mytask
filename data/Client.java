package mytask.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Client {
    private final Long id;

    private final String name;

    private double balance;

    private Map<Product, Integer> basket;

    public Client(Long id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        basket = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return this.balance;
    }

    public Map<Product, Integer> getBasket () {
        return basket;
    }

    public void setBasket(Map<Product, Integer> basket) {
        this.basket = basket;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
