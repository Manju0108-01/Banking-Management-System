package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigDecimal;
import java.util.ArrayList;

public class BankAccount {
    public static String hashPin(String pin) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hash = md.digest(
                pin.getBytes(StandardCharsets.UTF_8));

        StringBuilder hex = new StringBuilder();

        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }

        return hex.toString();

    } catch (NoSuchAlgorithmException e) {
        throw new IllegalStateException("SHA-256 is unavailable", e);
    }
}

    private String accountNumber;
    private String accountHolderName;
    private BigDecimal balance;
    private String pin;

    private ArrayList<Transaction> transactions = new ArrayList<>();

    public BankAccount(String accountNumber,
                       String accountHolderName,
                       BigDecimal balance,
                       String pin) {

        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.pin = pin;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getPinForStorage() {
    return pin;
}

    public boolean verifyPin(String enteredPin) {

    // Check accounts that already have hashed PINs
    if (pin.length() == 64) {
        return pin.equals(hashPin(enteredPin));
    }

    // Support old accounts with plain-text PINs
    if (pin.equals(enteredPin)) {
        pin = hashPin(enteredPin);
        return true;
    }

    return false;
}

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void deposit(BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Invalid deposit amount!");
            return;
        }

        balance = balance.add(amount);

        transactions.add(new Transaction("Deposit", amount));

        System.out.println("Deposit successful!");
    }

    public void withdraw(BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Invalid withdrawal amount!");
            return;
        }

        if (amount.compareTo(balance) > 0) {
            System.out.println("Insufficient balance!");
            return;
        }

        balance = balance.subtract(amount);

        transactions.add(new Transaction("Withdrawal", amount));

        System.out.println("Withdrawal successful!");
    }

    public void transfer(BankAccount receiver, BigDecimal amount) {

        if (receiver == null) {
            System.out.println("Receiver account not found!");
            return;
        }

        if (receiver == this) {
            System.out.println("Cannot transfer to the same account!");
            return;
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Invalid transfer amount!");
            return;
        }

        if (amount.compareTo(balance) > 0) {
            System.out.println("Insufficient balance!");
            return;
        }

        balance = balance.subtract(amount);
        receiver.balance = receiver.balance.add(amount);

        transactions.add(new Transaction(
                "Transfer Sent to " + receiver.accountNumber, amount));

        receiver.transactions.add(new Transaction(
                "Transfer Received from " + accountNumber, amount));

        System.out.println("Money transferred successfully!");
    }

    public void showTransactionHistory() {

        System.out.println("\n===== TRANSACTION HISTORY =====");

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction transaction : transactions) {
            transaction.display();
        }
    }
}
