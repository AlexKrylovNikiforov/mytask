package mytask.service;

import mytask.dao.ShopDao;
import mytask.data.Cashier;
import mytask.data.Product;
import mytask.data.ProductType;
import mytask.data.Shop;
import mytask.exception.CashierNotFoundException;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class ShopService {

    private final ShopDao shopDao = new ShopDao();

    public Map<Product, Integer> getCurrentWarehouse() {
        return shopDao.getCurrentWarehouse();
    }
    public Shop initializeShop() {
        CashierService cashierService = new CashierService();
        Shop shop = null;
        try {
            List<Cashier> cashierList = cashierService.getCashiersList();
            shop = new Shop(cashierList, getCurrentWarehouse());
        } catch (FileNotFoundException | CashierNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return shop;
    }
    public boolean isProductInWarehouse(String productName) {
        for(Map.Entry<Product, Integer> entry: getCurrentWarehouse().entrySet()) {
            if(entry.getKey().getName().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isProductCountEnough(String name, int count) {
        for(Map.Entry<Product, Integer> entry: getCurrentWarehouse().entrySet()) {
            if(isProductInWarehouse(entry.getKey().getName())) {
                return count <= entry.getValue();
            }
        }
        return false;
    }

    public Product getProductByName(String name) {
        return shopDao.getProductByName(name);
    }

    public void updateWarehouse(Product newProduct, int count) {
        shopDao.updateCurrentWarehouse(newProduct, count);
        Map<Product, Integer> currentWarehouse1 = shopDao.getCurrentWarehouse();
        shopDao.saveWarehouse(currentWarehouse1);
    }

    public Cashier getCashierByProductType(List<Cashier> cashierList, ProductType productType) throws CashierNotFoundException {
        for(Cashier cashier: cashierList) {
            if(cashier.getProductTypes().contains(productType)) {
                return cashier;
            }
        }
        return null;
    }
}
