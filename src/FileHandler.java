import java.io.*;
import java.util.*;
import java.time.LocalDateTime;

public class FileHandler {

    private static final String ACCOUNT_REQUESTS_FILE = "accountRequests.txt";
    private static final String EXISTING_ACCOUNTS_FILE = "existingAccounts.txt";
    private static final String DELETION_REQUESTS_FILE = "deletionRequests.txt";

    // Ensure files exist before any operation
    public static void ensureFilesExist() {
        try {
            createFileIfNotExists(ACCOUNT_REQUESTS_FILE);
            createFileIfNotExists(EXISTING_ACCOUNTS_FILE);
            createFileIfNotExists(DELETION_REQUESTS_FILE);
        } catch (IOException e) {
            System.out.println("Error creating required files: " + e.getMessage());
        }
    }

    private static void createFileIfNotExists(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    // Save new account request
    public static void saveAccountRequest(Account account) {
        if (isDuplicateAccountRequest(account.getUsername()) || isUsernameTaken(account.getUsername())) {
            System.out.println("Username already taken or account creation request already submitted! Pending approval...");
            return;
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNT_REQUESTS_FILE, true))) {
            writer.println(serializeAccountRequest(account));
        } catch (IOException e) {
            System.out.println("Error saving account request: " + e.getMessage());
        }
    }

  

    // Prevent duplicate usernames
    private static boolean isUsernameTaken(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(EXISTING_ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Account acc = deserializeAccount(line);
                if (acc != null && acc.getUsername().equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // Handle silently
        }
        return false;
    }

    // Check for duplicate account requests
    public static boolean isDuplicateAccountRequest(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNT_REQUESTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking account requests: " + e.getMessage());
        }
        return false;
    }

    // Method to serialize account request
    private static String serializeAccountRequest(Account account) {
        return String.format("%s,%s,%d", account.getUsername(), account.getName(), account.getAge());
    }
    
    // Display account requests
    public static void displayAccountRequests() {
        boolean requestsFound = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNT_REQUESTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                requestsFound = true;
            }
        } catch (IOException e) {
            // Ignore error silently
        }
        if (!requestsFound) {
            System.out.println("No requests in inbox.");
        }
    }

    public static boolean isPendingApproval(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("accountRequests.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking account requests: " + e.getMessage());
        }
        return false;
    }

    // Display deletion requests
    public static void displayDeletionRequests() {
        boolean requestsFound = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(DELETION_REQUESTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Deletion request: " + line);
                requestsFound = true;
            }
        } catch (IOException e) {
            // Ignore error silently
        }
        if (!requestsFound) {
            System.out.println("No requests in inbox.");
        }
    }

    // Display existing accounts
    public static void displayExistingAccounts() {
        boolean accountsFound = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(EXISTING_ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Account acc = deserializeAccount(line);
                System.out.println("Username: " + acc.getUsername());
                System.out.println("Name: " + acc.getName());
                System.out.println("Balance: $" + acc.getBalance());
                accountsFound = true;
                System.out.println("------");
            }
        } catch (IOException e) {
            // Ignore error silently
        }
        if (!accountsFound) {
            System.out.println("No accounts in inbox.");
        }
    }

    // Remove an existing account
    public static boolean removeAccount(String username) {
        List<Account> accounts = new ArrayList<>();
        boolean removed = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(EXISTING_ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Account acc = deserializeAccount(line);
                if (acc != null && !acc.getUsername().equals(username)) {
                    accounts.add(acc);
                } else {
                    removed = true;
                }
            }
        } catch (IOException e) {
            // Handle silently
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(EXISTING_ACCOUNTS_FILE))) {
            for (Account acc : accounts) {
                writer.println(serializeAccount(acc));
            }
        } catch (IOException e) {
            // Handle silently
        }

        return removed;
    }

    // Save account for deletion
    public static void saveDeletionRequest(String username) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DELETION_REQUESTS_FILE, true))) {
            writer.println(username);
        } catch (IOException e) {
            System.out.println("Error saving deletion request: " + e.getMessage());
        }
    }


    // Update account balance for withdrawal/deposit
    public static void updateAccountBalance(Account account) {
        List<Account> accounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(EXISTING_ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Account acc = deserializeAccount(line);
                if (acc != null && acc.getUsername().equals(account.getUsername())) {
                    accounts.add(account);
                } else {
                    accounts.add(acc);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading existing accounts: " + e.getMessage());
        }

        // Write back updated accounts to the file
        try (PrintWriter writer = new PrintWriter(new FileWriter(EXISTING_ACCOUNTS_FILE))) {
            for (Account acc : accounts) {
                writer.println(serializeAccount(acc));
            }
        } catch (IOException e) {
            System.out.println("Error saving updated accounts: " + e.getMessage());
        }
    }

    

    // Load account requests
    public static Account loadAccountRequest(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNT_REQUESTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Account account = deserializeAccount(line);
                if (account != null && account.getUsername().equals(username)) {
                    return account;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading account request: " + e.getMessage());
        }
        return null;
    }

    // Load approved User accounts
    public static Map<String, String> loadApprovedUserCredentials() {
        Map<String, String> userCredentials = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(EXISTING_ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    userCredentials.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading existing accounts: " + e.getMessage());
        }
        return userCredentials;
    }
    

    // Load existing account
    public static Account loadAccount(String username) {
        File file = new File(EXISTING_ACCOUNTS_FILE);
        if (!file.exists()) {
            // Silently handle the missing file
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Account account = deserializeAccount(line);
                if (account != null && account.getUsername().equals(username)) {
                    return account;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading account: " + e.getMessage());
        }
        return null;
    }
    
    // Helper method to serialize Account
    private static String serializeAccount(Account account) {
        StringBuilder transactions = new StringBuilder();
        for (Transaction t : account.getTransactionHistory()) {
            transactions.append(t.getType()).append(",").append(t.getAmount()).append(",").append(t.getDate().toString()).append(";");
        }
        return String.format("%s,%s,%s,%f,%d,%d,%s", account.getUsername(), account.getPassword(), account.getName(),
            account.getBalance(), account.getAge(), account.getCreditScore(), transactions.toString());
    }
    
    // Helper method to deserialize Account
    private static Account deserializeAccount(String data) {
        String[] parts = data.split(",", 7);
        if (parts.length < 7) {
            return null;
        }
        Account account = new Account(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]),
            Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
    
        String[] transactions = parts[6].split(";");
        for (String transaction : transactions) {
            String[] tParts = transaction.split(",");
            if (tParts.length == 3) {
                account.addTransaction(new Transaction(tParts[0], Double.parseDouble(tParts[1]), LocalDateTime.parse(tParts[2])));
            }
        }
        return account;
    }
    

    // Save an account to the existing accounts file
    public static void saveExistingAccount(Account account) {
        removeAccountRequest(account.getUsername());
        try (PrintWriter writer = new PrintWriter(new FileWriter(EXISTING_ACCOUNTS_FILE, true))) {
            writer.println(serializeAccount(account));
        } catch (IOException e) {
            System.out.println("Error saving existing account: " + e.getMessage());
        }
    }

    // Remove an account request
    public static void removeAccountRequest(String username) {
        List<Account> accounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNT_REQUESTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Account acc = deserializeAccount(line);
                if (acc != null && !acc.getUsername().equals(username)) {
                    accounts.add(acc);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading account requests: " + e.getMessage());
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNT_REQUESTS_FILE))) {
            for (Account acc : accounts) {
                writer.println(serializeAccountRequest(acc));
            }
        } catch (IOException e) {
            System.out.println("Error updating account requests: " + e.getMessage());
        }
    }
    

    // Remove a deletion request
    public static void removeDeletionRequest(String username) {
        List<String> usernames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DELETION_REQUESTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals(username)) {
                    usernames.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading deletion requests: " + e.getMessage());
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(DELETION_REQUESTS_FILE))) {
            for (String name : usernames) {
                writer.println(name);
            }
        } catch (IOException e) {
            System.out.println("Error saving updated deletion requests: " + e.getMessage());
        }
    }
     
}
