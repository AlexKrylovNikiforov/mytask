package mytask.domain;

import mytask.type.ProductType;

public class Product {
    private final long id;

    private final String name;

    private double price;

    private final ProductType productType;

    public Product(Long id, String name, ProductType productType, double price) {
        this.id = id;
        this.name = name;
        this.productType = productType;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
