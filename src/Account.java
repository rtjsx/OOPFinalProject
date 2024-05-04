import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;
import java.time.LocalDateTime;

public class Account {
    private String username;
    private String password;
    private String name;
    private double balance;
    private int age;
    private int creditScore;
    private List<Transaction> transactionHistory;

    public Account(String username, String password, String name, double initialDeposit, int age, int creditScore) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.balance = initialDeposit;
        this.age = age;
        this.creditScore = creditScore;
        this.transactionHistory = new ArrayList<>();
        this.transactionHistory.add(new Transaction("deposit", initialDeposit, LocalDateTime.now())); // Initial deposit as a transaction
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds. Transaction failed.");
        } else {
            balance -= amount;
            transactionHistory.add(new Transaction("withdrawal", amount, LocalDateTime.now()));
            FileHandler.updateAccountBalance(this); // Persist balance
            System.out.println("$" + amount + " withdrawn successfully.");
        }
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("deposit", amount, LocalDateTime.now()));
        FileHandler.updateAccountBalance(this); // Persist balance
        System.out.println("$" + amount + " deposited successfully.");
    }

    public void displayBalance() {
        System.out.printf("Current balance: $%.2f%n", balance);
    }

    public void showHistory() {
        System.out.printf("Current balance: $%.2f%n", balance);

        List<Transaction> sortedTransactions = new ArrayList<>(transactionHistory);
        Collections.reverse(sortedTransactions); // Reverse to get most recent first

        for (Transaction transaction : sortedTransactions) {
            System.out.println(transaction);
        }

        System.out.println("Press any key to return to the menu.");
        new Scanner(System.in).nextLine();
    }

    public boolean requestDeletion() {
        System.out.println("Are you sure? Enter (1) to confirm, enter (2) to return to menu.");
        int choice = new Scanner(System.in).nextInt();
        if (choice == 1) {
            FileHandler.saveDeletionRequest(this.username); // Add to deletion requests
            return true;
        }
        return false;
    }

    public void addTransaction(Transaction transaction) {
        this.transactionHistory.add(transaction);
    }

    // Getters & Setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public int getAge() {
        return age;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}
