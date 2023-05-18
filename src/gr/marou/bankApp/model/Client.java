package gr.marou.bankApp.model;

import java.math.BigDecimal;
import java.util.*;

/**
 * Represents a client with login credentials and funds in a banking system.
 */
public class Client {
    private final HashMap<String, String> loginCredentials;
    private BigDecimal funds;
    public Client() {
        loginCredentials= new HashMap<>();
        funds = BigDecimal.ZERO;

        // Load login credentials from a CSV file
        try (Scanner scanner =  new Scanner(Objects.requireNonNull(getClass().getResourceAsStream("credentials.csv")))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String user = parts[0];
                    String pasw = parts[1];

                    loginCredentials.put(user, pasw);
                }else{
                    System.out.println("Invalid line format: " + line);
                }
            }
            System.out.println("Login credentials loaded successfully from the CSV file.");
        } catch (Exception e) {
            System.out.println("Failed to read login credentials from the CSV file" + e.getMessage());
        }
    }

    /**
     * Returns the login credentials of the client.
     * @return the login credentials as a HashMap, where the key is the username and the value is the password
     */
    public  HashMap<String, String> getLoginCredentials(){
        return loginCredentials;
    }

    public BigDecimal getFunds() {
        return funds;
    }

    public void setFunds(BigDecimal funds) {
        this.funds = funds;
    }
}
