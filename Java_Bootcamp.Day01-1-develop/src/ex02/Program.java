public class Program {
    public static void main(String[] args) {
        User user1 = new User("Mike", 400);
        User user2 = new User("John", 12000);
        UsersList list = new UsersArrayList();
        list.addUser(user1);
        list.addUser(user2);
        System.out.println(list.getUserByIndex(0));
        System.out.println(list.getUserByIndex(1));
        System.out.println("count = " + list.getCountOfUsers());

        User user3 = new User("LeBron", 20);
        User user4 = new User("Jordan", 4000000);
        list.addUser(user3);
        list.addUser(user4);
        System.out.println(list.getUserById(3));
        System.out.println(list.getUserById(4));
        System.out.println("count = " + list.getCountOfUsers());
    }
}
