package mytask.dao;

import mytask.data.Client;
import mytask.data.Product;
import mytask.exception.NoClientFoundException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ClientDao {
    private static final Path DB_PATH = Path.of("client_db.txt");
    private final ProductDao productDao = new ProductDao();

    public Client findClientById(Long id) throws NoClientFoundException {
        Client client = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(DB_PATH.toFile()));
            String line;

            while ((line = br.readLine()) != null) {
                String[] currentClient = line.split(";");
                Long clientId = Long.parseLong(currentClient[0]);

                if (clientId.equals(id)) {
                    double balance = Double.parseDouble(currentClient[2]);
                    client = new Client(clientId, currentClient[1], balance);
                    break;
                }
            }
            br.close();

            if (client == null) {
                throw new NoClientFoundException("No client found with the given ID: " + id);
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
    public void showClientBasket(Client client) {
        Map<Product, Integer> currentBasket = client.getBasket();
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
    public void updateBasket(Product product, int count) {
    }

    public Map<Product, Integer> getClientBasket() {
        return null;
    }
}
