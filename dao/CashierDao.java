package mytask.dao;

import mytask.dao.api.Dao;
import mytask.domain.Cashier;
import mytask.type.ProductType;
import mytask.exception.CashierNotFoundException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CashierDao implements Dao<Cashier> {

    private static final Path DB_PATH = Path.of("cashier_db.txt");

    @Override
    public Cashier getById(int id) {
        return null;
    }

    @Override
    public Cashier save(Cashier entity) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void delete(Cashier entity) {

    }

    @Override
    public List<Cashier> getAll() {
        List<String> strings = Files.readAllLines(DB_PATH);
        for (String string: strings) {

        }
        return null;
    }

    // TODO: Здесь не должно быть полей класса
    private final List<Cashier> currentCashiers = new ArrayList<>();

    public CashierDao() {
    }

    public List<Cashier> getCashiersList() throws CashierNotFoundException, FileNotFoundException {
        // TODO: Используй эту структуру вместо Buffered/File reader'ов
//        List<String> cashierLines = Files.readAllLines(DB_PATH);

        try (BufferedReader br = new BufferedReader(new FileReader(DB_PATH.toFile()))) {
            String line;
            List<ProductType> productTypes = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] cashierData = line.split(";");
                long cashierId = Long.parseLong(cashierData[0]);
                String cashierName = cashierData[1];
                String [] productTypesData = cashierData[2].split(",");
                for(String productTypeData: productTypesData) {
                    ProductType productType = ProductType.valueOf(productTypeData);
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

    // TODO: Удаляй те методы, которые не используешь
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
