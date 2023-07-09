package mytask.dao;

import mytask.data.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private static final Path DB_PATH = Path.of("product_table.txt");

    public List<Long> getIdList() {
        List<Long> currentIds = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(DB_PATH.toFile()))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] productData = line.split(";");
                Long productIds = Long.parseLong(productData[0]);
                currentIds.add(productIds);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentIds;
    }

    public Product findByName(String name) {

        return null;
    }

    public void saveNewProduct(Product newProduct) {

    }
}
