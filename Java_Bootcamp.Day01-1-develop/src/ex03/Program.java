public class Program {
    public static void main(String[] args) {
        User user1 = new User("Mike", 400);
        User user2 = new User("John", 12000);

        TransactionsLinkedList transactions = user1.getTransactionsList();

        Transaction trans = new Transaction(user1, user2, Transaction.Category.DEBIT, 20);
        transactions.addTransaction(trans);
        Transaction trans1 = new Transaction(user1, user2, Transaction.Category.DEBIT, 15);
        transactions.addTransaction(trans1);
        Transaction trans2 = new Transaction(user1, user2, Transaction.Category.DEBIT, 10);
        transactions.addTransaction(trans2);
        for(Object i : transactions.toArray()) {
            System.out.println(i);
        }

        System.out.println(user2.getName() + " have " + transactions.getCountTransactions() + " transactions");
        transactions.deleteTransactionById(trans1.getIdentifier());
        System.out.println(user2.getName() + " have " + transactions.getCountTransactions() + " transactions");
        transactions.deleteTransactionById(trans2.getIdentifier());
        System.out.println(user2.getName() + " have " + transactions.getCountTransactions() + " transactions");
        transactions.deleteTransactionById(trans.getIdentifier());
        System.out.println(user2.getName() + " have " + transactions.getCountTransactions() + " transactions");
    }
}
