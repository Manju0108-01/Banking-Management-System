package util;
import model.BankAccount;
import model.Transaction;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class BankFileManager {

    private static final String FILE_NAME = "accounts.txt";
    private static final String TRANSACTION_FILE = "transactions.txt";

    // Save account details
    public static void saveAccounts(ArrayList<BankAccount> accounts) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (BankAccount account : accounts) {

                writer.write(
                        account.getAccountNumber() + "|" +
                        account.getAccountHolderName() + "|" +
                        account.getBalance() + "|" +
                        account.getPinForStorage()
                );

                writer.newLine();
            }

            System.out.println("Account data saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    // Load account details
    public static ArrayList<BankAccount> loadAccounts() {

        ArrayList<BankAccount> accounts = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return accounts;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|", -1);

                if (data.length == 4) {

                    BankAccount account = new BankAccount(
                            data[0],
                            data[1],
                            new BigDecimal(data[2]),
                            data[3]
                    );

                    accounts.add(account);
                }
            }

            System.out.println("Account data loaded successfully!");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }

        return accounts;
    }

    // Save transaction history
    public static void saveTransactions(ArrayList<BankAccount> accounts) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(TRANSACTION_FILE))) {

            for (BankAccount account : accounts) {

                for (Transaction transaction : account.getTransactions()) {

                    writer.write(
                            account.getAccountNumber() + "|" +
                            transaction.getType() + "|" +
                            transaction.getAmount() + "|" +
                            transaction.getDateTime()
                    );

                    writer.newLine();
                }
            }

            System.out.println("Transaction history saved!");

        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    // Load transaction history
    public static void loadTransactions(ArrayList<BankAccount> accounts) {

        File file = new File(TRANSACTION_FILE);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(TRANSACTION_FILE))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|", 4);

                if (data.length == 4) {

                    for (BankAccount account : accounts) {

                        if (account.getAccountNumber().equals(data[0])) {

                            Transaction transaction = new Transaction(
                                    data[1],
                                    new BigDecimal(data[2]),
                                    LocalDateTime.parse(data[3])
                            );

                            account.addTransaction(transaction);
                            break;
                        }
                    }
                }
            }

            System.out.println("Transaction history loaded!");

        } catch (IOException | NumberFormatException
                 | DateTimeParseException e) {

            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }
}
