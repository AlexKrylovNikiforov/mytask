package mytask.service;

import mytask.dao.CashierDao;
import mytask.data.Cashier;
import mytask.data.Product;
import mytask.exception.CashierNotFoundException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CashierService {

    CashierDao cashierDao = new CashierDao();
    ProductService productService = new ProductService();

    public List<Cashier> getCashiersList() throws FileNotFoundException, CashierNotFoundException {
        return cashierDao.getCashiersList();
    }


    public double scanProduct(Product currentProduct) {
        return productService.getProductPrice(currentProduct);
    }
}
