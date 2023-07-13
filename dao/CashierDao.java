package mytask.dao;

import mytask.data.Cashier;
import mytask.data.Client;
import mytask.data.ProductType;
import mytask.exception.CashierNotFoundException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CashierDao {

    private static final Path DB_PATH = Path.of("cashier_db.txt");

    private final List<Cashier> currentCashiers = new ArrayList<>();

    public CashierDao() {
    }

    public List<Cashier> getCashiersList() throws CashierNotFoundException, FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(DB_PATH.toFile()))) {
            String line;
            List<ProductType> productTypes = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] cashierData = line.split(";");
                long cashierId = Long.parseLong(cashierData[0]);
                String cashierName = cashierData[1];
                String [] productTypesData = cashierData[2].split(",");
                for(String productTypeData: productTypesData) {
                    ProductType productType = mytask.data.ProductType.valueOf(productTypeData);
                    productTypes.add(productType);
                }
                Cashier newCashier = new Cashier(cashierId, cashierName, productTypes);
                currentCashiers.add(newCashier);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return currentCashiers;
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
