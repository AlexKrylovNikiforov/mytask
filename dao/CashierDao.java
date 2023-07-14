package mytask.dao;

import mytask.data.Cashier;
import mytask.data.Product;
import mytask.data.ProductType;
import mytask.exception.CashierNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CashierDao {

    private static final Path DB_PATH = Path.of("cashier_db.txt");

    private final List<Cashier> currentCashiers = new ArrayList<>();

    public CashierDao() {
    }

    public List<Cashier> getCashiersList() {
        List<String> cashierLines = null;
        try {
            cashierLines = Files.readAllLines(DB_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Cashier> allCashiers = new ArrayList<>();
        for(String cashierLine: cashierLines) {
            String [] currentCashier = cashierLine.split(";");
            long id = Long.parseLong(currentCashier[0]);
            String name = currentCashier[1];
            String[] productTypes = currentCashier[2].split(",");
            List<ProductType> currentProductTypes = new ArrayList<>();
            for (int i = 0; i < productTypes.length; i++) {
                ProductType productType = ProductType.valueOf(productTypes[i]);
                currentProductTypes.add(productType);
            }
            Cashier newCashier = new Cashier(id, name, currentProductTypes);
            allCashiers.add(newCashier);
        }
        return allCashiers;
}

    public Cashier getCashierById(long id) throws CashierNotFoundException {
        try {
            for(Cashier cashier: currentCashiers) {
                if(cashier.getId() == id) {
                    return cashier;
                }
            }
        } catch (Exception e) {
            throw new CashierNotFoundException("No cashier found with ID: " + id);
        }
        return null;
    }
}
