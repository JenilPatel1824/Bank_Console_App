package model;

public class SavingAccount extends Account {

    public SavingAccount(String acc_no)
    {
        super(acc_no);
    }
    public SavingAccount(String acc_no, double balance)
    {
        super(acc_no,balance);
    }
    @Override
    public String display_account_type()
    {
        return "SavingAccount";
    }

}
