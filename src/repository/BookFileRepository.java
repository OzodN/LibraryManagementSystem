/*
 * Copyright © 2025 Your Name
 *
 * This code is licensed under the MIT License.
 * See the LICENSE.txt file for details.
 */

package repository;

import model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookFileRepository {
    private static final String FILE_PATH = "src/data/books.txt";

    public List<Book> loadBook() {
        List<Book> books = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) return books;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split("<<N>>");
                if (parts.length != 5) continue;

                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                String author = parts[2];
                boolean available = Boolean.parseBoolean(parts[3]);
                boolean deleted = Boolean.parseBoolean(parts[4]);

                books.add(new Book(id, title, author, available, deleted));
            }
        } catch (IOException e) {
            System.out.println("Error reading books file: " + e.getMessage());
        }
        return books;
    }

    public void saveBooks(List<Book> books) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Book b : books) {
                br.write(b.getId() +
                        "<<N>>" + b.getTitle() +
                        "<<N>>" + b.getAuthor() +
                        "<<N>>" + b.isAvailable() +
                        "<<N>>" + b.isDeleted());
                br.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing books file: " + e.getMessage());
            e.printStackTrace();

        }
    }
}
