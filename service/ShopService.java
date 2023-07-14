package mytask.service;

import mytask.dao.ShopDao;
import mytask.data.*;
import mytask.exception.CashierNotFoundException;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

    public boolean isProductCountEnough(Product product, int count) {
        for(Map.Entry<Product, Integer> entry: getCurrentWarehouse().entrySet()) {
            if(entry.getKey().getName().equalsIgnoreCase(product.getName())) {
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
        //shopDao.saveWarehouse();
    }

    public void processPayment(Client client) throws FileNotFoundException, CashierNotFoundException {
        CashierService cashierService = new CashierService();
        ClientService clientService = new ClientService();
        double totalPrice = 0.0;
        for(Map.Entry<Product, Integer> entry: client.getBasket().entrySet()) {
            Product currentProduct = entry.getKey();
            List<Cashier> cashiers = cashierService.getCashiersList();
            Cashier currentCashier = getCashierByProductType(cashiers, currentProduct.getProductType());
            totalPrice += cashierService.scanProduct(currentProduct);
        }
        clientService.updateClientBalance(client, totalPrice);
    }

    private Cashier getCashierByProductType(List<Cashier> cashierList, ProductType productType) throws CashierNotFoundException {
        for(Cashier cashier: cashierList) {
            if(cashier.getProductTypes().contains(productType)) {
                return cashier;
            }
        }
        return null;
    }

    public void getProductToTransferToClient(Client client) {
        Product newProduct;
        String reply;
        Scanner sc = new Scanner(System.in);
        ClientService cs = new ClientService();
        do {
            System.out.println("Please enter product name or enter Q to exit to the main menu: ");
            reply = sc.nextLine();

            if (reply.equalsIgnoreCase("Q")) {
                break;
            }
            newProduct = getProductByName(reply);
            boolean productInWarehouse = isProductInWarehouse(reply);
            if (productInWarehouse) {
                System.out.println("Please enter product count: ");
                int count = Integer.parseInt(sc.nextLine());
                boolean productCountEnough = isProductCountEnough(newProduct, count);
                if (productCountEnough) {
                    cs.addProductToBasket(client, newProduct, count);
                    updateWarehouse(newProduct, count);
                } else {
                    System.out.println("Not enough product in warehouse");
                }
            }
        } while (!reply.equalsIgnoreCase("Q"));
    }
}
