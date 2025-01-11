package model;

import java.util.concurrent.atomic.AtomicReference;


public abstract class Account {

    private String acc_no;
    //private User user;
    private AtomicReference<Double> balance;

    public Account(String acc_no)
    {
        this.acc_no=acc_no;
        //this.user=user;
        this.balance=new AtomicReference<Double>(0.0);
    }
    public Account(String acc_no,double balance)
    {
        this.acc_no=acc_no;
        if(balance>=0) {
            this.balance = new AtomicReference<>(balance);
        }
        else {
            throw new IllegalArgumentException("Please enter valid initial amount: ");
        }
    }

    public abstract String display_account_type();

    public double deposite(double amount)
    {
        if(amount<=0)
        {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        return balance.updateAndGet(current_balance->current_balance+amount);
    }

    public double withdraw(double amount)
    {
        if(amount<=0)
        {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if(amount>balance.get())
        {
            throw new IllegalArgumentException("Insufficient Balance!!");
        }
        return balance.updateAndGet(current_balance->current_balance-amount);
    }



    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public double getBalance()
    {
        return balance.get();
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

}
