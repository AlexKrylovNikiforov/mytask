package mytask.service;

import mytask.dao.ClientDao;
import mytask.domain.Client;
import mytask.domain.Product;
import mytask.exception.BalanceWrongValueException;
import mytask.exception.ClientNotFoundException;
import mytask.validation.ClientValidator;

import java.util.List;
import java.util.Map;

public class ClientService {
    ClientDao clientDao = new ClientDao();
    ClientValidator clientValidator = new ClientValidator();

    public Client createNewClient(long currentId, String currentName, double currentBalance) {
        Client newClient = new Client(currentId, currentName, currentBalance);
        try {
            clientValidator.validateBalance(newClient.getBalance());
        } catch (BalanceWrongValueException e) {
            System.out.println(e.getMessage());
        }
        clientDao.save(newClient);
        return newClient;
    }

    public Client getClientById(Long id) {
        try {
            return clientDao.findClientById(id);
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int assignId() {
        return clientDao.getIdList().size() + 1;
    }

    public Map<Product, Integer> getClientBasket() {
        return clientDao.getClientBasket();
    }

    public void addProductToBasket(Client client, Product product, int count) {
        Map<Product, Integer> clientBasket = clientDao.getClientBasket();
        clientDao.addProductToBasket(product, count);
        clientDao.updateClientsBasket(client, clientBasket);
    }
    public void removeProductFromBasket(Product product, int count) {
        clientDao.getClientBasket().put(product, count);
    }

    public List<Client> getClientsList() {
        return clientDao.getClientList();
    }

    public void updateClientBalance(Client client, double amount) {
        client.setBalance(amount);
    }
    public void payForProducts(Client client, double totalPrice) {
        if(clientValidator.isBalanceEnough(client.getBalance(), totalPrice)) {
            double newBalance = client.getBalance() - totalPrice;
            client.setBalance(newBalance);
            clientDao.update(client);
        }
    }
}
