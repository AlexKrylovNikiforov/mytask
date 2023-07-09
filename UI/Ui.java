package mytask.UI;

import mytask.dao.ClientDao;
import mytask.data.Client;
import mytask.data.Product;
import mytask.service.ClientService;
import mytask.service.ShopService;

import java.util.*;

public class Ui {

    private final String HELLO_MESSAGE = "****** WELCOME TO SHOP ******";
    ClientService clientService = new ClientService();
    ShopService shopService = new ShopService();

    ClientDao clientDao = new ClientDao();

    public Product start() {
        displayMenu();
        Scanner sc = new Scanner(System.in);
        Map<Product, Integer> currentWarehouse = shopService.getCurrentWarehouse();

        while(sc.hasNextInt()) {
            System.out.println("Please, select an option and enter a number");
            int choice = sc.nextInt();
            if(choice == 6) {
                System.out.println("BYE, HOPE WE'LL SEE YOU AGAIN!");
                break;
            }
            switch(choice) {
                case 0:
                    System.out.println("Please, enter your ID:\n");
                    Long id = sc.nextLong();
                    Client currentClient = clientService.getClientById(id); //search for client database and get the client with id
                    break;
                case 1:
                    System.out.println("Please enter your name:\n");
                    String currentName = sc.nextLine();
                    System.out.println("Please enter your balance amount:\n");
                    double currentBalance = sc.nextDouble();
                    int currentId; //returns the list of existing Id
                    currentId = clientService.assignId();
                    Client newClient = clientService.createNewClient(currentId, currentName, currentBalance);
                    break;
                case 2:
                    for(Map.Entry<Product, Integer> entry: currentWarehouse.entrySet()) {
                        String name = entry.getKey().getName();
                        int count = entry.getValue();
                        double price = entry.getKey().getPrice();
                        System.out.printf("%s: %d, Price: %f EUR%n", name, count, price);
                    }
                    break;
                case 3:
                    clientService.showClientBasket();
                case 4:
                    System.out.println("Please enter product name:\n");
                    String name = sc.nextLine();
                    if(shopService.isProductInWarehouse(name)) {
                        System.out.println("Please enter product count:\n");
                        int count = sc.nextInt();
                        if(shopService.isProductCountEnough(name, count)) {
                            Product newProduct =
                            clientService.addProductToBasket();
                        }
                        clientService.addProductToBasket(newProduct, count);// add logic to build new Product and put it into client basket
                    }

                case 5:
            }
        }
        return null;
    }

    private void displayMenu () {
        System.out.println(HELLO_MESSAGE);
        for (UiCommand command: UiCommand.values()) {
            int label = command.getLabel();
            String description = command.getDescription();
            System.out.printf("%d: %s%n", label, description);
        }
    }
}
