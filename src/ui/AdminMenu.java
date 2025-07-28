/*
 * Copyright © 2025 Nuritdinov Ozod
 *
 * This code is licensed under the MIT License.
 * See the LICENSE.txt file for details.
 */

package ui;

import model.Book;
import service.LibraryService;
import util.InputUtil;

import java.util.Scanner;

public class AdminMenu {
    private static final LibraryService libraryService = LibraryService.getInstance();
    private static final Scanner sc = new Scanner(System.in);
    private static final InputUtil inputUtils = new InputUtil();


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
                continue;
            }

            switch (choice) {
                case 1 -> listBooks();
                case 2 -> findBook();
                case 3 -> addBook();
                case 4 -> updateBook();
                case 5 -> removeBook();
                case 0 -> {
                    System.out.println("Goodbye!");
                    libraryService.shutdown();
                    System.exit(0);
                }
                default -> {
                    System.out.println("Invalid choice.");
                    continue;
                }
            }
        }
    }

    private void listBooks() {
        var books = libraryService.listBooks();
        if (books.isEmpty()) {
            System.out.println("No book available.");
            inputUtils.pause();
            return;
        }
        System.out.println("\nList of available books: ");
        int i = 1;
        for (Book b : books) {
            System.out.println(i + ". " + b.getTitle());
            i++;
        }
        while (true) {
            System.out.print("Enter a book title to get more information (or press ENTER to skip): ");
            String title = sc.nextLine();
            if (!title.isBlank()) {
                Book found = libraryService.findBookByTitle(title);
                if (found != null) {
                    System.out.println(found);
                    inputUtils.pause();
                    break;
                } else {
                    System.out.println("Book not found. Try again.");
                    continue;
                }
            } else break;
        }
    }


    private void addBook() {
        String title = inputUtils.promptNonBlank("Enter book title (or 0 to cancel): ", "title");
        if (title == null) return;

        String author = inputUtils.promptNonBlank("Enter book author (or 0 to cancel): ", "author");
        if (author == null) return;

        boolean success = libraryService.addBook(title, author);
        if (success) System.out.println("Book added: " + title + " by " + author);
        else System.out.println("Book already exists.");

        inputUtils.pause();
    }

    private void updateBook() {
        int id = inputUtils.promptInt("Enter book ID (or 0 to cancel): ");
        if (id == 0) return;

        Book book = libraryService.findBookByID(id);
        if (book != null) {
            System.out.println(book);
        } else {
            System.out.println("Book not found.");
            inputUtils.pause();
            return;
        }

        System.out.print("Do you want to update this book? (Y/N): ");
        String confirm = sc.nextLine();
        while (true) {
            if (confirm.equalsIgnoreCase("Y")) {
                System.out.println("Choose an option: " +
                        "\n1. Update a whole book" +
                        "\n2. Update only a title" +
                        "\n3. Update only an author" +
                        "\n0. Back to menu");
                System.out.flush();
                String input = sc.nextLine();
                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        String title = inputUtils.promptNonBlank("Enter new book title (or 0 to cancel): ", "title");
                        if (title == null) break;
                        String author = inputUtils.promptNonBlank("Enter new book author (or 0 to cancel): ", "author");
                        if (author == null) break;
                        libraryService.updateBook(id, title, author);
                        inputUtils.pause();
                        return;
                    }
                    case 2 -> {
                        String title = inputUtils.promptNonBlank("Enter new book title (or 0 to cancel): ", "title");
                        if (title == null) break;
                        libraryService.updateBookTitle(id, title);
                        inputUtils.pause();
                        return;
                    }
                    case 3 -> {
                        String author = inputUtils.promptNonBlank("Enter new book author (or 0 to cancel): ", "author");
                        if (author == null) break;
                        libraryService.updateBookAuthor(id, author);
                        inputUtils.pause();
                        return;
                    }
                    case 0 -> {
                        return;
                    }
                    default -> {
                        System.out.println("Invalid choice.");
                        continue;
                    }
                }
            } else {
                System.out.println("Updating canceled.");
                inputUtils.pause();
                break;
            }
        }
    }

    private void findBook() {
        String input = inputUtils.promptNonBlank("Enter book ID/Title (or 0 to cancel): ", "title");
        if (input == null) return;

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

        inputUtils.pause();
    }

    private void removeBook() {
        int id = inputUtils.promptInt("Enter book ID to remove (or 0 to cancel): ");
        if (id == 0) return;

        Book book = libraryService.findBookByID(id);
        if (book != null) {
            System.out.println(book);
        } else {
            System.out.println("Book not found.");
            inputUtils.pause();
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

        inputUtils.pause();
    }
}