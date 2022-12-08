package pl.bkwak.book.store.database;

import pl.bkwak.book.store.model.User;

import java.util.LinkedList;

public class UserDB {

    private static final UserDB instance = new UserDB();
    private final LinkedList<User> users = new LinkedList<User>();

    private UserDB() {
        users.add(new User("admin", "b5aedd032493308c08936ee7de0aa22c", User.Role.ADMIN));
        users.add(new User("adam", "db2e2307bb005c0b1c2c947774cfd63d", User.Role.USER));
        users.add(new User("ewa", "a063853a88f50fefd0a019380eea9b0c", User.Role.USER));
    }

    public static UserDB getInstance() {
        return instance;
    }

    public LinkedList<User> getUsers() {
        return users;
    }

    public User findByLogin(String login) {
        for (User user : this.users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public boolean addUser(User newUser) {
        for (User user : users) {
            if (user.getLogin().equals(newUser.getLogin())) {
                return false;
            }
        }
        users.add(newUser);
        return true;
    }

    public boolean grantAdminStatus(String login) {
        for (User user : this.users) {
            if (user.getLogin().equals(login)) {
                user.setRole(User.Role.ADMIN);
                return true;
            }
        }
        return false;
    }
}
