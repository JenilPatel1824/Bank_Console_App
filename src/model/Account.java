package model;

import java.util.concurrent.atomic.AtomicReference;


public abstract class Account {

    private String holder_name;
    private AtomicReference<Double> balance;

    public Account(String holder_name)
    {
        this.holder_name=holder_name;
        this.balance=new AtomicReference<Double>(0.0);
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

    public String getHolder_name() {
        return holder_name;
    }

    public void setHolder_name(String holder_name) {
        this.holder_name = holder_name;
    }

    public double getBalance()
    {
        return balance.get();
    }

}
