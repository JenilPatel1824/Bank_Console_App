import DAO.Database;
import model.Account;
import model.SavingAccount;
import model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

        Database db = new Database();
        ConcurrentHashMap<String, ConcurrentHashMap<String, AtomicReference<Double>>> accounts = db.getAccounts();

        System.out.println("Application Started... ");
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter User ID (or type 'exit' to quit): ");
            String userId = sc.nextLine();
            if ("exit".equalsIgnoreCase(userId)) {
                System.out.println("Exiting...");
                break;
            }
            // Check if the user exists in the system
            if (!accounts.containsKey(userId)) {
                System.out.println("User ID not found. Try again.");
                continue;
            }
            // Prompt user to select account if multiple accounts exist for a user
            String accountNo;
            ConcurrentHashMap<String, AtomicReference<Double>> userAccounts = accounts.get(userId);
            if (userAccounts.size() >= 1) {
                System.out.println("Please select an account number:");
                userAccounts.forEach((accNo, balance) -> System.out.println(accNo));
                accountNo = sc.nextLine();

                // Check if the account exists
                if (!userAccounts.containsKey(accountNo)) {
                    System.out.println("Invalid account number. Try again.");
                    continue;
                }
            } else {
                System.out.println("No Accounts found!");
                continue;
            }

            System.out.println("Select action: ");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");

            String action = sc.nextLine();
//            System.out.println("Enter Account Number: ");
//            String accountNo = sc.nextLine();

            db.handleAction(action, userId, accountNo);
        }
    }
}