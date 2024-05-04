import java.util.Scanner;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserManager userManager = new UserManager();
    private static final AdminManager adminManager = new AdminManager();

    public static void main(String[] args) {
        FileHandler.ensureFilesExist();

        boolean running = true;
        while (running) {
            System.out.println("——————————");
            System.out.println("Welcome to the best banking app!");
            System.out.println("(1) New user registration");
            System.out.println("(2) Existing user login");
            System.out.println("(3) Admin access");
            System.out.println("(4) Exit program");
            System.out.println("——————————");
            System.out.print("Choose an option: ");

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
                    System.out.println("Exiting program...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
