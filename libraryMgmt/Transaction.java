package libraryMgmt;

import java.time.LocalDateTime;

public class Transaction {
    static int transactionCounter = 0;
    int transactionId;
    Book book;
    Member member;
    LocalDateTime borrowDate;
    LocalDateTime returnDate;

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
}