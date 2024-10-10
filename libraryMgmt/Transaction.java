package libraryMgmt;

import java.time.LocalDateTime;

public class Transaction {
	private static int transactionCounter = 0;
	private int transactionId;
	private Book book;
	private Member member;
	private LocalDateTime borrowDate;
	private LocalDateTime returnDate;

    public Transaction(Book book, Member member) {
        this.transactionId = ++transactionCounter;
        this.book = book;
        this.member = member;
        this.borrowDate = LocalDateTime.now();
    }

    public void completeReturn() {
        this.returnDate = LocalDateTime.now();
    }

    public String getTransactionDetails() {
        return "Transaction ID: " + transactionId + ", Book: " + book.getBookDetails() + ", Member: " + member.getMemberDetails() +
                ", Borrow Date: " + borrowDate + ", Return Date: " + returnDate;
    }

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
    
    
}