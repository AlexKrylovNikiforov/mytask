package mytask.data;

public enum ProductType {
    ALCOHOL(1, "Alcoholic beverages and beer"),
    TOBACCO(2, "Tobacco & cigars"),
    MEAT(3, "All kind of meat and chicken"),
    FISH(4, "All kind of fish"),
    FRUIT(5, "All varieties of fruits"),
    VEGETABLE(6, "All varieties of vegetables");

    private final int label;
    private final String description;

    ProductType(int label, String description) {
        this.label = label;
        this.description = description;
    }

    public int getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}
