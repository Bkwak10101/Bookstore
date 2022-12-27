package pl.bkwak.book.store.core;

import org.apache.commons.codec.digest.DigestUtils;
import pl.bkwak.book.store.database.UserDB;
import pl.bkwak.book.store.model.User;

public class Authenticator {
    public static final String seed = "JH9NcX0&n5*CEjV^u1ZNfdI816gLBaIOWK&!bAqt";
    private static final Authenticator instance = new Authenticator();
    private User loggedUser = null;

    public static Authenticator getInstance() {
        return instance;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void authenticate(User user) {
        UserDB userDB = UserDB.getInstance();
        User userFromDB = userDB.findByLogin(user.getLogin());
        if (userFromDB != null && userFromDB.getPassword().equals(
                DigestUtils.md5Hex(user.getPassword() + seed))) {
            loggedUser = userFromDB;
        }
    }
}
