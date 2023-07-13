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
    private Client currentClient;

    public void initializeApp() throws FileNotFoundException, CashierNotFoundException {
        currentClient = initializeClient();
        List<Product> productList = productService.getProductList();
        List<Cashier> cashierList = cashierService.getCashiersList();
        Map<Product, Integer> currentWarehouse = shopService.getCurrentWarehouse();
        Shop shop = shopService.createNewShop(currentWarehouse);
        shop.setCashierList(cashierList);
    }
    public void start() throws FileNotFoundException, CashierNotFoundException {
        Map<Product, Integer> currentWarehouse;
        Scanner sc = new Scanner(System.in);

        displayMenu();
        while(sc.hasNext()) {
            System.out.println("Please, select an option and enter a number");
            int choice = sc.nextInt();
            if(choice == 0) {
                System.out.println("BYE, HOPE WE'LL SEE YOU AGAIN!");
                break;
            }
            switch(choice) {
                case 1:
                    currentWarehouse = shopService.getCurrentWarehouse();
                    for(Map.Entry<Product, Integer> entry: currentWarehouse.entrySet()) {
                        String name = entry.getKey().getName();
                        int count = entry.getValue();
                        double price = entry.getKey().getPrice();
                        System.out.printf("%s: %d, Price: %f EUR%n", name, count, price);
                    }
                    break;
                case 2:
                    Map<Product, Integer> currentClientBasket = clientService.getClientBasket();
                    printer.printBasket(currentClientBasket);
                    break;
                case 3:
                    Product newProduct;
                    while (!sc.next().equalsIgnoreCase("Q")) {
                        System.out.println("Please enter product name or enter Q to exit to main menu:\n");
                        String name = sc.nextLine();
                        if(shopService.isProductInWarehouse(name)) {
                            System.out.println("Please enter product count:\n");
                            int count = sc.nextInt();
                            if(shopService.isProductCountEnough(name, count)) {
                                newProduct = shopService.getProductByName(name);
                                clientService.addProductToBasket(currentClient, newProduct, count);
                                shopService.updateWarehouse(newProduct, count);
                            }
                        }
                    }
                    break;
                case 4:
                    double totalPrice = 0.0;
                    for(Map.Entry<Product, Integer> entry: currentClient.getBasket().entrySet()) {
                        Product currentProduct = entry.getKey();
                        List<Cashier> cashiersList = cashierService.getCashiersList();
                        Cashier cashierByProductType = shopService.getCashierByProductType(cashiersList, currentProduct.getProductType());
                        double price = cashierService.scanProduct(currentProduct);
                        totalPrice += price;
                        shopService.updateWarehouse(currentProduct, entry.getValue());
                    }
                    clientService.payForProducts(currentClient, totalPrice);
                    break;
                case 5:
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
            }
        }
    }

    private void displayMenu () {
        System.out.println("****** WELCOME TO SHOP ******");
        for (UiCommand command: UiCommand.values()) {
            int label = command.getLabel();
            String description = command.getDescription();
            System.out.printf("%d: %s%n", label, description);
        }
    }
    private Client initializeClient() {
        System.out.println("Please enter 1 for existing ID or 2 for create new client");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        while (sc.hasNextInt()) {
            if (choice == 1) {
                System.out.println("Currently registered clients: " + clientService.getClientsList().size());
                System.out.println("Please, enter your ID:\n");
                Long id = sc.nextLong();
                currentClient = logIn(id);
                return currentClient;
            } else if (choice == 2) {
                currentClient = signIn();
            } else {
                System.out.println("You entered wrong value, the value must be 1 or 2");
            }
        }
        return currentClient;
    }

    private Client logIn(Long id) {
        return clientService.getClientById(id);
    }
    private Client signIn() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your name:\n");
        String currentName = sc.nextLine();
        System.out.println("Please enter your balance amount:\n");
        double currentBalance = sc.nextDouble();
        int currentId; //returns the list of existing Id
        currentId = clientService.assignId();
        return clientService.createNewClient(currentId, currentName, currentBalance);
    }


}
