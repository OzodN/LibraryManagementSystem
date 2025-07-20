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
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> listBooks();
                case 2 -> findBook();
                case 3 -> addBook();
                case 4 -> updateBook();
                case 5 -> removeBook();
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
                int i = 1;
                System.out.println( i + ". " + b.getTitle());
                i++;
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

    private void addBook() {
        System.out.print("Enter book title: ");
        String title = sc.nextLine();
        if (title.equals("0")) return;
        System.out.print("Enter book author: ");
        String author = sc.nextLine();
        if (author.equals("0")) return;
        libraryService.addBook(title, author);
        System.out.println("Book added successfully!" +
                "\nPress ENTER to return to menu: ");
        String input = sc.nextLine();
        if (input.equals("")) return;
        else return;
    }

    private void updateBook() {
        System.out.println("Choose an option: " +
                "\n1. Update a whole book" +
                "\n2. Update only a title" +
                "\n3. Update only an author" +
                "\n0. Back to menu");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1 -> {
                System.out.println("Enter book ID: ");
                int id = sc.nextInt();
                sc.nextLine();
                if (id == 0) return;
                libraryService.getBookInfo(id);
                System.out.print("Enter book title: ");
                String title = sc.nextLine();
                if (title.equals("0")) return;
                System.out.print("Enter book author: ");
                String author = sc.nextLine();
                if (author.equals("0")) return;
                libraryService.updateBook(id, title, author);
            }
            case 2 -> {
                System.out.println("Enter book ID: ");
                int id = sc.nextInt();
                sc.nextLine();
                if (id == 0) return;
                libraryService.getBookInfo(id);
                System.out.print("Enter book title: ");
                String title = sc.nextLine();
                if (title.equals("0")) return;
                libraryService.updateTheTitleOfTheBook(id, title);
            }
            case 3 -> {
                System.out.println("Enter book ID: ");
                int id = sc.nextInt();
                sc.nextLine();
                if (id == 0) return;
                libraryService.getBookInfo(id);
                System.out.print("Enter book author: ");
                String author = sc.nextLine();
                if (author.equals("0")) return;
                libraryService.updateTheAuthorOfTheBook(id, author);
            }
            case 0 -> {
                return;
            }
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
        if (found != null) {
            System.out.println("Book found:\n" + found);
        }
        else System.out.println("Book not found.");
        System.out.println("Press ENTER to return to menu: ");
        String input = sc.nextLine();
        if (input.equals("")) return;
        else return;
    }

    private void removeBook() {
        System.out.println("Enter book ID you want to remove: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (id == 0) return;
        libraryService.getBookInfo(id);
        System.out.println("Press ENTER to confirm deletion...");
        String confirmation = sc.nextLine();
        if (confirmation.equals("")) {
            libraryService.removeBook(id);
            System.out.println("Book removed successfully!" +
                "\nPress ENTER to return to menu: ");
            String choice = sc.nextLine();
        } else return;
    }

    private void shoutdown() {
        libraryService.shutdown();
    }
}
