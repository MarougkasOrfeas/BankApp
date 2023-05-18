package gr.marou.bankApp.Service;

import gr.marou.bankApp.model.Client;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of the ClientService interface that provides banking operations and services for a client.
 */
public class ClientServiceImpl implements ClientService {

    private static final String CSV_FILE_PATH = "src/gr/marou/bankApp/model/credentials.csv";
    private final Client client;

    public ClientServiceImpl(Client client){
        this.client = client;
    }

    /**
     * Retrieves the current balance of the client's account.
     * @return the current balance
     */
    @Override
    public BigDecimal getBalance() {
        if(client.getFunds()==null){
            client.setFunds(BigDecimal.ZERO);
        }
        return client.getFunds();
    }

    /**
     * Deposits the specified amount of funds into the client's account.
     * @param depositAmount the amount of funds to deposit
     * @return the updated balance after the deposit
     */
    @Override
    public BigDecimal depositFunds(BigDecimal depositAmount) {
        BigDecimal funds = client.getFunds();
        if(funds == null){
            funds = BigDecimal.ZERO;
        }
        if (depositAmount == null) {
            depositAmount = BigDecimal.ZERO;
        }
        BigDecimal newAmount = funds.add(depositAmount);
        client.setFunds(newAmount);
        return newAmount;
    }

    /**
     * Withdraws the specified amount of funds from the client's account.
     * @param amount the amount of funds to withdraw
     * @return true if the withdrawal is successful, false otherwise
     */
    @Override
    public boolean withdrawFunds(BigDecimal amount) {
        BigDecimal funds = client.getFunds();

        if(funds == null){
            funds = BigDecimal.ZERO;
        }

        if (amount == null) {
            amount = BigDecimal.ZERO;
        }

        if(Objects.equals(funds, BigDecimal.ZERO)){
            return false;
        }

        if(amount.compareTo(funds)>0){
            return false;
        }else{
            BigDecimal newAmount = funds.subtract(amount);
            client.setFunds(newAmount);
        }
        return true;
    }

    /**
     * Checks if the provided username is valid and exists in the login credentials.
     * @param user the username to be verified
     * @return true if the username is valid and exists, false otherwise
     */
    @Override
    public boolean userLogin(String user) {
        return verifyUsername(user);
    }

    /**
     * Checks if the provided password is valid and matches the stored hashed password.
     * @param pasw the password to be verified
     * @return true if the password is valid and matches, false otherwise
     */
    @Override
    public boolean paswLogin(String pasw){
        return verifyPasw(pasw);
    }

    /**
     Creates a new account with the specified username and password.
     @param user the username for the new account
     @param pasw the password for the new account
     @return true if the account is created successfully, false if the username already exists
     */
    @Override
    public boolean createNewAcc(String user, String pasw) {
        boolean foundUser = verifyUsername(user);
        if(!foundUser){
            String userEncrypted = encrypt(user);
            String paswEncrypted = encrypt(pasw);
            client.getLoginCredentials().put(userEncrypted,paswEncrypted);

            // Append the new account record to the CSV file
            try {
                FileWriter csvWriter = new FileWriter(CSV_FILE_PATH, true);
                csvWriter.append(userEncrypted);
                csvWriter.append(",");
                csvWriter.append(paswEncrypted);
                csvWriter.append("\n");
                csvWriter.close();
            } catch (IOException e) {
                System.out.println("Failed to write to CSV file: " + e.getMessage());
            }
            return true;
        }
        System.out.println("This user already exists");
        return false;
    }

    /**
     * Encrypts the provided value using the SHA-256 hashing algorithm.
     * @param value the value to be encrypted
     * @return the encrypted value as a hexadecimal string
     * @throws RuntimeException if the SHA-256 algorithm is not available
     */
    @Override
    public String encrypt(String value){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    /**
     * Verifies if the entered username matches any of the stored hashed usernames.
     * @param enteredUsername the username to be verified
     * @return true if the username is found, false otherwise
     */
    private boolean verifyUsername(String enteredUsername) {
        String hashedUser  = encrypt(enteredUsername);
        for (Map.Entry<String, String> credentials : client.getLoginCredentials().entrySet()) {
            String username = credentials.getKey();
            if (username.equals(hashedUser)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies if the entered password matches any of the stored hashed passwords.
     * @param enteredPasw the password to be verified
     * @return true if the password is found, false otherwise
     */
    private boolean verifyPasw(String enteredPasw) {
        String hashedPasw  = encrypt(enteredPasw);
        for (Map.Entry<String, String> credentials : client.getLoginCredentials().entrySet()) {
            String pasw = credentials.getValue();
            if (pasw.equals(hashedPasw)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts a byte array to a hexadecimal string representation.
     * @param bytes the byte array to be converted
     * @return the hexadecimal string representation of the byte array
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

}
