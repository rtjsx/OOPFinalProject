import java.util.Scanner;

public class UserManager {
    private final Scanner scanner = new Scanner(System.in);

    public void handleNewUser() {
        FileHandler.ensureFilesExist();
        System.out.println("Please fill out the following form:");
        System.out.print("Username: ");
        String username = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();
        System.out.print("Name: ");
        String name = scanner.next();
    
        double initialDeposit = 0;
        boolean validDeposit = false;
        while (!validDeposit) {
            System.out.print("Initial deposit (minimum $100): ");
            if (scanner.hasNextDouble()) {
                initialDeposit = scanner.nextDouble();
                if (initialDeposit >= 100) {
                    validDeposit = true;
                } else {
                    System.out.println("Invalid amount. Minimum deposit is $100.");
                    scanner.nextLine(); // Consume newline and prompt for input again
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.next(); // Clear invalid input
                scanner.nextLine(); // Consume newline
            }
        }
    
        int age = 0;
        boolean validAge = false;
        while (!validAge) {
            System.out.print("Age: ");
            if (scanner.hasNextInt()) {
                age = scanner.nextInt();
                validAge = true;
            } else {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.next(); // Clear invalid input
                scanner.nextLine(); // Consume newline
            }
        }
    
        System.out.print("Credit score: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a numeric value.");
            return; // Exit the method if input is not numeric
        }
        int creditScore = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String hashedPassword = PasswordUtils.hashPassword(password);  // Hash the password

        Account newAccount = new Account(username, hashedPassword, name, initialDeposit, age, creditScore);
        FileHandler.saveAccountRequest(newAccount);
        System.out.println("Form submitted! Redirecting to main menu...");
    }
    
    

    public void handleExistingUser() {
        System.out.println("Welcome back!");
        System.out.print("Please enter your credentials:\nUsername: ");
        String username = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        FileHandler.ensureFilesExist();
        if (Authentication.verifyUserCredentials(username, password)) {
            showUserMenu(username);
        } else {
            System.out.println("Invalid credentials or pending approval.");
        }
    }

    private void showUserMenu(String username) {
        boolean active = true;
        while (active) {
            System.out.println("Welcome back!");
            System.out.println("(1) Withdraw");
            System.out.println("(2) Deposit");
            System.out.println("(3) View transaction history");
            System.out.println("(4) Display balance");
            System.out.println("(5) Request account deletion");
            System.out.println("(6) Exit and return to main menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            Account account = FileHandler.loadAccount(username);

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawalAmount = scanner.nextDouble();
                    account.withdraw(withdrawalAmount);
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 3:
                    account.showHistory();
                    break;
                case 4:
                    account.displayBalance();
                    break;
                case 5:
                    if (account.requestDeletion()) {
                        System.out.println("Account deletion requested.");
                        active = false; // End session only if deletion confirmed
                    }

                    break;
                case 6:
                    active = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

}
