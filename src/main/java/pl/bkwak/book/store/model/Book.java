package pl.bkwak.book.store.model;

public class Book {
    private String author;
    private String title;
    private double price;
    private String releaseDate;
    private int inStock;
    private Category category;
    private String isbn;

    public Book(String author, String title, double price,
                String releaseDate, int inStock, Category category, String isbn) {
        this.author = author;
        this.title = title;
        this.price = price;
        this.releaseDate = releaseDate;
        this.inStock = inStock;
        this.category = category;
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("\n Tytul: ").append((this.getTitle()))
                .append("\n Autor: ").append((this.getAuthor()))
                .append("\n Kategoria: ").append((categoryName(this.getCategory())))
                .append("\n Rok wydania: ").append(this.getReleaseDate())
                .append("\n Cena: ").append(this.getPrice())
                .append("\n Ilosc w magazynie: ").append(this.getInStock())
                .toString();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getInStock() {
        return inStock;
    }


    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    private String categoryName(Category category) {
        switch (category) {
            case BIOGRAPHY:
                return "Biografia";

            case CRIME:
                return "Kryminal";

            case FANTASY:
                return "Fantastyka";

            case THRILLER:
                return "Thriller";

            case SCIENCE_FICTION:
                return "Science Fiction";

            default:
                return "";
        }
    }

    public enum Category {
        BIOGRAPHY,
        CRIME,
        THRILLER,
        FANTASY,
        SCIENCE_FICTION
    }

}
