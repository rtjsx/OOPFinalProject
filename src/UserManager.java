import java.util.Scanner;

public class UserManager {
    private final Scanner scanner = new Scanner(System.in);

    public void handleNewUser() {
        System.out.println("Please fill out the following form:");
        System.out.println("Username:");
        String username = scanner.next();
        System.out.println("Password:");
        String password = scanner.next();
        System.out.println("Name:");
        String name = scanner.next();
        System.out.println("Initial deposit (minimum $100):");
        double initialDeposit = scanner.nextDouble();
        System.out.println("Age:");
        int age = scanner.nextInt();
        System.out.println("Credit score:");
        int creditScore = scanner.nextInt();

        Account newAccount = new Account(username, password, name, initialDeposit, age, creditScore);
        FileHandler.saveAccountRequest(newAccount);
        System.out.println("Form submitted! Redirecting to main menu...");
    }

    public void handleExistingUser() {
        System.out.println("Welcome back!");
        System.out.println("Please enter your credentials:");
        System.out.println("Username:");
        String username = scanner.next();
        System.out.println("Password:");
        String password = scanner.next();

        if (Authentication.verifyUserCredentials(username, password)) {
            showUserMenu(username);
        } else {
            System.out.println("Oh, oh - it looks like such credentials do not exist :(");
            System.out.println("Redirecting you back to the main menu…");
        }
    }

    private void showUserMenu(String username) {
        boolean active = true;
        while (active) {
            System.out.println("Welcome back!");
            System.out.println("If you want to withdraw enter (1):");
            System.out.println("If you want to deposit enter (2):");
            System.out.println("If you want to view your deposit/withdrawal history enter (3):");
            System.out.println("If you want your account to be deleted enter (4):");
            System.out.println("Enter (5) to exit and return to the main menu.");

            int choice = scanner.nextInt();
            Account account = FileHandler.loadAccount(username);

            switch (choice) {
                case 1:
                    System.out.println("Enter amount to withdraw:");
                    double withdrawalAmount = scanner.nextDouble();
                    account.withdraw(withdrawalAmount);
                    break;
                case 2:
                    System.out.println("Enter amount to deposit:");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 3:
                    account.showHistory();
                    break;
                case 4:
                    if (account.requestDeletion()) {
                        System.out.println("Account deletion requested.");
                    }
                    active = false;
                    break;
                case 5:
                    active = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

}
