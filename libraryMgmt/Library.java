package libraryMgmt;

import java.util.ArrayList;

public class Library {
    private ArrayList<Book> bookList;
    private ArrayList<Member> memberList;
    private ArrayList<Transaction> transactionList;

    public Library() {
        bookList = new ArrayList<>();
        memberList = new ArrayList<>();
        transactionList = new ArrayList<>();
    }

    public void addBook(Book book) {
        bookList.add(book);
    }

    public void registerMember(Member member) {
        memberList.add(member);
    }

    public boolean borrowBook(int memberId, int bookId) {
        Member member = findMember(memberId);
        Book book = findBook(bookId);
        if (member != null && book != null && book.isAvailable()) {
            if (member.borrowBook(book)) {
                transactionList.add(new Transaction(book, member));
                return true;
            }
        }
        return false;
    }

    public boolean returnBook(int memberId, int bookId) {
        Member member = findMember(memberId);
        Book book = findBook(bookId);
        if (member != null && book != null) {
            member.returnBook(book);
            for (Transaction transaction : transactionList) {
                if (transaction.getBook() == book && transaction.getMember() == member) {
                    transaction.completeReturn();
                }
            }
            return true;
        }
        return false;
    }

    public void displayAvailableBooks() {
        for (Book book : bookList) {
            if (book.isAvailable()) {
                System.out.println(book.getBookDetails());
            }
        }
    }

    public void displayMemberDetails(int memberId) {
        Member member = findMember(memberId);
        if (member != null) {
            System.out.println(member.getMemberDetails());
        } else {
            System.out.println("Member not found.");
        }
    }

    private Member findMember(int memberId) {
        for (Member member : memberList) {
            if (memberId == member.getMemberId()) { // Correctly accessing memberId
                return member;
            }
        }
        return null;
    }

    private Book findBook(int bookId) {
        for (Book book : bookList) {
            if (bookId == book.getBookId()) { // Correctly accessing bookId
                return book;
            }
        }
        return null;
    }
}

