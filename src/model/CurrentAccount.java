package model;

public class CurrentAccount extends Account{
    public CurrentAccount(String acc_no)
    {
        super(acc_no);
    }
    @Override
    public String display_account_type()
    {
        return "CurrentAccount";
    }
}
