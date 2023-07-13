package mytask;

import mytask.UI.Ui;
import mytask.data.Cashier;
import mytask.exception.CashierNotFoundException;
import mytask.service.CashierService;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    private static final Ui ui = new Ui();

    public static void main(String[] args) throws FileNotFoundException, CashierNotFoundException {
        ui.initializeApp();
        ui.start();
    }

}
