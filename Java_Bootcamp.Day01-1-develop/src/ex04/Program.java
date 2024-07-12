public class Program {
    public static void main(String[] args) {
        User user1 = new User("Mike", 400);
        User user2 = new User("John", 12000);

        TransactionsService transactionsService = new TransactionsService();
        transactionsService.addUser(user1);
        transactionsService.addUser(user2);
        transactionsService.executeTransaction(user1.getIdentifier(), user2.getIdentifier(), 2000);
        System.out.println(user1);
        System.out.println(user2);

        for (Transaction i : user1.getTransactionsList().toArray())
            System.out.println(i);
        for (Transaction i : user2.getTransactionsList().toArray())
            System.out.println(i);

        System.out.println(transactionsService.getUserList().getCountOfUsers());
        transactionsService.removeTransaction(user1.getTransactionsList().toArray()[0].getIdentifier(), user1.getIdentifier());
        for (Transaction transaction : transactionsService.getUnpairedTransactions())
            System.out.println(transaction);
    }
}
