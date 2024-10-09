package libraryMgmt;

public class Book {
    int bookId;
    String title;
    String author;
    boolean isAvailable;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getBookDetails() {
        return "ID: " + bookId + ", Title: " + title + ", Author: " + author + ", Available: " + isAvailable;
    }

    public boolean borrowBook() {
        if (isAvailable) {
            isAvailable = false;
            return true;
        }
        return false;
    }

    public void returnBook() {
        isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}

