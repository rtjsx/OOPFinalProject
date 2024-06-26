import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String type; // "deposit" or "withdrawal"
    private final double amount;
    private final LocalDateTime date;

    public Transaction(String type, double amount) {
        this(type, amount, LocalDateTime.now());
    }
    
    // New constructor to accept a date parameter
    public Transaction(String type, double amount, LocalDateTime date) {
        this.type = type;
        this.amount = amount;
        this.date = date; // Set the provided date
    }

    public String getType() {
        return type;
    }


    public double getAmount() {
        return amount;
    }


    public LocalDateTime getDate() {
        return date;
    }

    // Format and display transaction details
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("Type: %s, Amount: $%.2f, Date: %s", type, amount, date.format(formatter));
    }
}
