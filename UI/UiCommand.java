package mytask.UI;

public enum UiCommand {

    SHOW_PRODUCTS(1, "Show products in shop"),
    SHOW_BASKET(2, "Go to your basket"),
    ADD_PRODUCTS(3, "Add products to basket"),
    BUY_PRODUCTS(4, "Proceed to payment"),
    RETURN_PRODUCTS(5, "Return products"),
    EXIT_ANYTIME(0, "Exit app");

    private final int label;

    private final String description;

    UiCommand(int label, String description) {
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

