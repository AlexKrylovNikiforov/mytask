package mytask.UI;

import mytask.data.Client;
import mytask.data.Product;

import java.util.Map;

public class Printer {

    protected void printClient(Client client) {
        System.out.println("****** CLIENT INFO ******");
        System.out.printf("ID: %d%nName: %s%nBalance: %f%n", client.getId(), client.getName(), client.getBalance());
    }

    protected void printProduct(Product product) {
        System.out.println("****** PRODUCT INFO ******");
        System.out.printf("Name: %s%nPrice: %f%n", product.getName(), product.getPrice());
    }

    protected void printBasket(Map<Product, Integer> basket) {
        double totalPrice = 0.0;
        for (Map.Entry<Product, Integer> entry : basket.entrySet()) {
            String name = entry.getKey().getName();
            int count = entry.getValue();
            double price = entry.getKey().getPrice();
            System.out.printf("%s: %d | Price per unit: %f EUR | Total: %f%n", name, count, price, price*count);
            totalPrice += price;
        }
        System.out.printf("Total price: %f EUR%n", totalPrice);
    }
}
