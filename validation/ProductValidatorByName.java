package mytask.validation;

import mytask.service.ProductService;

public class ProductValidatorByName {
//    ShopService shopService = new ShopService();
    ProductService ps = new ProductService();

    public boolean validateProductByName(String name) {
//        List<String> currentProducts = new ArrayList<>();
//        Map<Product, Integer> currentWarehouse = shopService.getCurrentWarehouse();
//        for(Map.Entry<Product, Integer> entry: currentWarehouse.entrySet()) {
//            String productName = ps.getProductName(entry.getKey());
//            currentProducts.add(productName);
//        }
//        return currentProducts.contains(name);
        return false;
    }
}
