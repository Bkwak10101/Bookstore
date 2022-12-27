package pl.bkwak.book.store.database;

import pl.bkwak.book.store.model.AudioBook;
import pl.bkwak.book.store.model.Book;
import pl.bkwak.book.store.model.Ebook;
import pl.bkwak.book.store.model.PrintedBook;

import java.util.LinkedList;

public class BookDB {
    private static final BookDB instance = new BookDB();
    private LinkedList<PrintedBook> books = new LinkedList<PrintedBook>();
    private LinkedList<AudioBook> audioBooks = new LinkedList<AudioBook>();
    private LinkedList<Ebook> ebooks = new LinkedList<Ebook>();

    private BookDB() {
        this.books.add(new PrintedBook("Remigiusz Mroz", "Iluzionista",
                26.71, "2019-10-30", 10,
                Book.Category.CRIME, "9788380759183", PrintedBook.Cover.SOFT));
        this.ebooks.add(new Ebook("Remigiusz Mroz", "Iluzionista",
                23.30, "2019-10-30",
                Book.Category.CRIME, "9788380759183", Ebook.Format.MOBI));
        this.ebooks.add(new Ebook("Remigiusz Mroz", "Iluzionista",
                21.30, "2019-10-30",
                Book.Category.CRIME, "9788380759183", Ebook.Format.EPUB));
        this.audioBooks.add(new AudioBook("Remigiusz Mroz", "Iluzionista",
                31.49, "2019-10-30", 3,
                Book.Category.CRIME, "9788380759183", AudioBook.Format.CD));
        this.audioBooks.add(new AudioBook("Remigiusz Mroz", "Iluzionista",
                29.90, "2019-10-30", 6,
                Book.Category.CRIME, "9788380759183", AudioBook.Format.MP3));
        this.books.add(new PrintedBook("Kobe Bryant", "Mamba mentality: How I play",
                50.99, "2019-10-16", 6,
                Book.Category.BIOGRAPHY, "9788381295185", PrintedBook.Cover.SOFT));
        this.ebooks.add(new Ebook("Kobe Bryant", "Mamba mentality: How I play",
                59.99, "2019-10-16",
                Book.Category.BIOGRAPHY, "9788381295185", Ebook.Format.MOBI));
        this.ebooks.add(new Ebook("Kobe Bryant", "Mamba mentality: How I play",
                57.99, "2019-10-16",
                Book.Category.BIOGRAPHY, "9788381295185", Ebook.Format.EPUB));
        this.books.add(new PrintedBook("Karpyshyn Drew", "Darth Bane: Dynasty of Evil",
                52.48, "2010-09-01", 2,
                Book.Category.SCIENCE_FICTION, "9788324136551", PrintedBook.Cover.SOFT));
        this.books.add(new PrintedBook("Andrzej Sapkowski", "Miecz przeznaczenia. Wiedzmin",
                40.72, "2014-10-06", 4,
                Book.Category.FANTASY, "9788375780642", PrintedBook.Cover.SOFT));
        this.ebooks.add(new Ebook("Andrzej Sapkowski", "Miecz przeznaczenia. Wiedzmin",
                29.99, "2014-10-06",
                Book.Category.FANTASY, "9788375780642", Ebook.Format.MOBI));
        this.ebooks.add(new Ebook("Andrzej Sapkowski", "Miecz przeznaczenia. Wiedzmin",
                27.99, "2014-10-06",
                Book.Category.FANTASY, "9788375780642", Ebook.Format.EPUB));
        this.books.add(new PrintedBook("Stephen King", "To",
                49.54, "2011-05-01", 4,
                Book.Category.THRILLER, "9788381257022", PrintedBook.Cover.HARD));
        this.audioBooks.add(new AudioBook("Stephen King", "To",
                45.90, "2011-05-01", 6,
                Book.Category.THRILLER, "9788381257022", AudioBook.Format.MP3));
        this.audioBooks.add(new AudioBook("Stephen King", "To",
                45.90, "2011-05-01", 3,
                Book.Category.THRILLER, "9788381257022", AudioBook.Format.CD));
        this.ebooks.add(new Ebook("Stephen King", "To",
                35.00, "2011-05-01",
                Book.Category.THRILLER, "9788381257022", Ebook.Format.MOBI));
        this.ebooks.add(new Ebook("Stephen King", "To",
                32.00, "2011-05-01",
                Book.Category.THRILLER, "9788381257022", Ebook.Format.EPUB));
    }

    public static BookDB getInstance() {
        return instance;
    }

    public LinkedList<PrintedBook> getBooks() {
        return books;
    }

    public LinkedList<AudioBook> getAudioBooks() {
        return audioBooks;
    }

    public LinkedList<Ebook> getEbooks() {
        return ebooks;
    }

    public boolean buyPrintedBook(String title, String amount) {
        return books.stream()
                .filter(book -> book.getTitle().equals(title) && book.getInStock() > 0 &&
                        book.getInStock() - Integer.valueOf(amount) >= 0)
                .findFirst()
                .map(book -> {
                    book.setInStock(book.getInStock() - Integer.valueOf(amount));
                    return true;
                })
                .orElse(false);
    }

    public boolean buyAudiobook(String title, int file, String amount) {
        AudioBook.Format format = AudioBook.Format.MP3;
        System.out.println("FILE: " + file);
        if (file == 2) format = AudioBook.Format.CD;
        AudioBook.Format finalFormat = format;
        return audioBooks.stream()
                .filter(book -> book.getTitle().equals(title) && book.getFormat().equals(finalFormat)
                        && book.getInStock() > 0 && book.getInStock() - Integer.valueOf(amount) >= 0)
                .findFirst()
                .map(book -> {
                    book.setInStock(book.getInStock() - Integer.valueOf(amount));
                    return true;
                })
                .orElse(false);
    }

    public boolean buyEbook(String title, int file) {
        Ebook.Format format = Ebook.Format.MOBI;
        if (file == 2) format = Ebook.Format.EPUB;
        Ebook.Format finalFormat = format;
        return ebooks.stream()
                .filter(book -> book.getTitle().equals(title) && book.getFormat().equals(finalFormat))
                .findFirst()
                .map(book -> true)
                .orElse(false);
    }

    public boolean restockPrintedBook(String title, String amount) {
        return books.stream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst()
                .map(book -> {
                    book.setInStock(book.getInStock() + Integer.valueOf(amount));
                    return true;
                })
                .orElse(false);
    }

    public boolean restockAudiobook(String title, int file, String amount) {
        AudioBook.Format format = AudioBook.Format.MP3;
        if (file == 2) format = AudioBook.Format.CD;
        AudioBook.Format finalFormat = format;
        return audioBooks.stream()
                .filter(book -> book.getTitle().equals(title) && book.getFormat().equals(finalFormat))
                .findFirst()
                .map(book -> {
                    book.setInStock(book.getInStock() + Integer.valueOf(amount));
                    return true;
                })
                .orElse(false);
    }

    public boolean restockEbook(String title, int file, String amount) {
        Ebook.Format format = Ebook.Format.MOBI;
        if (file == 2) format = Ebook.Format.EPUB;
        Ebook.Format finalFormat = format;
        return ebooks.stream()
                .filter(book -> book.getTitle().equals(title) && book.getFormat().equals(finalFormat))
                .findFirst()
                .map(book -> {
                    book.setInStock(book.getInStock() + Integer.valueOf(amount));
                    return true;
                })
                .orElse(false);
    }
}