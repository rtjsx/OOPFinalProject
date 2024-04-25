import java.util.Scanner;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserManager userManager = new UserManager();
    private static final AdminManager adminManager = new AdminManager();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("——————————");
            System.out.println("Welcome to the best banking app!");
            System.out.println("If you are a new user enter (1):");
            System.out.println("If you are an existing user enter (2):");
            System.out.println("If you are an admin enter (3):");
            System.out.println("To end the program enter (4).");
            System.out.println("——————————");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    userManager.handleNewUser();
                    break;
                case 2:
                    userManager.handleExistingUser();
                    break;
                case 3:
                    adminManager.handleAdmin();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
