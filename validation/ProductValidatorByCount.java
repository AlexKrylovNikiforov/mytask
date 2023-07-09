package mytask.validation;

import mytask.data.Product;
import mytask.service.ProductService;
import mytask.service.ShopService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductValidatorByCount {

    ShopService shopService = new ShopService();
    ProductService ps = new ProductService();

    public boolean validateProductByCount(String name, int count) {
        Map<Product, Integer> currentWarehouse = shopService.getCurrentWarehouse();
        for(Map.Entry<Product, Integer> entry: currentWarehouse.entrySet()) {
            if(shopService.isProductInWarehouse(entry.getKey().getName())) {
                return count <= entry.getValue();
            }
        }
        return false;
    }
}
