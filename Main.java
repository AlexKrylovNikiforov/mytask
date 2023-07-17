package mytask;

import mytask.UI.Ui;
import mytask.exception.CashierNotFoundException;
import java.io.FileNotFoundException;

public class Main {
    private static final Ui ui = new Ui();

    public static void main(String[] args) throws FileNotFoundException, CashierNotFoundException {
        ui.start();
    }

}
