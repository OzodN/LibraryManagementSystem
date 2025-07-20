package repository;

import model.User;
import model.Role;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class UserFileReposiroty {
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
                Role role = Role.valueOf(parts[2].toUpperCase());

                users.add(new User(username, passwordHash, role));
            }
        } catch (IOException e) {
            System.out.println("Failed to load users: " + e.getMessage());
        }
        return users;
    }

    public void saveUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                bw.write(user.getUsername() +
                        "<<N>>" + user.getPasswordHash() +
                        "<<N>>" + user.getRole().name());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
