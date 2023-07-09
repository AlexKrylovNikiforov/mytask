package mytask.service;

import mytask.dao.ShopDao;
import mytask.data.Product;
import mytask.validation.ProductValidatorByCount;
import mytask.validation.ProductValidatorByName;

import java.util.Map;

public class ShopService {

    private ShopDao shopDao = new ShopDao();

    private ProductValidatorByCount validatorByCount = new ProductValidatorByCount();

    private ProductValidatorByName validatorByName = new ProductValidatorByName();


    public Map<Product, Integer> getCurrentWarehouse() {
        Map<Product, Integer> currentWarehouse = shopDao.getCurrentWarehouse();
        return currentWarehouse;
    }

    public boolean isProductInWarehouse(String productName) {
        return validatorByName.validateProductByName(productName);
    }

    public boolean isProductCountEnough(String name, int count) {
        return validatorByCount.validateProductByCount(name, count);
    }
}
