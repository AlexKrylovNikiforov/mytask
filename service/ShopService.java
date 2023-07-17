package mytask.service;

import mytask.dao.ShopDao;
import mytask.data.*;
import mytask.exception.CashierNotFoundException;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;


public class ShopService {

    private final ShopDao shopDao = new ShopDao();

    public Map<Product, Integer> getCurrentWarehouse() {
        return shopDao.getCurrentWarehouse();
    }
//    public Shop initializeShop() {
//        CashierService cashierService = new CashierService();
//        Shop shop = null;
//        try {
//            List<Cashier> cashierList = cashierService.getCashiersList();
//            shop = new Shop(cashierList, getCurrentWarehouse());
//        } catch (FileNotFoundException | CashierNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//        return shop;
//    }
    public boolean isProductInWarehouse(String productName) {
        for(Map.Entry<Product, Integer> entry: getCurrentWarehouse().entrySet()) {
            if(entry.getKey().getName().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isProductCountEnough(Product product, int count) {
        for(Map.Entry<Product, Integer> entry: getCurrentWarehouse().entrySet()) {
            if(entry.getKey().getName().equalsIgnoreCase(product.getName())) {
                return count <= entry.getValue();
            }
        }
        return false;
    }

    public int  getCurrentProductCount(Product product) {
        int currentCount = 0;
        for(Map.Entry<Product, Integer> entry: getCurrentWarehouse().entrySet()) {
            if (entry.getKey().getName().equalsIgnoreCase(product.getName())) {
                currentCount = entry.getValue();
            }
        }
        return currentCount;
    }


    public Product getProductByName(String name) {
        return shopDao.getProductByName(name);
    }

    public void updateWarehouse(Product newProduct, int count) {
        shopDao.updateCurrentWarehouse(newProduct, count);
        //shopDao.saveWarehouse();
    }

    public void processPayment(Client client) {
        CashierService cashierService = new CashierService();
        ClientService clientService = new ClientService();
        double totalPrice = 0.0;
        for(Map.Entry<Product, Integer> entry: client.getBasket().entrySet()) {
            Product currentProduct = entry.getKey();
            totalPrice += cashierService.scanProduct(currentProduct);
        }
        clientService.updateClientBalance(client, totalPrice);
    }

//    private Cashier getCashierByProductType(List<Cashier> cashierList, ProductType productType) throws CashierNotFoundException {
//        for(Cashier cashier: cashierList) {
//            if(cashier.getProductTypes().contains(productType)) {
//                return cashier;
//            }
//        }
//        return null;
//    }
}
