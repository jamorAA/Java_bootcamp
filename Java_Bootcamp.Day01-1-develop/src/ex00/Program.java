public class Program {
    public static void main(String[] args) {
        User user1 = new User(1, "Mike", 400);
        User user2 = new User(2, "John", 12000);
        System.out.println(user1);
        System.out.println(user2);
        Transaction transaction1 = new Transaction(user1, user2, Transaction.Category.CREDIT, 500);
        System.out.println(transaction1);
        System.out.println(user1);
        System.out.println(user2);
    }
}
