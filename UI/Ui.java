package mytask.UI;

import mytask.dao.ClientDao;
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

    private final ProductService productService = new ProductService();
    
    private final Printer printer = new Printer();

    public void start() throws FileNotFoundException, CashierNotFoundException {
        Map<Product, Integer> currentWarehouse;
        Scanner sc = new Scanner(System.in);
        System.out.println("****** WELCOME TO SHOP ******");
        Client currentClient = clientService.initializeClient();
        printer.printClient(currentClient);
        displayMenu();
        while(sc.hasNextLine()) {
            System.out.println("Please, select an option and enter a number");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 0 -> {
                    System.out.println("BYE, HOPE WE'LL SEE YOU AGAIN!");
                    return;
                }
                case 1 -> {
                    currentWarehouse = shopService.getCurrentWarehouse();
                    printer.printWarehouse(currentWarehouse);
                    System.out.println();
                    displayMenu();
                }
                case 2 -> {
                    Map<Product, Integer> currentClientBasket = clientService.getClientBasket(currentClient);
                    printer.printBasket(currentClientBasket);
                    System.out.println();
                    displayMenu();
                }
                case 3 -> {
                    Product newProduct;
                    String reply;
                    ClientService cs = new ClientService();
                    do {
                        System.out.println("Please enter product name or enter Q to exit to the main menu: ");
                        reply = sc.nextLine();

                        if (reply.equalsIgnoreCase("Q")) {
                            break;
                        }
                        newProduct = shopService.getProductByName(reply);
                        boolean productInWarehouse = shopService.isProductInWarehouse(reply);
                        int currentCount = shopService.getCurrentProductCount(newProduct);
                        printer.printProduct(newProduct, currentCount);
                        if (productInWarehouse) {
                            System.out.println("Please enter product count: ");
                            int count = Integer.parseInt(sc.nextLine());
                            boolean productCountEnough = shopService.isProductCountEnough(newProduct, count);
                            if (productCountEnough) {
                                cs.addProductToBasket(currentClient, newProduct, count);
                                shopService.updateWarehouse(newProduct, count);
                            } else {
                                System.out.println("Not enough product in warehouse");
                            }
                        }
                    } while (!reply.equalsIgnoreCase("Q"));;
                    System.out.println();
                    displayMenu();
                }
                case 4 -> {
                    shopService.processPayment(currentClient);
                    System.out.println("Payment accepted");
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
                        clientService.removeProductFromBasket(currentClient, currentProduct, count);
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
