package mytask.service;

import mytask.dao.ClientDao;
import mytask.data.Client;
import mytask.data.Product;
import mytask.exception.IllegalBalanceException;
import mytask.exception.NoClientFoundException;
import mytask.validation.ClientValidator;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ClientService {
    ClientDao clientDao = new ClientDao();
    ClientValidator clientValidator = new ClientValidator();

    public Client createNewClient(long currentId, String currentName, double currentBalance) {
        Client newClient = new Client(currentId, currentName, currentBalance);
        try {
            clientValidator.validateBalance(newClient.getBalance());
        } catch (IllegalBalanceException e) {
            System.out.println(e.getMessage());
        }
        clientDao.save(newClient);
        return newClient;
    }

    public Client getClientById(Long id) {
        try {
            return clientDao.findClientById(id);
        } catch (NoClientFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int assignId() {
        return clientDao.getIdList().size() + 1;
    }

    public void showClientBasket() {
        Map<Product, Integer> currentBasket = clientDao.getClientBasket();
        double totalPrice = 0.0;
        for (Map.Entry<Product, Integer> entry : currentBasket.entrySet()) {
            String name = entry.getKey().getName();
            int count = entry.getValue();
            double price = entry.getKey().getPrice();
            System.out.printf("%s: %d, Price: %f EUR%n", name, count, price);
            totalPrice += price;
        }
        System.out.printf("Total price: %f EUR%n", totalPrice);

    }

    public void addProductToBasket(Product product, int count) {
        clientDao.updateBasket(product, count);
    }
}