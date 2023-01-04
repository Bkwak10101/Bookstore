package pl.bkwak.book.store.gui;

import org.apache.commons.codec.digest.DigestUtils;
import pl.bkwak.book.store.core.Authenticator;
import pl.bkwak.book.store.database.BookDB;
import pl.bkwak.book.store.database.UserDB;
import pl.bkwak.book.store.model.*;

import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

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
        System.out.println("1. List printed books");
        System.out.println("2. List audiobooks");
        System.out.println("3. List ebooks");
        System.out.println("4. Buy book");
        System.out.println("5. Sign out");
        System.out.println("6. Exit");
        if (authenticator.getLoggedUser() != null &&
                authenticator.getLoggedUser().getRole() == User.Role.ADMIN) {
            System.out.println("7. Restock books");
            System.out.println("8. List users");
            System.out.println("9. Grant user admin status");

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
        bookDB.getBooks().stream().forEach(System.out::println);
    }

    public void listAudioBooks(){
        bookDB.getAudioBooks().stream().forEach(System.out::println);
    }

    public void listEbooks(){
        bookDB.getEbooks().stream().forEach(System.out::println);
    }

    public void listUsers() {
        userDB.getUsers().stream().forEach(System.out::println);
    }

    public boolean buy() {
        System.out.println("Title:");
        String title = this.scanner.nextLine();
        int format = selectFormat(title, true);
        System.out.println("Amount:");
        String amount = this.scanner.nextLine();
        while (Integer.parseInt(amount) <= 0) {
            System.out.println("Amount of books to buy is not valid");
            System.out.println("Amount:");
            amount = this.scanner.nextLine();
        }
        System.out.println("\nOrdering...");
        return switch (format) {
            case 1 -> bookDB.buyPrintedBook(title, amount);
            case 2 -> bookDB.buyAudiobook(title,selectFileFormat(format, title), amount);
            case 3 -> bookDB.buyEbook(title,selectFileFormat(format, title));
            default -> false;
        };
    }

    public boolean restock() {
        System.out.println("Title:");
        String title = this.scanner.nextLine();
        int format = selectFormat(title, false);
        System.out.println("Amount:");
        String amount = this.scanner.nextLine();
        while (Integer.parseInt(amount) <= 0) {
            System.out.println("Amount of books to restock is not valid");
            System.out.println("Amount:");
            amount = this.scanner.nextLine();
        }
        System.out.println("\nRestocking...");
        return switch (format) {
            case 1 -> bookDB.restockPrintedBook(title, amount);
            case 2 -> bookDB.restockAudiobook(title,selectFileFormat(format, title), amount);
            default -> false;
        };
    }

    public int selectFormat(String title, boolean isEbookValid) {
        System.out.println(" Available book format: ");
        System.out.println("1. Printed");
        if (bookDB.getAudioBooks().stream().anyMatch(a -> a.getTitle().equals(title))) {
            System.out.println("2. Audiobook");
        }
        if (bookDB.getEbooks().stream().anyMatch(e -> e.getTitle().equals(title)) && isEbookValid) {
            System.out.println("3. Ebook");
        }
        System.out.println("Format:");
        String format = this.scanner.nextLine();
        while (Integer.valueOf(format) <= 0 ||
                (isEbookValid) ? Integer.valueOf(format) > 3 : Integer.valueOf(format) > 2 ) {
            System.out.println("Format of books is not valid");
            System.out.println("Format:");
            format = this.scanner.nextLine();
        }
        return Integer.valueOf(format);
    }

    public int selectFileFormat(int format, String title){
        System.out.println(" Available file format: ");
        if(format == 2){
            System.out.println("1. MP3");
            System.out.println("2. CD");
        }
        if(format == 3){
            System.out.println("1. MOBI");
            System.out.println("2. EPUB");
        }
        System.out.println("File format:");
        String fileFormat = this.scanner.nextLine();
        while (Integer.valueOf(fileFormat) <= 0 || Integer.valueOf(fileFormat) > 2 ) {
            System.out.println("Format of books to buy is not valid");
            System.out.println("Format:");
            fileFormat = this.scanner.nextLine();
        }
        System.out.println(Integer.valueOf(fileFormat));
        return Integer.valueOf(fileFormat) ;
    }

    public void grantStatus() {
        System.out.println("User login to grant admin status:");
        String login = this.scanner.nextLine();
        System.out.println("\nUpdating user status...");
        showResultStatus(userDB.grantAdminStatus(login));
    }
}
