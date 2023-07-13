package mytask.dao;

import mytask.data.Product;
import mytask.data.Shop;
import mytask.service.ProductService;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ShopDao {

    private static final Path DB_PATH = Path.of("warehouse_db.txt");
    private final ProductService productService = new ProductService();


    public Map<Product, Integer> getCurrentWarehouse() {
        Map<Product, Integer> currentWarehouse = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(DB_PATH.toFile()));
            String line;
            while((line = br.readLine()) != null) {
                String [] products = line.split(";");
                long id = Long.parseLong(products[0]);
                int count = Integer.parseInt(products[1]);
                Product currentProduct = productService.getProductById(id);
                currentWarehouse.put(currentProduct, count);
            }
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));;
        }
        return currentWarehouse;
    }

    public Product getProductByName(String name) {
        for(Map.Entry<Product, Integer> entry: getCurrentWarehouse().entrySet()) {
            if(entry.getKey().getName().equalsIgnoreCase(name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void updateCurrentWarehouse(Product product, int count) {
        Map<Product, Integer> currentWarehouse = getCurrentWarehouse();
        currentWarehouse.put(product, count);
    }

    public void saveWarehouse(Map<Product, Integer> warehouse) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DB_PATH.toFile()))) {
            for (Map.Entry<Product, Integer> entry : warehouse.entrySet()) {
                Product product = entry.getKey();
                int count = entry.getValue();

                bw.write(product.getId() + "; " + count);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
