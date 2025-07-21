/*
 * Copyright © 2025 Your Name
 *
 * This code is licensed under the MIT License.
 * See the LICENSE.txt file for details.
 */

package service;

import model.Book;
import repository.BookFileRepository;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;
import java.util.logging.Level;

public class LibraryService {
    private static LibraryService instance;
    private List<Book> books = new ArrayList<>();
    private final BookFileRepository bookFileRepository = new BookFileRepository();
    private static final Logger logger = Logger.getLogger(LibraryService.class.getName());

    private LibraryService() {
        this.books = bookFileRepository.loadBooks();
    }

    public static synchronized LibraryService getInstance() {
        if (instance == null) instance = new LibraryService();
        return instance;
    }

    public boolean addBook(String title, String author) {
        if (title == null || title.isBlank() || author == null || author.isBlank()) {
            logger.warning("Book title or author cannot be empty");
            return false;
        }

        if (books.stream().anyMatch(b -> b.getTitle().equalsIgnoreCase(title.trim())
                && b.getAuthor().equalsIgnoreCase(author.trim()))) {
            return false;
        }

        int nextId = books.stream().mapToInt(Book::getId).max().orElse(0) + 1;
        books.add(new Book(nextId, title, author));
        bookFileRepository.saveBooks(books);
        return true;
    }

    public void removeBook(int id) {
        Book book = findBookByID(id);
        if (book != null) {
            book.setDeleted(true);
            bookFileRepository.saveBooks(books);
            logger.info("Book marked as deleted.");
        } else logger.warning("Book not found.");
    }

    public void updateBook(int id, String newTitle, String newAuthor) {
        Book book = findBookByID(id);
        if (book != null) {
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            bookFileRepository.saveBooks(books);
            logger.info("Book updated successfully!");
        } else logger.warning("Could not update a book. Book doesn't exist.");
    }

    public void updateBookTitle(int id, String newTitle) {
        Book book = findBookByID(id);
        if (book != null) {
            book.setTitle(newTitle);
            bookFileRepository.saveBooks(books);
            logger.info("The title of a book updated successfully!");
        } else logger.warning("Could not update a book. Book doesn't exist.");
    }

    public void updateBookAuthor(int id, String newAuthor) {
        Book book = findBookByID(id);
        if (book != null) {
            book.setAuthor(newAuthor);
            bookFileRepository.saveBooks(books);
            logger.info("The author of a book updated successfully!");
        } else logger.warning("Could not update a book. Book doesn't exist.");
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
            bookFileRepository.saveBooks(books);
            logger.info("Book borrowed successfully!");
        } else logger.warning("Book not found or already borrowed");
    }

    public void borrowBookByTitle(String title) {
        Book book = findBookByTitle(title);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            bookFileRepository.saveBooks(books);
            logger.info("Book borrowed successfully!");
        } else logger.warning("Book not found or already borrowed");
    }

    public void returnBookByID(int id) {
        Book book = findBookByID(id);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            bookFileRepository.saveBooks(books);
            logger.info("Book returned successfully!");
        } else logger.warning("Book not found or it wasn't borrowed.");
    }

    public void returnBookByTitle(String title) {
        Book book = findBookByTitle(title);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            bookFileRepository.saveBooks(books);
            logger.info("Book returned successfully!");
        } else logger.warning("Book not found or it wasn't borrowed.");
    }

    public List<Book> listBooks() {
        return books.stream()
                .filter(book -> !book.isDeleted())
                .collect(Collectors.toList());
    }

    public void shutdown() {
        bookFileRepository.saveBooks(books);
    }
}
