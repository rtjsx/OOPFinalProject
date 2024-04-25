import java.util.Scanner;

public class AdminManager {
    private final Scanner scanner = new Scanner(System.in);

    public void handleAdmin() {
        System.out.println("Welcome Admin!");
        System.out.println("Please enter your credentials:");
        System.out.println("Username:");
        String username = scanner.next();
        System.out.println("Password:");
        String password = scanner.next();

        if (Authentication.verifyAdminCredentials(username, password)) {
            showAdminMenu();
        } else {
            System.out.println("Invalid credentials. Redirecting to main menu...");
        }
    }

    private void showAdminMenu() {
        boolean active = true;
        while (active) {
            System.out.println("Welcome back!");
            System.out.println("If you want to view account requests for creation, enter (1):");
            System.out.println("If you want to view account requests for deletion, enter (2):");
            System.out.println("If you want to display existing accounts, enter (3):");
            System.out.println("Enter (4) to exit and return to the main menu.");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    FileHandler.displayAccountRequests();
                    break;
                case 2:
                    FileHandler.displayDeletionRequests();
                    break;
                case 3:
                    FileHandler.displayExistingAccounts();
                    break;
                case 4:
                    active = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
