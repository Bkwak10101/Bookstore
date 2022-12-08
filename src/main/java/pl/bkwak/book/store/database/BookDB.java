package pl.bkwak.book.store.database;

import pl.bkwak.book.store.model.Book;

import java.util.LinkedList;

public class BookDB {
    private static final BookDB instance = new BookDB();
    private LinkedList<Book> books = new LinkedList<Book>();

    private BookDB() {
        this.books.add(new Book("Remigiusz Mroz", "Iluzionista",
                25.93, "2019-10-30", 10,
                Book.Category.CRIME, "9788380759183"));
        this.books.add(new Book("Kobe Bryant", "Mamba mentality: How I play",
                44.23, "2019-10-16", 5,
                Book.Category.BIOGRAPHY, "9788381295185"));
        this.books.add(new Book("Karpyshyn Drew", "Darth Bane: Dynasty of Evil",
                52.48, "2010-09-01", 2,
                Book.Category.SCIENCE_FICTION, "9788324136551"));
        this.books.add(new Book("Andrzej Sapkowski", "Miecz przeznaczenia. Wiedzmin",
                39.92, "2014-10-06", 6,
                Book.Category.FANTASY, "9788375780642"));
        this.books.add(new Book("Stephen King", "To",
                38.89, "2011-05-01", 4,
                Book.Category.THRILLER, "9788381257022"));
    }

    public static BookDB getInstance() {
        return instance;
    }

    public LinkedList<Book> getBooks() {
        return books;
    }

    public boolean buyBook(String title, String amount) {
        for (Book book : this.books) {
            if (book.getTitle().equals(title) && book.getInStock() > 0 && book.getInStock() - Integer.valueOf(amount) >= 0) {
                book.setInStock(book.getInStock() - Integer.valueOf(amount));
                return true;
            }
        }
        return false;
    }

    public boolean restockBook(String title, String amount) {
        for (Book book : this.books) {
            if (book.getTitle().equals(title)) {
                book.setInStock(book.getInStock() + Integer.valueOf(amount));
                return true;
            }
        }
        return false;
    }
}
