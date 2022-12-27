package pl.bkwak.book.store.model;

public sealed class Book permits AudioBook,Ebook,PrintedBook {
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

    public Book(String author, String title, double price,
                String releaseDate, Category category, String isbn) {
        this.author = author;
        this.title = title;
        this.price = price;
        this.releaseDate = releaseDate;
        this.category = category;
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("--------------------------------------")
                .append("\n Tytul: ").append((this.getTitle()))
                .append("\n Autor: ").append((this.getAuthor()))
                .append("\n Kategoria: ").append(categoryName(this.getCategory()))
                .append("\n Rok wydania: ").append(this.getReleaseDate())
                .append("\n Cena: ").append(this.getPrice())
                .toString();
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getReleaseDate() {
        return releaseDate;
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

    private String categoryName(Category category) {
         return switch (category) {
            case BIOGRAPHY -> "Biografia";
            case CRIME -> "Kryminal";
            case FANTASY -> "Fantastyka";
            case THRILLER -> "Thriller";
            case SCIENCE_FICTION -> "Science Fiction";
            default -> "";
        };
    }

    public enum Category {
        BIOGRAPHY,
        CRIME,
        THRILLER,
        FANTASY,
        SCIENCE_FICTION
    }

}
