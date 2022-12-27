package pl.bkwak.book.store.model;

import java.text.Format;

public final class Ebook extends Book{
    private Format format;

    public Ebook(String author, String title, double price, String releaseDate,
                 Category category, String isbn, Format format) {
        super(author, title, price, releaseDate, category, isbn);
        this.format = format;
    }

    public Format getFormat() {
        return format;
    }

    @Override
    public String toString() {
        System.out.println(super.toString());
        return new StringBuilder()
                .append(" Format ebook: ")
                .append(formatName(this.getFormat()))
                .toString();
    }

    private String formatName(Format format){
        return switch (format){
            case MOBI -> "MOBI";
            case EPUB -> "EPUB";
        };
    }

    public enum Format{
        MOBI,
        EPUB
    }
}
