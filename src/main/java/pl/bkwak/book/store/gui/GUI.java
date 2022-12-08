package pl.bkwak.book.store.gui;

import org.apache.commons.codec.digest.DigestUtils;
import pl.bkwak.book.store.core.Authenticator;
import pl.bkwak.book.store.database.BookDB;
import pl.bkwak.book.store.database.UserDB;
import pl.bkwak.book.store.model.Book;
import pl.bkwak.book.store.model.User;

import java.util.Scanner;

public class GUI {
    private static final GUI instance = new GUI();
    final Authenticator authenticator = Authenticator.getInstance();
    final BookDB bookDB = BookDB.getInstance();
    final UserDB userDB = UserDB.getInstance();
    private Scanner scanner = new Scanner(System.in);

    private GUI() {
    }

    public static void showSignUpResult(boolean result) {
        if (result) {
            System.out.println("\nRegistration successful\n");
        } else {
            System.out.println("Login is already taken");
        }
    }

    public static void showOrderResult(boolean result) {
        if (result) {
            System.out.println("\nOrder successfull\n");
        } else {
            System.out.println("Book of this title is not available in the bookstore or is out of stock");
        }
    }

    public static void showRestockResult(boolean result) {
        if (result) {
            System.out.println("\nRestock successfull\n");
        } else {
            System.out.println("Book of this title is not available in the bookstore");
        }
    }

    public static void showResultStatus(boolean result) {
        if (result) {
            System.out.println("\nUser status updated successfully\n");
        } else {
            System.out.println("User of this login does not exists");
        }
    }

    public static GUI getInstance() {
        return instance;
    }

    public String showStartMenu() {
        System.out.println("1. Sign in");
        System.out.println("2. Sign up");
        System.out.println("3. Exit");
        return this.scanner.nextLine();
    }

    public String showMenu() {

        System.out.println("1. List books");
        System.out.println("2. Buy book");
        System.out.println("3. Sign out");
        System.out.println("4. Exit");
        if (authenticator.getLoggedUser() != null &&
                authenticator.getLoggedUser().getRole() == User.Role.ADMIN) {
            System.out.println("5. Restock books");
            System.out.println("6. List users");
            System.out.println("7. Grant user admin status");

        }
        return this.scanner.nextLine();
    }

    public User readLoginAndPassword() {
        User user = new User();
        System.out.println("Login:");
        user.setLogin(this.scanner.nextLine());
        System.out.println("Password:");
        user.setPassword(this.scanner.nextLine());
        return user;
    }

    public User setNewUser() {
        User user = new User();
        System.out.println("Login:");
        user.setLogin(this.scanner.nextLine());

        System.out.println("Password:");
        user.setPassword(DigestUtils.md5Hex(this.scanner.nextLine() + Authenticator.seed));
        return user;
    }

    public void listBooks() {
        for (Book book : this.bookDB.getBooks()) {
            System.out.println(book);
        }
    }

    public void listUsers() {
        for (User user : this.userDB.getUsers()) {
            System.out.println(user);
        }
    }

    public boolean buy() {
        int counter = 0;
        System.out.println("Title:");
        String title = this.scanner.nextLine();
        System.out.println("Amount:");
        String amount = this.scanner.nextLine();
        while (Integer.parseInt(amount) <= 0 && counter < 3) {
            System.out.println("Amount of books to buy is not valid");
            System.out.println("Amount:");
            amount = this.scanner.nextLine();
        }
        System.out.println("\nOrdering...");
        return bookDB.buyBook(title, amount);
    }

    public void restock() {
        int counter = 0;
        System.out.println("Title:");
        String title = this.scanner.nextLine();
        System.out.println("Amount:");
        String amount = this.scanner.nextLine();
        while (Integer.parseInt(amount) <= 0 && counter < 3) {
            System.out.println("Amount of books to restock is not valid");
            System.out.println("Amount:");
            amount = this.scanner.nextLine();
        }
        System.out.println("\nRestocking...");
        showRestockResult(bookDB.restockBook(title, amount));
    }

    public void grantStatus() {
        System.out.println("User login to grant admin status:");
        String login = this.scanner.nextLine();
        System.out.println("\nUpdating user status...");
        showResultStatus(userDB.grantAdminStatus(login));
    }
}
