/*
 * Copyright © 2025 Your Name
 *
 * This code is licensed under the MIT License.
 * See the LICENSE.txt file for details.
 */

package model;

public class Book {
    private int id; //id of the book
    private String title; //name of the book
    private String author; // author of the book
    private boolean isAvailable;// book's existing
    private boolean isDeleted;

    public Book(int id, String title, String author) {
        this(id, title, author, true , false);
    }

    public Book(int id, String title, String author, boolean isAvailable, boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Book Info:" +
                "\n ID: " + id +
                "\n Title: " + title +
                "\n author: " + author +
                "\n Available: " + (isAvailable ? "Yes" : "No") +
                "\n----------------------";
    }
}
