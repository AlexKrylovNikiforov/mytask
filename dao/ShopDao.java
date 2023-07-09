package mytask.dao;

import mytask.data.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ShopDao {

    private static final Path DB_PATH = Path.of("warehouse_db.txt");

    private Map<Product, Integer> warehouse;
    public Map<Product, Integer> getCurrentWarehouse() {
        Map<Product, Integer> currentWarehouse = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(DB_PATH.toFile()));
            String line;
            while((line = br.readLine()) != null) {
                String [] products = line.split(";");
            }
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));;
        }
        return null;
    }

}
