package pl.bkwak.book.store.core;

import pl.bkwak.book.store.database.BookDB;
import pl.bkwak.book.store.database.UserDB;
import pl.bkwak.book.store.gui.GUI;
import pl.bkwak.book.store.model.User;

public class Core {
    private static final Core instance = new Core();
    final BookDB bookDB = BookDB.getInstance();
    final UserDB userDB = UserDB.getInstance();
    final Authenticator authenticator = Authenticator.getInstance();
    final GUI gui = GUI.getInstance();

    private Core() {
    }

    public static Core getInstance() {
        return instance;
    }

    public void start() {
        boolean isRunning = false;
        while (!isRunning) {
            switch (gui.showStartMenu()) {
                case "1":
                    isRunning = signIn(isRunning);
                    break;
                case "2":
                    gui.showSignUpResult(signUp());
                    break;
                case "3":
                    System.exit(0);
                    break;
            }
        }
        menu();
    }

    public void menu() {
        while (true) {
            switch (gui.showMenu()) {
                case "1":
                    gui.listBooks();
                    break;
                case "2":
                    gui.showOrderResult(gui.buy());
                    break;
                case "3":
                    authenticator.setLoggedUser(null);
                    start();
                    break;
                case "4":
                    System.exit(0);
                    break;
                case "5":
                    if (this.authenticator.getLoggedUser().getRole() == User.Role.ADMIN) {
                        gui.restock();
                    }
                    break;
                case "6":
                    if (this.authenticator.getLoggedUser().getRole() == User.Role.ADMIN) {
                        gui.listUsers();
                    }
                    break;
                case "7":
                    if (this.authenticator.getLoggedUser().getRole() == User.Role.ADMIN) {
                        gui.grantStatus();
                    }
                    break;
                default:
                    System.out.println("Wrong choice!!!");
                    break;
            }
        }
    }

    public boolean signIn(boolean isRunning) {
        int counter = 0;
        while (!isRunning && counter < 3) {
            this.authenticator.authenticate(this.gui.readLoginAndPassword());
            isRunning = authenticator.getLoggedUser() != null;
            if (!isRunning) {
                System.out.println("Not authorized !!!");
            }
            counter++;
        }
        return isRunning;
    }

    public boolean signUp() {
        User user = this.gui.setNewUser();
        if (this.userDB.addUser(user)) {
            user.setRole(User.Role.USER);
            return true;
        } else return false;
    }
}
