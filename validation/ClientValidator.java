package mytask.validation;

import mytask.exception.IllegalBalanceException;

public class ClientValidator {
    public boolean validateBalance(double balance) throws IllegalBalanceException {
        if(balance > 1000) {
            throw new IllegalBalanceException("Please enter amount less than 1000");
        }
        return true;
    }
}
