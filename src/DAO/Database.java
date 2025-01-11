package DAO;

import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Database {
    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, AtomicReference<Double>>> accounts= new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, ConcurrentHashMap<String,AtomicReference<Double>>> getAccounts() {
        return accounts;
    }
    public Database()
    {
        ConcurrentHashMap<String,AtomicReference<Double>> acc1=new ConcurrentHashMap<>();
        acc1.put("a1",new AtomicReference<Double>(100.0));
        accounts.putIfAbsent("u1",acc1);

        ConcurrentHashMap<String,AtomicReference<Double>> acc2=new ConcurrentHashMap<>();
        acc2.put("a2",new AtomicReference<Double>(100.0));
        accounts.putIfAbsent("u2",acc2);

        ConcurrentHashMap<String,AtomicReference<Double>> acc3=new ConcurrentHashMap<>();
        acc3.put("a3",new AtomicReference<Double>(100.0));
        accounts.putIfAbsent("u3",acc3);

        ConcurrentHashMap<String,AtomicReference<Double>> acc4=new ConcurrentHashMap<>();
        acc4.put("a4",new AtomicReference<Double>(100.0));
        accounts.putIfAbsent("u4",acc4);
    }


        // Check Balance
        public double checkBalance(String userId, String accountNo) {
                Double balance = accounts.getOrDefault(userId, new ConcurrentHashMap<>())
                        .getOrDefault(accountNo, new AtomicReference<>(null))
                        .get();
                if (balance != null) {
                    System.out.println("Balance: " + balance);
                } else {
                    System.out.println("Account not found or invalid.");

            }
                return balance==null?0:balance;
        }

        // Deposit
        public double deposit(String userId, String accountNo,double depositAmount) {
            Scanner sc = new Scanner(System.in);
            AtomicReference<Double> updatedBalance = new AtomicReference<>(-1.0);

            accounts.computeIfPresent(userId, (k, innerMap) -> {
                AtomicReference<Double> bal = innerMap.get(accountNo);
                if (bal != null) {
                    double newBalance = bal.updateAndGet(b -> b + depositAmount);
                    updatedBalance.set(newBalance); // Update the reference to the new balance
                    return innerMap;
                }
                return null;
            });

            if (updatedBalance.get() == -1.0) {
                System.out.println("Account not found or invalid.");
            } else {
                System.out.println("Deposit successful! New Balance: " + updatedBalance.get());
            }

            return updatedBalance.get(); // Return the updated balance (or -1 if the account is invalid)
        }

        // Withdraw
        public double withdraw(String userId, String accountNo,double withdrawAmount) {
            final double finalWithdrawAmount = withdrawAmount;

            // Perform withdrawal operation
            AtomicReference<Double> updatedBalance = new AtomicReference<>(-1.0); // Default to -1 for failure

            boolean accountExists = accounts.computeIfPresent(userId, (k, innerMap) -> {
                AtomicReference<Double> bal = innerMap.get(accountNo);
                if (bal != null) {
                    double currentBalance = bal.get();
                    if (currentBalance >= withdrawAmount) {
                        double newBalance = bal.updateAndGet(b -> b - withdrawAmount);
                        updatedBalance.set(newBalance); // Set updated balance
                        System.out.println("Withdrew " + withdrawAmount + " from account " + accountNo);
                    } else {
                        System.out.println("Insufficient balance in account " + accountNo);
                    }
                    return innerMap;
                }
                return null;
            }) != null;

            if (!accountExists) {
                System.out.println("Account number " + accountNo + " not found for user " + userId);
            }

            return updatedBalance.get(); // Return updated balance or -1 for failure
        }

    // Action Handling
        public void handleAction(String action, String userId, String accountNo) {
            Scanner sc = new Scanner(System.in);

            switch (action) {
                case "1":
                    checkBalance(userId, accountNo);
                    break;
                case "2":
                    System.out.println("Enter Deposit Amount: ");
                    double depositAmount;
                    while (true) {
                        try {
                            depositAmount = Double.parseDouble(sc.nextLine());
                            if (depositAmount <= 0) {
                                System.out.println("Please enter an amount greater than 0");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input! Please enter a valid number.");
                        }
                    }
                    deposit(userId, accountNo,depositAmount);
                    break;
                case "3":
                    System.out.println("Enter Withdrawal Amount: ");
                    double withdrawAmount;
                    while (true) {
                        try {
                            withdrawAmount = Double.parseDouble(sc.nextLine());
                            if (withdrawAmount <= 0) {
                                System.out.println("Amount must be positive! Please enter a valid number.");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input! Please enter a valid number.");
                        }
                    }
                    withdraw(userId, accountNo,withdrawAmount);
                    break;
                default:
                    System.out.println("Invalid action. Try again.");
            }
        }
}

