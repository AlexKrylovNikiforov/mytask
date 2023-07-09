package mytask.UI;

public enum UiCommand {

    ENTER_ID(0, "Enter ID for existing client"),
    NEW_CLIENT(1, "Enter ID or select New client"),
    SHOW_PRODUCTS(2, "Show products in shop"),
    SHOW_BASKET(3, "Go to your basket"),
    BUY_PRODUCTS(4, "Start buying"),
    RETURN_PRODUCTS(5, "Return products"),
    EXIT_ANYTIME(6, "Exit app");

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

