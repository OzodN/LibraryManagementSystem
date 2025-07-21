/*
 * Copyright © 2025 Your Name
 *
 * This code is licensed under the MIT License.
 * See the LICENSE.txt file for details.
 */

package ui;

import model.Book;
import service.LibraryService;

import java.util.Scanner;

public class AdminMenu {
    private static final LibraryService libraryService = LibraryService.getInstance();
    private static final Scanner sc = new Scanner(System.in);

    public void start() {

        while (true) {
            System.out.println("\n======= Admin Menu =======" +
                    "\n1. Show all available books" +
                    "\n2. Find book" +
                    "\n3. Add book" +
                    "\n4. Update book" +
                    "\n5. Remove Book" +
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
                case 3 -> addBook();
                case 4 -> updateBook();
                case 5 -> removeBook();
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

        System.out.print("Enter a book title to get more information (or press ENTER to skip): ");
        String title = sc.nextLine();
        if (!title.isBlank()) {
            Book found = libraryService.findBookByTitle(title);
            if (found != null) {
                System.out.println(found);
            } else {
                System.out.println("Book not found.");
                return;
            }

            pause();
        }
    }

    private void addBook() {
        System.out.print("Enter book title (or 0 to cancel): ");
        String title = sc.nextLine();
        if (title.equals("0")) return;

        System.out.print("Enter book author (or 0 to cancel): ");
        String author = sc.nextLine();
        if (author.equals("0")) return;

        boolean success = libraryService.addBook(title, author);
        if (success) System.out.println("Book added: " + title + " by " + author);
        else System.out.println("Book already exists.");

        pause();
    }

    private void updateBook() {
        int id = promptInt("Enter book ID (or 0 to cancel): ");
        if (id == 0) return;

        Book book = libraryService.findBookByID(id);
        if (book != null) {
            System.out.println(book);
        } else {
            System.out.println("Book not found.");
            pause();
            return;
        }

        System.out.println("Do you want to update this book? (Y/N): ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("Y")) {
            System.out.println("Choose an option: " +
                    "\n1. Update a whole book" +
                    "\n2. Update only a title" +
                    "\n3. Update only an author" +
                    "\n0. Back to menu");

            String input = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                return;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter book title (or 0 to cancel): ");
                    String title = sc.nextLine();
                    if (title.equals("0")) return;
                    System.out.print("Enter book author (or 0 to cancel): ");
                    String author = sc.nextLine();
                    if (author.equals("0")) return;
                    libraryService.updateBook(id, title, author);
                }
                case 2 -> {
                    System.out.print("Enter book title (or 0 to cancel): ");
                    String title = sc.nextLine();
                    if (title.equals("0")) return;
                    libraryService.updateBookTitle(id, title);
                }
                case 3 -> {
                    System.out.print("Enter book author (or 0 to cancel): ");
                    String author = sc.nextLine();
                    if (author.equals("0")) return;
                    libraryService.updateBookAuthor(id, author);
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        } else System.out.println("Updating canceled.");

        pause();
    }

    private void findBook() {
        System.out.print("Enter Book ID/Title (or 0 to cancel): ");
        String input = sc.nextLine();

        if (input.equals("0")) return;

        Book found = null;
        try {
            int id = Integer.parseInt(input);
            found = libraryService.findBookByID(id);
        } catch (NumberFormatException e) {
            found = libraryService.findBookByTitle(input);
        }

        if (found != null) {
            System.out.println("Book found:\n" + found);
        } else System.out.println("Book not found.");

        pause();
    }

    private void removeBook() {
        int id = promptInt("Enter book ID to remove (or 0 to cancel): ");
        if (id == 0) return;

        Book book = libraryService.findBookByID(id);
        if (book != null) {
            System.out.println(book);
        } else {
            System.out.println("Book not found.");
            pause();
            return;
        }
        System.out.print("Do you want to remove this book? (Y/N): ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("Y")) {
            libraryService.removeBook(id);
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("Removing canceled.");
        }

        pause();
    }

    private void shutdown() {
        libraryService.shutdown();
    }

    private int promptInt(String message) {
        System.out.print(message);
        while (!sc.hasNextInt()) {
            System.out.print("Invalid number. Try again: ");
            sc.nextLine();
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }

    private void pause() {
        System.out.print("Press ENTER to return to the menu...");
        sc.nextLine();
    }
}