package models;

import orm.Constraints;
import orm.OrmColumn;
import orm.OrmColumnId;
import orm.OrmEntity;

@OrmEntity(table = "users")
public class User {
    @OrmColumnId(constraints = @Constraints(primaryKey = true, allowNull = false, unique = true))
    private Integer user_id;
    @OrmColumn(name = "FirstName", length = 50)
    private String firstName;
    @OrmColumn(name = "LastName", length = 50)
    private String lastName;
    @OrmColumn(name = "Age")
    private Integer age;

    public User(Integer user_id, String firstName, String lastName, Integer age) {
        this.user_id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    public User() {
    }
    public Integer getUserId() {
        return user_id;
    }
    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return "User: {" + "user_id=" + user_id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", age=" + age + '}';
    }
}
