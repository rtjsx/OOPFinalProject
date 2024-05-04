import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Authentication {
    private static final Map<String, String> adminCredentials = new HashMap<>();

    static {
        adminCredentials.put("admin", "adminPass");
    }

    public static boolean verifyUserCredentials(String username, String password) {
        Map<String, String> userCredentials = FileHandler.loadApprovedUserCredentials();

        if (userCredentials.containsKey(username)) {
            return password.equals(userCredentials.get(username));
        } else {
            System.out.println("Invalid credentials or pending approval.");
        }
        return false;
    }

    
    private static Map<String, String> loadApprovedUserCredentials() {
        Map<String, String> userCredentials = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("existingAccounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length >= 2) {
                    userCredentials.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading existing accounts: " + e.getMessage());
        }
        return userCredentials;
    }

    public static boolean verifyAdminCredentials(String username, String password) {
        return password.equals(adminCredentials.get(username));
    }

    public static boolean isPendingApproval(String username) {
        return FileHandler.isPendingApproval(username);
    }
}
