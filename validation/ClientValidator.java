package mytask.validation;

import mytask.exception.BalanceWrongValueException;

public class ClientValidator {
    public boolean validateBalance(double balance) throws BalanceWrongValueException {
        if(balance > 1000) {
            throw new BalanceWrongValueException("Please enter amount less than 1000");
        }
        return true;
    }

    public boolean isBalanceEnough(double balance, double amount) {
        return balance >= amount;
    }
}
