package mytask.service;

import mytask.dao.ProductDao;
import mytask.data.Product;

public class ProductService {
    private Product product;

    private ProductDao pd = new ProductDao();

    public String getProductName(Product product) {
        return product.getName();
    }

    public long returnProductId (Product product) {
        return product.getID();
    }

    public double getProductPrice(Product product) {
        return product.getPrice();
    }

    public Product createNewProduct (String name) {
        Long currentId = assignId();
        Product newProduct = new Product(currentId, name);
        pd.saveNewProduct(newProduct);
        return newProduct;
    }
    public Long assignId() {
        return (long) pd.getIdList().size() + 1;
    }

}
