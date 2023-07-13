package mytask.service;

import mytask.dao.ShopDao;
import mytask.data.Cashier;
import mytask.data.Product;
import mytask.data.ProductType;
import mytask.data.Shop;
import mytask.exception.CashierNotFoundException;
import mytask.validation.ProductValidatorByCount;
import mytask.validation.ProductValidatorByName;

import java.util.List;
import java.util.Map;

public class ShopService {

    private final ShopDao shopDao = new ShopDao();

    private final ProductValidatorByCount validatorByCount = new ProductValidatorByCount();

    private final ProductValidatorByName validatorByName = new ProductValidatorByName();

    public Map<Product, Integer> getCurrentWarehouse() {
        return shopDao.getCurrentWarehouse();
    }

    public boolean isProductInWarehouse(String productName) {
        return validatorByName.validateProductByName(productName);
    }

    public boolean isProductCountEnough(String name, int count) {
        return validatorByCount.validateProductByCount(name, count);
    }

    public Product getProductByName(String name) {
        return shopDao.getProductByName(name);
    }

    public void updateWarehouse(Product newProduct, int count) {
        shopDao.updateCurrentWarehouse(newProduct, count);
        Map<Product, Integer> currentWarehouse1 = shopDao.getCurrentWarehouse();
        shopDao.saveWarehouse(currentWarehouse1);
    }

    public Shop createNewShop(Map<Product, Integer> warehouse) {
        return new Shop(warehouse);
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
