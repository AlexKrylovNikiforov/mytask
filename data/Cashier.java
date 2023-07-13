package mytask.data;

import java.util.List;

public class Cashier {
    private final long id;

    private final String name;

    private final List<ProductType> productTypes;

    public Cashier(long id, String name, List<ProductType> productTypes) {
        this.id = id;
        this.name = name;
        this.productTypes = productTypes;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ProductType> getProductTypes() {
        return productTypes;
    }
}
