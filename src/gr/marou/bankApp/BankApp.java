package gr.marou.bankApp;

import gr.marou.bankApp.Service.ClientService;
import gr.marou.bankApp.Service.ClientServiceImpl;
import gr.marou.bankApp.model.Client;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * The main class that represents the Bank Application.
 */
public class BankApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClientService clientService = new ClientServiceImpl(new Client());

    public static void main(String[] args) {
        startApplication();
        loginMenu();
    }

    /**
     * Displays the actions menu and handles user input for various actions.
     */
    private static void actionsMenu(){
        while(true){
            printActionsMenu();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> showBalance();
                case 2 -> withdrawFunds();
                case 3 -> depositFunds();
                case 4 -> System.exit(0);
                default -> System.out.println("Wrong choice");
            }
        }
    }

    /**
     * Displays the login menu and handles user input for login or registration.
     */
    private static void loginMenu(){
        while(true){
            printLoginMenu();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> userLogin();
                case 2 -> createNewAcc();
                case 3 -> System.exit(0);
                default -> System.out.println("Invalid input");
            }
        }
    }

    /**
     * Displays a welcome message at the start of the application.
     */
    private static void startApplication(){
        System.out.println("Application launched...");
        System.out.println("Welcome to my ATM machine!");
        System.out.println("##########################");
    }

    /**
     * Prints the actions menu options.
     */
    private static void printActionsMenu(){
        System.out.println("Select one of the actions bellow:");
        System.out.println("---------------------------------");
        System.out.println("1 - View Balance");
        System.out.println("2 - Withdraw Funds");
        System.out.println("3 - Deposit Funds");
        System.out.println("4 - Exit");

    }

    /**
     * Prints the login menu options.
     */
    private static void printLoginMenu(){
        System.out.println("Select one of the actions bellow: ");
        System.out.println("----------------------------------");
        System.out.println("1 - Login");
        System.out.println("2 - Register");
        System.out.println("3 - Exit");
    }

    /**
     * Deposits funds into the client's account.
     */
    private static void depositFunds() {
        System.out.println("Type here the amount you want to deposit:\t");
        String input = scanner.next();
        if (input.isEmpty()) {
            System.out.println("Invalid amount: cannot be empty");
            return;
        }
        BigDecimal depositAmount;
        try{
            depositAmount = new BigDecimal(input);
        }catch (NumberFormatException e){
            System.out.println("Cant deposit this!" + input);
            return;
        }
        BigDecimal funds = clientService.depositFunds(depositAmount);
        System.out.println("New Balance is: " + funds);
    }

    /**
     * Withdraws funds from the client's account.
     */
    private static void withdrawFunds() {
        System.out.println("Enter an amount to withdraw:\t");
        String input = scanner.next();
        if (input.isEmpty()) {
            System.out.println("Invalid amount: cannot be empty");
            return;
        }
        BigDecimal amount = null;
        try{
            amount = new BigDecimal(input);
        }catch (NumberFormatException e){
            System.out.println("Invalid amount: " + input);
        }
        boolean success = clientService.withdrawFunds(amount);
        if(success){
            System.out.println("Withdrawal successfully!");
        }else{
            System.out.println("Insufficient Funds!");
        }
    }

    /**
     * Handles the user login process.
     */
    private static void userLogin() {
        int userAttempts = 5;
        int paswAttempts = 3;
        while(true) {
            System.out.println("Enter your card-ID:\t");
            String ID = scanner.next();
            boolean successID = clientService.userLogin(ID);
            if (successID) {
                System.out.println("Enter your passward:\t");
                String pasw = scanner.next();
                boolean successPasw = clientService.paswLogin(pasw);
                if (successPasw) {
                    System.out.println("Welcome user ");
                    actionsMenu();
                    break;
                } else {
                    paswAttempts--;
                    if (paswAttempts <= 0) {
                        break;
                    }
                }
            } else {
                System.out.println("The card-ID you have entered, doesnt exist!");
                userAttempts--;
                if (userAttempts <= 0) {
                    break;
                }
            }
        }
    }

    /**
     * Creates a new account for the user.
     */
    private static void createNewAcc() {

        while(true){
            String user = checkUser();
            System.out.println("Enter your desired pasward:\t");
            String pasw = scanner.next();
            System.out.println("Confirm pasward:\t");
            String confPass = scanner.next();
            if(!confPass.equals(pasw)){
                System.out.println("Paswards dont match!");
            }else{
                boolean success = clientService.createNewAcc(user,pasw);
                if(success){
                    System.out.println("Account created!");
                }
                break;
            }
        }
    }

    /**
     * Displays the user's account balance.
     */
    private static void showBalance() {
        System.out.println("Your balance is: " + clientService.getBalance().toString());
    }

    /**
     * Prompts the user to enter a card ID and validates its length.
     * @return the entered card ID
     */
    private static String checkUser(){
        String user;
        while(true){
            System.out.println("Enter your card-ID:\t");
            user = scanner.next();
            if(user.length()<6){
                System.out.println("Card-ID needs to be atleast 6 digits");
            }else{
                break;
            }
        }
        return user;
    }
}