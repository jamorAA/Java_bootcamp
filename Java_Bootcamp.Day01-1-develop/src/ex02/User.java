public class User {
    private Integer Identifier;
    private String Name;
    private Integer Balance;

    public User(String name, Integer balance) {
        if (balance < 0) {
            System.err.println("Error");
        } else {
            Identifier = UserIdsGenerator.getInstance().idGenerator();
            Name = name;
            Balance = balance;
        }
    }

    public Integer getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(Integer identifier) {
        Identifier = identifier;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getBalance() {
        return Balance;
    }

    public void setBalance(Integer balance) {
        if (balance < 0) {
            System.err.println("Error");
        } else {
            Balance = balance;
        }
    }
    @Override
    public String toString() {
        return "id: " + Identifier + ", name: " + Name + ", balance: " + Balance + ";";
    }
}
