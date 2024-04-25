import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        this.transactionHistory.add(new Transaction("deposit", initialDeposit)); // Initial deposit as a transaction
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("deposit", amount));
        System.out.println("$" + amount + " deposited successfully.");
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Withdrawal amount exceeds balance. Transaction failed.");
        } else {
            balance -= amount;
            transactionHistory.add(new Transaction("withdrawal", amount));
            System.out.println("$" + amount + " withdrawn successfully.");
        }
    }

    public void showHistory() {
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
        System.out.println("Press any key to return to the menu.");
        new Scanner(System.in).nextLine();
    }

    public boolean requestDeletion() {
        System.out.println("Are you sure? Enter (1) to confirm, enter (2) to return to menu.");
        int choice = new Scanner(System.in).nextInt();
        if (choice == 1) {
            FileHandler.saveDeletionRequest(this.username);
            return true;
        }
        return false;
    }
}
