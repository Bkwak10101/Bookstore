package pl.bkwak.book.store.model;

public final class PrintedBook extends Book{
    private Cover cover;
    public PrintedBook(String author, String title, double price, String releaseDate,
                       int inStock, Category category, String isbn, Cover cover) {
        super(author, title, price, releaseDate, inStock, category, isbn);
        this.cover = cover;
    }

    public Cover getCover() {
        return cover;
    }

    @Override
    public String toString() {
        System.out.println(super.toString());
        return new StringBuilder()
                .append(" Okladka: ")
                .append(formatName(this.getCover()))
                .append("\n Ilosc w magazynie: ").append(this.getInStock())
                .toString();
    }

    private String formatName(Cover cover){
        return switch (cover){
            case SOFT -> "okladka miekka";
            case HARD -> "okladka twarda";
        };
    }

    public enum Cover{
        HARD,
        SOFT
    }
}
