package service;

import model.Book;
import repository.BookFileRepository;

import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private static LibraryService instance;
    private List<Book> books = new ArrayList<>();
    private final BookFileRepository bookFileRepository = new BookFileRepository();

    private LibraryService() {
        this.books = bookFileRepository.loadBook();
    }

    public static LibraryService getInstance() {
        if (instance == null) instance = new LibraryService();
        return instance;
    }

    public void addBook(String title, String author) {
        int nextId = books.stream().mapToInt(Book::getId).max().orElse(0) + 1;
        books.add(new Book(nextId, title, author));
        bookFileRepository.saveBooks(books);
    }

    public void getBookInfo(int id) {
        Book book = findBookByID(id);
        System.out.println(book);
    }

    public void removeBook(int id) {
        Book book = findBookByID(id);
        if (book != null) {
            book.setDeleted(true);
            System.out.println("Book marked as deleted.");
        } else System.out.println("Book not found.");
    }

    public void updateBook(int id, String newTitle, String newAuthor) {
        Book book = findBookByID(id);
        if (book != null) {
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            bookFileRepository.saveBooks(books);
            System.out.println("Book updated successfully!");
        } else System.out.println("Could not update a book. Book doesn't exist.");
    }

    public void updateTheTitleOfTheBook(int id, String newTitle) {
        Book book = findBookByID(id);
        if (book != null) {
            book.setTitle(newTitle);
            bookFileRepository.saveBooks(books);
            System.out.println("The title of a book updated successfully!");
        }
        else System.out.println("Could not update a book. Book doesn't exist.");
    }

    public void updateTheAuthorOfTheBook(int id, String newAuthor) {
        Book book = findBookByID(id);
        if (book != null) {
            book.setAuthor(newAuthor);
            bookFileRepository.saveBooks(books);
            System.out.println("The author of a book updated successfully!");
        }
        else System.out.println("Could not update a book. Book doesn't exist.");
    }

    public Book findBookByID(int id) {
        for (Book b : books) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    public Book findBookByTitle(String title) {
        for (Book b : books) {
            if (!(b.isDeleted()) && b.getTitle().equalsIgnoreCase(title)) {
                return b;
            }
        }
        return null;
    }

    public void borrowBookByID(int id) {
        Book book = findBookByID(id);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            getBookInfo(id);
            bookFileRepository.saveBooks(books);
            System.out.println("Book borrowed successfullly!");
        } else System.out.println("Book not found or already borrowed");
    }

    public void borrowBookByTitle(String title) {
        Book book = findBookByTitle(title);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            System.out.println(book);
            bookFileRepository.saveBooks(books);
            System.out.println("Book borrowed successfullly!");
        } else System.out.println("Book not found or already borrowed");
    }

    public void returnBookByID(int id) {
        Book book = findBookByID(id);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            getBookInfo(id);
            bookFileRepository.saveBooks(books);
            System.out.println("Book returned successfullly!");
        } else System.out.println("Book not found or it wasn't borrowed.");
    }

    public void returnBookByTitle(String title) {
        Book book = findBookByTitle(title);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            System.out.println(book);
            bookFileRepository.saveBooks(books);
            System.out.println("Book returned successfullly!");
        } else System.out.println("Book not found or it wasn't borrowed.");
    }

    public List<Book> listBooks() {
        return books.stream()
                .filter(book -> !book.isDeleted())
                .toList();
    }

    public void shutdown() {
        bookFileRepository.saveBooks(books);
    }
}
