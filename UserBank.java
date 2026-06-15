package CodeSoft;

public class UserBank {

    private double balance = 1000;

    public void addMoney(double amount) {
        balance += amount;
    }

    public boolean takeMoney(double amount) {

        if (amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    public double getBalance() {
        return balance;
    }
}

