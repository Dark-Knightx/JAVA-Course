package libraryMgmt;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        int choice;  

        do {
            System.out.println("====== Library Management System ======");
            System.out.println("1. Add Book");
            System.out.println("2. Register Member");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display Available Books");
            System.out.println("6. Display Member Details");
            System.out.println("7. Exit");
            System.out.println("=======================================");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Book Author: ");
                    String author = scanner.nextLine();
                    library.addBook(new Book(bookId, title, author));
                    break;
                case 2:
                    System.out.print("Enter Member ID: ");
                    int memberId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter Member Name: ");
                    String name = scanner.nextLine();
                    library.registerMember(new Member(memberId, name));
                    break;
                case 3:
                    System.out.print("Enter Member ID: ");
                    int borrowMemberId = scanner.nextInt();
                    System.out.print("Enter Book ID: ");
                    int borrowBookId = scanner.nextInt();
                    if (library.borrowBook(borrowMemberId, borrowBookId)) {
                        System.out.println("Book borrowed successfully.");
                    } else {
                        System.out.println("Unable to borrow the book.");
                    }
                    break;
                case 4:
                    System.out.print("Enter Member ID: ");
                    int returnMemberId = scanner.nextInt();
                    System.out.print("Enter Book ID: ");
                    int returnBookId = scanner.nextInt();
                    if (library.returnBook(returnMemberId, returnBookId)) {
                        System.out.println("Book returned successfully.");
                    } else {
                        System.out.println("Unable to return the book.");
                    }
                    break;
                case 5:
                    library.displayAvailableBooks();
                    break;
                case 6:
                    System.out.print("Enter Member ID: ");
                    int displayMemberId = scanner.nextInt();
                    library.displayMemberDetails(displayMemberId);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);

        scanner.close();
    }
}

