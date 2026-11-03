package service;

import model.BankAccount;
import java.util.ArrayList;

public class BankService {

    // Search account using account number
    public BankAccount findAccount(
            ArrayList<BankAccount> accounts,
            String accountNumber) {

        for (BankAccount account : accounts) {

            if (account.getAccountNumber()
                    .equals(accountNumber)) {
                return account;
            }
        }

        return null;
    }
}