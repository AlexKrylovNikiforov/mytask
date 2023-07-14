package mytask.service;

import mytask.dao.ClientDao;
import mytask.data.Client;
import mytask.data.Product;
import mytask.exception.BalanceWrongValueException;
import mytask.exception.ClientNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientService {
    ClientDao clientDao = new ClientDao();

    public Client initializeClient() {
        Client currentClient = null;
        System.out.println("Please enter 1 for existing ID or 2 for create new client");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if (choice == 1) {
            System.out.println("Please, enter your ID:");
            long id = sc.nextLong();
            currentClient = logIn(id);
            return currentClient;
        } else if (choice == 2) {
            try {
                currentClient = signIn();
            } catch (BalanceWrongValueException e) {
                System.out.println(e.getMessage());;
            }
        } else {
            System.out.println("You entered wrong value, the value must be 1 or 2");
        }
        return currentClient;
    }

    private Client logIn(long id) {
        return getClientById(id);
    }
    private Client getClientById(Long id) {
        try {
            return clientDao.findClientById(id);
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    private Client signIn() throws BalanceWrongValueException {
        Scanner sc = new Scanner(System.in);
        long currentId = assignId();
        System.out.println("Please enter your name:");
        String currentName = sc.nextLine();
        System.out.println("Please enter your balance amount:");
        double currentBalance = sc.nextDouble();
        if(currentBalance < 1000) {
            return new Client(currentId, currentName, currentBalance);
        } else {
            throw new BalanceWrongValueException("Balance amount must be less than 1000 EUR");
        }
    }

    private int assignId() {
        return clientDao.getIdList().size() + 1;
    }

    public Map<Product, Integer> getClientBasket(Client client) {
        return clientDao.getClientBasket(client);
    }

    public void addProductToBasket(Client client, Product product, int count) {
        Map<Product, Integer> clientBasket = getClientBasket(client);
        clientDao.addProductToBasket(client, product, count);
        clientDao.updateClientsBasket(client, clientBasket);
    }
    public void removeProductFromBasket(Client client, Product product, int count) {
        clientDao.getClientBasket(client).put(product, count);
    }

    public List<Client> getClientsList() {
        return clientDao.getClientList();
    }

    public void updateClientBalance(Client client, double amount) {
        client.setBalance(amount);
    }
    public void payForProducts(Client client, double totalPrice) {
        if(client.getBalance() < totalPrice) {
            double newBalance = client.getBalance() - totalPrice;
            client.setBalance(newBalance);
            clientDao.update(client);
        }
    }
}