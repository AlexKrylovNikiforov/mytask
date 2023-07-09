package mytask.data;

public class Product {
    private final Long ID;

    private final String name;

    private double price;

    public Product(Long id, String name) {
        ID = id;
        this.name = name;
    }

    public Long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
