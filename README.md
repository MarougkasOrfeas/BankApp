# Bank Application

The Bank Application is a Java program that simulates a simple ATM machine. It allows users to perform various banking operations such as viewing balance, depositing funds, and withdrawing funds.

## Usage

Upon launching the application, you will be presented with a login menu where you can either log in or register a new account. Follow the on-screen prompts to navigate through the application and perform the desired actions.

### Login Menu

In the login menu, you have the following options:

1. Login: Allows existing users to log in using their card ID and password.
2. Register: Allows new users to create a new account by providing a card ID and password.
3. Exit: Exits the application.

### Actions Menu

After logging in, you will see the actions menu which provides the following options:

1. View Balance: Displays the current balance of the client's account.
2. Withdraw Funds: Allows the user to withdraw funds from the account.
3. Deposit Funds: Allows the user to deposit funds into the account.
4. Exit: Exits the application.

## Code Structure

The Bank Application is divided into several classes to handle different functionalities:

- `BankApp.java`: The main class that represents the Bank Application. It handles user input and navigation through the application.
- `ClientService.java`: The interface that defines the operations and services available for a client.
- `ClientServiceImpl.java`: The implementation of the `ClientService` interface that provides banking operations and services for a client.
- `Client.java`: The class that represents a client with login credentials and funds in the banking system.

## CSV File

The application uses a CSV file, `credentials.csv`, to store the login credentials of the clients. The file contains two columns: the encrypted card ID and the encrypted password. The file is loaded upon the application startup, and new account records are appended to it when a new account is created.
