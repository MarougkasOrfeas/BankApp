package gr.marou.bankApp.Service;

import java.math.BigDecimal;

/**
 * Represents the operations and services available for a client in a banking system.
 */
public interface ClientService {

    BigDecimal depositFunds(BigDecimal depositAmount);
    boolean withdrawFunds(BigDecimal amount);
    boolean userLogin(String value);
    boolean paswLogin(String pasw);
    boolean createNewAcc(String user, String pasw);
    String encrypt(String value) throws Exception;
    BigDecimal getBalance();
}
