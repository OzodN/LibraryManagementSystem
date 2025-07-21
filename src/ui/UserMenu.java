/*
 * Copyright © 2025 Your Name
 *
 * This code is licensed under the MIT License.
 * See the LICENSE.txt file for details.
 */

package ui;

import model.Book;
import org.w3c.dom.ls.LSOutput;
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

            String input = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                return;
            }

            switch (choice) {
                case 1 -> listBooks();
                case 2 -> findBook();
                case 3 -> borrowBook();
                case 4 -> returnBook();
                case 0 -> {
                    System.out.println("Goodbye!");
                    shutdown();
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
            pause();
            return;
        }

        System.out.println("\nList of available books: ");
        int i = 1;
        for (Book b : books) {
            System.out.println(i + ". " + b.getTitle());
            i++;
        }

        System.out.print("Enter a book title to get more information (or press Enter to skip): ");
        String title = sc.nextLine();

        if (!title.isBlank()) {
            Book found = libraryService.findBookByTitle(title);
            if (found != null) {
                System.out.println(found);
            } else {
                System.out.println("Book not found.");
                pause();
                return;
            }
        }

        pause();
    }

    private void findBook() {
        System.out.println("Enter Book ID/Title (or 0 to cancel): ");
        String input = sc.nextLine();

        if (input.equals("0")) return;

        Book found = null;
        try {
            int id = Integer.parseInt(input);
            found = libraryService.findBookByID(id);
        } catch (NumberFormatException e) {
            found = libraryService.findBookByTitle(input);
        }

        if (found != null) System.out.println("Book found: " + found);
        else System.out.println("Book not found.");

        pause();
    }

    private void borrowBook() {
        System.out.print("Enter book ID/Title to borrow (or 0 to cancel): ");
        String input = sc.nextLine();

        if (input.equals("0")) return;

        Book book = null;
        try {
            int id = Integer.parseInt(input);
            book = libraryService.findBookByID(id);
        } catch (NumberFormatException e) {
            book = libraryService.findBookByTitle(input);
        }

        if (book != null) {
            System.out.println(book);
        } else {
            System.out.println("Book not found.");
            pause();
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("This book is already borrowed");
            pause();
            return;
        }

        System.out.println("Do you want to borrow this book? (Y/N): ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("Y")) libraryService.borrowBookByID(book.getId());
        else System.out.println("Borrowing canceled.");

        pause();
    }

    private void returnBook() {
        System.out.print("Enter book ID/Title to return (or 0 to cancel): ");
        String input = sc.nextLine();

        if (input.equals("0")) return;

        Book book = null;
        try {
            int id = Integer.parseInt(input);
            book = libraryService.findBookByID(id);
        } catch (NumberFormatException e) {
            book = libraryService.findBookByTitle(input);
        }

        if (book != null) {
            System.out.println(book);
        } else {
            System.out.println("Book not found.");
            pause();
            return;
        }

        if (book.isAvailable()) {
            System.out.println("This book is not currently borrowed.");
            pause();
            return;
        }
        System.out.println("Do you want to return this book? (Y/N): ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("Y")) libraryService.returnBookByID(book.getId());
        else System.out.println("Returning canceled.");


        pause();
    }

    private void pause() {
        System.out.print("Press ENTER to return to the menu...");
        sc.nextLine();
    }

    private void shutdown() {
        libraryService.shutdown();
    }
}
