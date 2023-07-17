package mytask.UI;

import mytask.data.Client;
import mytask.data.Product;

import java.util.Map;

public class Printer {

    protected void printClient(Client client) {
        System.out.println("****** CLIENT INFO ******");
        System.out.printf("ID: %d%nName: %s%nBalance: %.2f%n", client.getId(), client.getName(), client.getBalance());
    }

    protected void printProduct(Product product, int count) {
        System.out.println("****** PRODUCT INFO ******");
        System.out.printf("Name: %s%nPrice: %.2f%n", product.getName(), product.getPrice());
        System.out.println("Current count: " + count);
    }

    protected void printBasket(Map<Product, Integer> basket) {
        if(basket == null) {
            System.out.println("Your basket is empty");
            return;
        }
        double totalPrice = 0.0;
        for (Map.Entry<Product, Integer> entry : basket.entrySet()) {
            String name = entry.getKey().getName();
            int count = entry.getValue();
            double price = entry.getKey().getPrice();
            System.out.printf("%s: %d | Price per unit: %.2f EUR | Total: %.2f%n", name, count, price, price*count);
            totalPrice += price;
        }
        System.out.printf("Total price: %.2f EUR%n", totalPrice);
    }

    public void printWarehouse(Map<Product, Integer> warehouse) {
        for (Map.Entry<Product, Integer> entry : warehouse.entrySet()) {
            String name = entry.getKey().getName();
            int count = entry.getValue();
            double price = entry.getKey().getPrice();
            System.out.printf("%s: %d, Price: %.2f EUR%n", name, count, price);
        }
    }
}
