package model;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private String type;
    private BigDecimal amount;
    private LocalDateTime dateTime;

    // Constructor for new transactions
    public Transaction(String type, BigDecimal amount) {

        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
    }

    // Constructor for previously saved transactions
    public Transaction(String type, BigDecimal amount,
                       LocalDateTime dateTime) {

        this.type = type;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void display() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        System.out.println(
                "Type: " + type
                + " | Amount: Rs. " + amount
                + " | Date: " + dateTime.format(formatter)
        );
    }
}
