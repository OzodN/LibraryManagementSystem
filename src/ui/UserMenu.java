package ui;

import model.Book;
import service.LibraryService;
import java.util.Scanner;

public class UserMenu {
    private static final LibraryService libraryService = LibraryService.getInstance();
    private static final Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n======= User Menu =======" +
                    "\n1. Show all available books" +
                    "\n2. Find book" +
                    "\n3. Borrow book" +
                    "\n4. Return book" +
                    "\n0. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> listBooks();
                case 2 -> findBook();
                case 3 -> borrowBook();
                case 4 -> returnBook();
                case 0 -> {
                    System.out.println("Goodbye!");
                    shoutdown();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void listBooks() {
        var books = libraryService.listBooks();
        if (books.isEmpty()) {
            System.out.println("No book available.");
            return;
        } else {
            System.out.println("\nList of available books: ");

            for (Book b : books) {
                System.out.println(b.getTitle());
            }

            System.out.print("Enter a book title to get more information: ");
            String title = sc.nextLine();
            if (!title.isBlank()) {
                Book found = libraryService.findBookByTitle(title);
                if (found != null) {
                    System.out.println(found);
                } else {
                    System.out.println("Book not found.");
                    return;
                }
            }
            System.out.print("Press ENTER to return to menu: ");
            String input = sc.nextLine();
            if (input.equals("")) return;
            else return;
        }
    }

    private void findBook() {
        System.out.println("Enter Book ID/Title: ");
        String choice = sc.nextLine();
        Book found = null;
        try {
            int id = Integer.parseInt(choice);
            found = libraryService.findBookByID(id);
        } catch (NumberFormatException e) {
            found = libraryService.findBookByTitle(choice);
        }
        if (found != null) System.out.println("Book found: " + found);
        else System.out.println("Book not found.");
        System.out.println("Press ENTER to return to menu: ");
        String input = sc.nextLine();
        if (input.equals("")) return;
        else return;
    }

    private void borrowBook() {
        System.out.print("Enter book ID/Title to borrow: ");
        String choice = sc.nextLine();
        try {
            int id = Integer.parseInt(choice);
            libraryService.borrowBookByID(id);
        } catch (NumberFormatException e) {
            libraryService.borrowBookByTitle(choice);
        }
        System.out.println("Press ENTER to return to menu: ");
        String input = sc.nextLine();
        if (input.equals("")) return;
        else return;
    }

    private void returnBook() {
        System.out.print("Enter book ID/Title to return: ");
        String choice = sc.nextLine();
        try {
            int id = Integer.parseInt(choice);
            libraryService.returnBookByID(id);
        } catch (NumberFormatException e) {
            libraryService.returnBookByTitle(choice);
        }
        System.out.println("Press ENTER to return to menu: ");
        String input = sc.nextLine();
        if (input.equals("")) return;
        else return;
    }

    private void shoutdown() {
        libraryService.shutdown();
    }
}
