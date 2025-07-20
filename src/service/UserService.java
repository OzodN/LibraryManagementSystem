/*
 * Copyright © 2025 Your Name
 *
 * This code is licensed under the MIT License.
 * See the LICENSE.txt file for details.
 */

package service;

import model.Role;
import model.User;
import repository.UserFileReposiroty;
import util.HashUtil;

import java.util.List;

public class UserService {
    private static UserService instance;
    private final List<User> users;
    private final UserFileReposiroty userRepo = new UserFileReposiroty();

    private UserService() {
        this.users = userRepo.loadUsers();
    }

    public static UserService getInstance() {
        if (instance == null) instance = new UserService();
        return instance;
    }

    public User login(String username, String password) {
        String hash = HashUtil.md5(password);
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username) && u.getPasswordHash().equals(hash)) {
                return u;
            }
        }
        return null;
    }

    public boolean register(String username, String password, Role role) {
        if (users.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username))) {
            return false;
        }
        String hash = HashUtil.md5(password);
        User newUser = new User(username, hash, role);
        users.add(newUser);
        userRepo.saveUsers(users);
        return true;
    }
}
