import java.util.*;
public class Transaction {
    private UUID Identifier;
    private User Recipient;
    private User Sender;

    enum Category {
        DEBIT, CREDIT
    }

    private Category TransferCategory;
    private Integer TransferAmount;

    public Transaction(User recipient, User sender, Category transferCategory, Integer transferAmount) {
        if ((transferAmount < 0 && transferCategory == Category.DEBIT)) {
            System.err.println("Error");
        } else if ((sender.getBalance() < transferAmount && transferCategory ==  Category.DEBIT) || (sender.getBalance() < -transferAmount && transferCategory ==  Category.CREDIT)) {
            System.err.println("Error");
        } else {
            Identifier = UUID.randomUUID();
            Recipient = recipient;
            Sender = sender;
            TransferCategory = transferCategory;
            TransferAmount = transferAmount;
            transactionHandler(recipient, sender, TransferCategory, TransferAmount);
        }
    }
    public static void transactionHandler(User recipient, User sender, Category category, Integer amount) {
        if (amount < 0)
            amount = -amount;
        recipient.setBalance(recipient.getBalance() + amount);
        sender.setBalance(sender.getBalance() - amount);
    }
    public UUID getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(UUID identifier) {
        Identifier = identifier;
    }

    public User getRecipient() {
        return Recipient;
    }

    public void setRecipient(User recipient) {
        Recipient = recipient;
    }

    public User getSender() {
        return Sender;
    }

    public void setSender(User sender) {
        Sender = sender;
    }

    public Integer getTransferAmount() {
        return TransferAmount;
    }

    public Category getTransferCategory() {
        return TransferCategory;
    }

    public void setTransferCategory(Category transferCategory) {
        TransferCategory = transferCategory;
    }

    public void setTransferAmount(Integer transferAmount) {
        TransferAmount = transferAmount;
    }

    @Override
    public String toString() {
        return Sender.getName() + " -> " + Recipient.getName() + ", -" + TransferAmount + ", OUTCOME, " + Identifier + "\n" + Recipient.getName() + " -> " + Sender.getName() + ", " + TransferAmount + ", INCOME, " + Identifier;
    }
}
