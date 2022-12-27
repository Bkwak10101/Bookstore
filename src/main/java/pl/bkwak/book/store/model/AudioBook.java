package pl.bkwak.book.store.model;

public final class AudioBook extends Book{
    Format format;

    public AudioBook(String author, String title, double price, String releaseDate,
                     int inStock, Category category, String isbn, Format format) {
        super(author, title, price, releaseDate, inStock, category, isbn);
        this.format = format;
    }

    public Format getFormat() {
        return format;
    }

    @Override
    public String toString() {
        System.out.println(super.toString());
        return new StringBuilder()
                .append(" Format audiobook: ")
                .append(formatName(this.getFormat()))
                .append("\n Ilosc w magazynie: ").append(this.getInStock())
                .toString();
    }

    private String formatName(Format format){
        return switch (format){
            case MP3 -> "MP3";
            case CD -> "CD";
        };
    }

    public enum Format{
        MP3,
        CD
    }
}
