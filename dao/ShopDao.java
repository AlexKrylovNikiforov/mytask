package mytask.dao;

import mytask.data.Product;
import mytask.data.ProductType;
import mytask.data.Shop;
import mytask.service.ProductService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopDao {

    private static final Path DB_PATH = Path.of("product_db.txt");


    public Map<Product, Integer> getCurrentWarehouse() {
        Map<Product, Integer> currentWarehouse = new HashMap<>();
        try {
            List<String> productLines = Files.readAllLines(DB_PATH);
            for(String productLine: productLines) {
                String [] products = productLine.split(";");
                long id = Long.parseLong(products[0]);
                String name = products[1];
                ProductType productType = ProductType.valueOf(products[2]);
                double price = Double.parseDouble(products[3]);
                int count = Integer.parseInt(products[4]);
                Product currentProduct = new Product(id, name, productType, price);
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
        getCurrentWarehouse().put(product, count);
    }

    public void saveWarehouse() {
        Map<Product, Integer> warehouse = getCurrentWarehouse();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DB_PATH.toFile()))) {
            for (Map.Entry<Product, Integer> entry : warehouse.entrySet()) {
                Product product = entry.getKey();
                int count = entry.getValue();
                String productData = (product.getId() + ";" + product.getName() + ";" + product.getProductType() + ";" + String.format("%.2f", product.getPrice()) + count);
                bw.write(productData);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
