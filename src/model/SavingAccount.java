package model;

public class SavingAccount extends Account {

    public SavingAccount(String holder_name)
    {
        super(holder_name);
    }
    @Override
    public String display_account_type()
    {
        return "SavingAccount";
    }

}
