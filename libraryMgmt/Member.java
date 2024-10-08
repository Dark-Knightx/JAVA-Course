package libraryMgmt;

import java.util.ArrayList;

public class Member {
    private int memberId;
    private String name;
    private ArrayList<Book> borrowedBooks;

    public Member(int memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getMemberDetails() {
        StringBuilder details = new StringBuilder("Member ID: " + memberId + ", Name: " + name + "\nBorrowed Books:\n");
        for (Book book : borrowedBooks) {
            details.append(book.getBookDetails()).append("\n");
        }
        return details.toString();
    }

    public boolean borrowBook(Book book) {
        if (book.borrowBook()) {
            borrowedBooks.add(book);
            return true;
        }
        return false;
    }

    public void returnBook(Book book) {
        book.returnBook();
        borrowedBooks.remove(book);
    }

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    
}