package mytask.dao;

import mytask.data.Product;
import mytask.data.ProductType;
import mytask.exception.ProductNotFoundException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private static final Path DB_PATH = Path.of("product_table.txt");

    ProductType productType;

    public List<Product> getProductList() {
        List<Product> currentProductList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(DB_PATH.toFile()))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] productData = line.split(";");
                Long productId = Long.parseLong(productData[0]);
                String productName = productData[1];
                productType = ProductType.valueOf(productData[2]);
                double productPrice = Double.parseDouble(productData[3]);
                Product currentProduct = new Product(productId, productName, productType, productPrice);
                currentProductList.add(currentProduct);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentProductList;
    }
    public List<Long> getIdList() {
        List<Long> currentIds = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(DB_PATH.toFile()))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] productData = line.split(";");
                Long productId = Long.parseLong(productData[0]);
                currentIds.add(productId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentIds;
    }

    public Product getProductByName(String name) throws ProductNotFoundException {
        List<Product> currentProducts = getProductList();
        for(Product product: currentProducts) {
            if(product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public Product getProductById(long id) throws ProductNotFoundException {
        List<Product> currentProducts = getProductList();
        for(Product product: currentProducts) {
            if(product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void saveNewProduct(Product newProduct) {


    }
}
