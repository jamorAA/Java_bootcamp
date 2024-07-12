import java.util.Objects;

class UserNotFoundException extends RuntimeException {}

public class UsersArrayList implements  UsersList {
    private User[] users;
    private Integer numberOfUsers;
    private final Integer size;

    public UsersArrayList() {
        size = 10;
        users = new User[size];
        numberOfUsers = 0;
    }
    @Override
    public void addUser(User user) {
        if(numberOfUsers >= size) {
            User[] tmp = new User[size * 2];
            System.arraycopy(users, 0, tmp, 0, users.length);
            users = tmp;
        }
        users[numberOfUsers] = user;
        numberOfUsers++;
    }

    @Override
    public User getUserById(Integer id) {
        for (User user : users)
            if (Objects.equals(user.getIdentifier(), id))
                return user;
        throw new UserNotFoundException();
    }

    @Override
    public User getUserByIndex(Integer index) {
        if(index <= users.length && index >= 0)
            return users[index];
        throw new UserNotFoundException();
    }

    @Override
    public Integer getCountOfUsers() {
        return numberOfUsers;
    }
}
