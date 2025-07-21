/*
 * Copyright © 2025 Your Name
 *
 * This code is licensed under the MIT License.
 * See the LICENSE.txt file for details.
 */

package repository;

import com.sun.nio.sctp.IllegalReceiveException;
import model.User;
import model.Role;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class UserFileRepository {
    private final String FILE_PATH = "src/data/users.txt";

    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) return users;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("<<N>>");
                if (parts.length != 3) continue;

                String username = parts[0];
                String passwordHash = parts[1];
                String roleStr = parts[2].toUpperCase();

                try {
                    Role role = Role.valueOf(roleStr);
                    users.add(new User(username, passwordHash, role));
                } catch (IllegalReceiveException e) {
                    System.out.println("Skipping line due to invalid role: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public boolean saveUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                bw.write(user.getUsername() +
                        "<<N>>" + user.getPasswordHash() +
                        "<<N>>" + user.getRole().name());
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
