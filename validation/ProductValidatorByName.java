package mytask.validation;

import mytask.data.Product;
import mytask.service.ProductService;
import mytask.service.ShopService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductValidatorByName {
    ShopService shopService = new ShopService();
    ProductService ps = new ProductService();

    public boolean validateProductByName(String name) {
        List<String> currentProducts = new ArrayList<>();
        Map<Product, Integer> currentWarehouse = shopService.getCurrentWarehouse();
        for(Map.Entry<Product, Integer> entry: currentWarehouse.entrySet()) {
            String productName = ps.getProductName(entry.getKey());
            currentProducts.add(productName);
        }
        return currentProducts.contains(name);
    }
}
