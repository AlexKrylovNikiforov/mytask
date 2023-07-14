package mytask.UI;

import mytask.dao.ClientDao;
import mytask.data.Cashier;
import mytask.data.Client;
import mytask.data.Product;
import mytask.data.Shop;
import mytask.exception.CashierNotFoundException;
import mytask.service.CashierService;
import mytask.service.ClientService;
import mytask.service.ProductService;
import mytask.service.ShopService;

import java.io.FileNotFoundException;
import java.util.*;

public class Ui {

    private final ClientService clientService = new ClientService();
    private final ShopService shopService = new ShopService();
    private final CashierService cashierService = new CashierService();

    private final ProductService productService = new ProductService();

    private final ClientDao clientDao = new ClientDao();
    private final Printer printer = new Printer();


    public void initializeApp() throws FileNotFoundException, CashierNotFoundException {
        Shop shop = shopService.initializeShop();

    }
    public void start() throws FileNotFoundException, CashierNotFoundException {
        Map<Product, Integer> currentWarehouse;
        Scanner sc = new Scanner(System.in);
        System.out.println("****** WELCOME TO SHOP ******");
        Client currentClient = clientService.initializeClient();
        printer.printClient(currentClient);
        displayMenu();
        while(sc.hasNext()) {
            System.out.println("Please, select an option and enter a number");
            int choice = sc.nextInt();
            switch (choice) {
                case 0 -> {
                    System.out.println("BYE, HOPE WE'LL SEE YOU AGAIN!");
                    return;
                }
                case 1 -> {
                    currentWarehouse = shopService.getCurrentWarehouse();
                    for (Map.Entry<Product, Integer> entry : currentWarehouse.entrySet()) {
                        String name = entry.getKey().getName();
                        int count = entry.getValue();
                        double price = entry.getKey().getPrice();
                        System.out.printf("%s: %d, Price: %f EUR%n", name, count, price);

                    }
                    System.out.println();
                    displayMenu();
                }
                case 2 -> {
                    Map<Product, Integer> currentClientBasket = clientService.getClientBasket();
                    printer.printBasket(currentClientBasket);
                    System.out.println();
                    displayMenu();
                }
                case 3 -> {
                    Product newProduct;
                    while (!sc.next().equalsIgnoreCase("Q")) {
                        System.out.println("Please enter product name or enter Q to exit to main menu: ");
                        String name = sc.nextLine();
                        boolean productInWarehouse = shopService.isProductInWarehouse(name);
                        if (productInWarehouse) {
                            System.out.println("Please enter product count: ");
                            int count = sc.nextInt();
                            boolean productCountEnough = shopService.isProductCountEnough(name, count);
                            if (productCountEnough) {
                                newProduct = shopService.getProductByName(name);
                                clientService.addProductToBasket(currentClient, newProduct, count);
                                shopService.updateWarehouse(newProduct, count);
                            }
                        }
                    }
                    System.out.println();
                    displayMenu();
                }
                case 4 -> {
                    double totalPrice = 0.0;
                    for (Map.Entry<Product, Integer> entry : currentClient.getBasket().entrySet()) {
                        Product currentProduct = entry.getKey();
                        printer.printProduct(currentProduct);
                        List<Cashier> cashiersList = cashierService.getCashiersList();
                        double price = cashierService.scanProduct(currentProduct);
                        totalPrice += price;
                        shopService.updateWarehouse(currentProduct, entry.getValue());
                    }
                    clientService.payForProducts(currentClient, totalPrice);
                    System.out.println();
                    displayMenu();
                }
                case 5 -> {
                    double cashBack = 0.0;
                    while (!sc.next().equalsIgnoreCase("Q")) {
                        printer.printBasket(currentClient.getBasket());
                        System.out.println("Please enter ID of product to return or Q to finish:\n");
                        long id = sc.nextLong();
                        System.out.println("Please enter count to return: ");
                        int count = sc.nextInt();
                        Product currentProduct = productService.getProductById(id);
                        clientService.removeProductFromBasket(currentProduct, count);
                        double price = currentProduct.getPrice();
                        cashBack += price;
                    }
                    clientService.updateClientBalance(currentClient, cashBack);
                    System.out.println();
                    displayMenu();
                }
            }
        }
    }

    private void displayMenu () {
        System.out.println("*** Main menu ***");
        for (UiCommand command: UiCommand.values()) {
            int label = command.getLabel();
            String description = command.getDescription();
            System.out.printf("%d: %s%n", label, description);
        }
    }




}
