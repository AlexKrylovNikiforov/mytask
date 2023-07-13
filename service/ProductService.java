package mytask.service;

import mytask.dao.ProductDao;
import mytask.data.Product;
import mytask.exception.ProductNotFoundException;

import java.util.List;

public class ProductService {
    private Product product;

    private final ProductDao productDao = new ProductDao();

    public String getProductName(Product product) {
        return product.getName();
    }

    public long returnProductId (Product product) {
        return product.getId();
    }

    public double getProductPrice(Product product) {
        return product.getPrice();
    }

    public Long assignId() {
        return (long) productDao.getIdList().size() + 1;
    }

    public List<Product> getProductList() {
        return productDao.getProductList();
    }
    public Product getProductByName(String name) {
        try {
            return productDao.getProductByName(name);
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Product getProductById(long id) {
        try {
            return productDao.getProductById(id);
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
