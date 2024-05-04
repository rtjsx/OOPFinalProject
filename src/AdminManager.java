import java.util.Scanner;

public class AdminManager {
    private final Scanner scanner = new Scanner(System.in);

    public void handleAdmin() {
        System.out.println("Welcome Admin!");
        System.out.println("Please enter your credentials:");
        System.out.print("Username: ");
        String username = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        if (Authentication.verifyAdminCredentials(username, password)) {
            showAdminMenu();
        } else {
            System.out.println("Invalid credentials. Redirecting to main menu...");
        }
    }

    private void approveAccountCreation() {
        System.out.print("Enter username to approve: ");
        String usernameToApprove = scanner.next();
        Account account = FileHandler.loadAccountRequest(usernameToApprove);
        if (account != null) {
            FileHandler.saveExistingAccount(account);
            System.out.println("Account with username " + usernameToApprove + " has been approved!");
        } else {
            System.out.println("No account creation requests found for the provided username.");
        }
    }
    
    

    private void approveAccountDeletion() {
        System.out.print("Enter username to delete: ");
        String usernameToDelete = scanner.next();
        if (FileHandler.removeAccount(usernameToDelete)) {
            FileHandler.removeDeletionRequest(usernameToDelete);
            System.out.println("Account with username " + usernameToDelete + " deleted!");
        } else {
            System.out.println("No requests in inbox.");
        }
    }

    private void showAdminMenu() {
        boolean active = true;
        while (active) {
            System.out.println("Welcome back!");
            System.out.println("(1) View account requests for creation");
            System.out.println("(2) View account requests for deletion");
            System.out.println("(3) Display existing accounts");
            System.out.println("(4) Approve account creation requests");
            System.out.println("(5) Approve account deletion requests");
            System.out.println("(6) Exit and return to the main menu");
            System.out.print("Choose an option: ");

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
                    approveAccountCreation();
                    break;
                case 5:
                    approveAccountDeletion();
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
