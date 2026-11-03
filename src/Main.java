
import model.BankAccount;
import util.BankFileManager;
import service.BankService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static int readInt(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Enter a number: ");
            }
        }
    }

    public static BigDecimal readAmount(Scanner sc) {
        while (true) {
            try {
                return new BigDecimal(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print(
                    "Invalid amount! Enter a valid amount: ");
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<BankAccount> accounts =
                BankFileManager.loadAccounts();

        BankFileManager.loadTransactions(accounts);

        BankService bankService = new BankService();

        // Create sample accounts only on first run
        if (accounts.isEmpty()) {

            accounts.add(new BankAccount(
                    "1001", "Manju",
                    new BigDecimal("5000.00"),
                    BankAccount.hashPin("1234")));

            accounts.add(new BankAccount(
                    "1002", "Rahul",
                    new BigDecimal("8000.00"),
                    BankAccount.hashPin("2345")));

            accounts.add(new BankAccount(
                    "1003", "Priya",
                    new BigDecimal("6000.00"),
                    BankAccount.hashPin("3456")));

            BankFileManager.saveAccounts(accounts);
        }

        int choice;

        do {
            System.out.println("\n===== BANKING MANAGEMENT SYSTEM =====");
            System.out.println("1. Login to Account");
            System.out.println("2. Create New Account");
            System.out.println("3. View All Accounts");
            System.out.println("4. Transfer Money");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            choice = readInt(sc);

            switch (choice) {

                case 1: {
                    System.out.print("Enter Account Number: ");
                    String accountNumber = sc.nextLine().trim();

                    BankAccount selectedAccount =
                            bankService.findAccount(
                                    accounts, accountNumber);

                    if (selectedAccount == null) {
                        System.out.println("Account Not Found!");
                        break;
                    }

                    boolean authenticated = false;

                    for (int attempt = 1; attempt <= 3; attempt++) {
                        System.out.print("Enter your 4-digit PIN: ");
                        String enteredPin = sc.nextLine();

                        if (selectedAccount.verifyPin(enteredPin)) {
                            authenticated = true;
                            break;
                        } else {
                            System.out.println(
                                    "Incorrect PIN! Attempts remaining: "
                                    + (3 - attempt));
                        }
                    }

                    if (!authenticated) {
                        System.out.println(
                                "Maximum attempts reached. Login denied!");
                        break;
                    }

                    BankFileManager.saveAccounts(accounts);

                    System.out.println("\nWelcome, "
                            + selectedAccount.getAccountHolderName());

                    int operation;

                    do {
                        System.out.println("\n===== ACCOUNT MENU =====");
                        System.out.println("1. Check Balance");
                        System.out.println("2. Deposit Money");
                        System.out.println("3. Withdraw Money");
                        System.out.println("4. Transaction History");
                        System.out.println("5. Back to Main Menu");

                        System.out.print("Enter your choice: ");
                        operation = readInt(sc);

                        switch (operation) {

                            case 1:
                                System.out.println("Balance: Rs. "
                                        + selectedAccount.getBalance());
                                break;

                            case 2:
                                System.out.print("Enter deposit amount: ");
                                BigDecimal depositAmount = readAmount(sc);

                                selectedAccount.deposit(depositAmount);

                                BankFileManager.saveAccounts(accounts);
                                BankFileManager.saveTransactions(accounts);
                                break;

                            case 3:
                                System.out.print("Enter withdrawal amount: ");
                                BigDecimal withdrawalAmount =
                                        readAmount(sc);

                                selectedAccount.withdraw(withdrawalAmount);

                                BankFileManager.saveAccounts(accounts);
                                BankFileManager.saveTransactions(accounts);
                                break;

                            case 4:
                                selectedAccount.showTransactionHistory();
                                break;

                            case 5:
                                System.out.println(
                                        "Returning to main menu...");
                                break;

                            default:
                                System.out.println("Invalid Choice!");
                        }

                    } while (operation != 5);

                    break;
                }

                case 2: {
                    System.out.println("\n===== CREATE NEW ACCOUNT =====");

                    String newAccountNumber;

                    while (true) {
                        System.out.print("Enter Account Number: ");
                        newAccountNumber = sc.nextLine().trim();

                        if (!newAccountNumber.matches("\\d+")) {
                            System.out.println(
                                    "Account number must contain digits only!");
                        } else if (bankService.findAccount(
                                accounts, newAccountNumber) != null) {
                            System.out.println(
                                    "Account Number Already Exists!");
                        } else {
                            break;
                        }
                    }

                    String holderName;

                    while (true) {
                        System.out.print("Enter Account Holder Name: ");
                        holderName = sc.nextLine().trim();

                        if (holderName.isEmpty()) {
                            System.out.println(
                                    "Account holder name cannot be empty!");
                        } else {
                            break;
                        }
                    }

                    BigDecimal initialDeposit;

                    while (true) {
                        System.out.print("Enter Initial Deposit: ");
                        initialDeposit = readAmount(sc);

                        if (initialDeposit.compareTo(
                                BigDecimal.ZERO) < 0) {
                            System.out.println(
                                    "Initial deposit cannot be negative!");
                        } else {
                            break;
                        }
                    }

                    String newPin;

                    while (true) {
                        System.out.print("Create a 4-digit PIN: ");
                        newPin = sc.nextLine().trim();

                        if (!newPin.matches("\\d{4}")) {
                            System.out.println(
                                    "PIN must contain exactly 4 digits!");
                        } else {
                            break;
                        }
                    }

                    String hashedPin = BankAccount.hashPin(newPin);

                    BankAccount newAccount = new BankAccount(
                            newAccountNumber,
                            holderName,
                            initialDeposit,
                            hashedPin
                    );

                    accounts.add(newAccount);
                    BankFileManager.saveAccounts(accounts);

                    System.out.println("Account Created Successfully!");
                    System.out.println("Account Number: "
                            + newAccount.getAccountNumber());
                    System.out.println("Account Holder: "
                            + newAccount.getAccountHolderName());
                    System.out.println("Balance: Rs. "
                            + newAccount.getBalance());

                    break;
                }

                case 3:
                    System.out.println("\n===== ALL BANK ACCOUNTS =====");

                    for (BankAccount account : accounts) {
                        System.out.println("Account Number: "
                                + account.getAccountNumber());
                        System.out.println("Account Holder: "
                                + account.getAccountHolderName());
                        System.out.println("Balance: Rs. "
                                + account.getBalance());
                        System.out.println("--------------------------");
                    }

                    break;

                case 4: {
                    System.out.println("\n===== MONEY TRANSFER =====");

                    System.out.print("Enter Sender Account Number: ");
                    String senderNumber = sc.nextLine().trim();

                    BankAccount sender =
                            bankService.findAccount(
                                    accounts, senderNumber);

                    if (sender == null) {
                        System.out.println("Sender Account Not Found!");
                        break;
                    }

                    boolean senderAuthenticated = false;

                    for (int attempt = 1; attempt <= 3; attempt++) {
                        System.out.print("Enter Sender PIN: ");
                        String enteredPin = sc.nextLine();

                        if (sender.verifyPin(enteredPin)) {
                            senderAuthenticated = true;
                            break;
                        } else {
                            System.out.println(
                                    "Incorrect PIN! Attempts remaining: "
                                    + (3 - attempt));
                        }
                    }

                    if (!senderAuthenticated) {
                        System.out.println("Transfer denied!");
                        break;
                    }

                    BankFileManager.saveAccounts(accounts);

                    System.out.print("Enter Receiver Account Number: ");
                    String receiverNumber = sc.nextLine().trim();

                    BankAccount receiver =
                            bankService.findAccount(
                                    accounts, receiverNumber);

                    if (receiver == null) {
                        System.out.println("Receiver Account Not Found!");
                        break;
                    }

                    System.out.print("Enter Transfer Amount: ");
                    BigDecimal transferAmount = readAmount(sc);

                    sender.transfer(receiver, transferAmount);

                    BankFileManager.saveAccounts(accounts);
                    BankFileManager.saveTransactions(accounts);

                    System.out.println("Sender Balance: Rs. "
                            + sender.getBalance());
                    System.out.println("Receiver Balance: Rs. "
                            + receiver.getBalance());

                    break;
                }

                case 5:
                    BankFileManager.saveAccounts(accounts);
                    BankFileManager.saveTransactions(accounts);

                    System.out.println(
                            "Thank you for using our Banking System!");
                    break;

                default:
                    System.out.println("Invalid Choice! Try Again.");
            }

        } while (choice != 5);

        sc.close();
    }

}
