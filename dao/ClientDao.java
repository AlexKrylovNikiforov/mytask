package mytask.dao;

import mytask.domain.Client;
import mytask.domain.Product;
import mytask.exception.ClientNotFoundException;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ClientDao {
    private static final Path DB_PATH = Path.of("client_db.txt");
    private final ProductDao productDao = new ProductDao();

    public Client findClientById(Long id) throws ClientNotFoundException {
        Client client = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(DB_PATH.toFile()));
            String line;

            while ((line = br.readLine()) != null) {
                String[] currentClient = line.split(";");
                long clientId = Long.parseLong(currentClient[0]);

                if (id == clientId) {
                    String name = currentClient[1];
                    double balance = Double.parseDouble(currentClient[2]);
                    client = new Client(clientId, name, balance);
                    break;
                }
            }
            br.close();

            if (client == null) {
                throw new ClientNotFoundException("No client found with the given ID: " + id);
            }
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return client;
    }

    public void update(Client client) {

    }

    public void save(Client client) {

    }

    public List<Long> getIdList() {
        List<Long> currentIds = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(DB_PATH.toFile()))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] clientData = line.split(";");
                Long clientId = Long.parseLong(clientData[0]);
                currentIds.add(clientId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentIds;
    }
    public void updateClientsBasket(Client client, Map<Product, Integer> newBasket) {
        client.setBasket(newBasket);
        updateClientData(client);
    }

    public void addProductToBasket(Product product, int count) {
        Map<Product, Integer> basket = getClientBasket();
        if (basket.containsKey(product)) {
            basket.put(product, basket.get(product) + count);
        } else {
            basket.put(product, count);
        }
    }
    public Map<Product, Integer> getClientBasket() {
        return null;
    }
    private void updateClientData(Client client) {
        try {
            File inputFile = DB_PATH.toFile();
            File tempFile = new File("temp.txt");

            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = br.readLine()) != null) {
                String[] clientData = line.split(";");
                long id = Long.parseLong(clientData[0]);
                if (id == client.getId()) {
                    line = client.getId() + ";" + client.getName() + ";" + client.getBalance();
                }
                bw.write(line);
                bw.newLine();
            }
            br.close();
            bw.close();

            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            } else {
                throw new IOException("Не удалось обновить данные клиента");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Client> getClientList () {
        List<Client> currentClients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DB_PATH.toFile()))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] clientData = line.split(";");
                Long clientId = Long.parseLong(clientData[0]);
                String clientName = clientData[1];
                double clientBalance = Double.parseDouble(clientData[2]);
                Client newClient = new Client(clientId, clientName, clientBalance);
                currentClients.add(newClient);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return currentClients;
    }

}
