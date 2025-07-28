/*
 * Copyright © 2025 Nuritdinov Ozod
 *
 * This code is licensed under the MIT License.
 * See the LICENSE.txt file for details.
 */

package service;

import model.Role;
import model.User;
import repository.UserFileRepository;
import util.HashUtil;

import java.util.List;
import java.util.logging.Logger;

public class UserService {
    private static UserService instance;
    private final List<User> users;
    private final UserFileRepository userRepo = new UserFileRepository();
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    private UserService() {
        this.users = userRepo.loadUsers();
    }

    public static synchronized UserService getInstance() {
        if (instance == null) instance = new UserService();
        return instance;
    }

    public User login(String username, String password) {

        if (username == null || username.trim().isBlank()
                || password == null|| password.isBlank() ) {
            logger.warning("Logging In failed: empty username or password");
            return null;
        }

        String trimmedUsername = username.trim();

        String hash = HashUtil.md5(password);
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(trimmedUsername)
                    && user.getPasswordHash().equals(hash)) {
                logger.info("User logged in: " + trimmedUsername);
                return user;
            }
        }

        logger.warning("Login failed for user: " + trimmedUsername);
        return null;
    }

    public boolean register(String username, String password, Role role) {
        if (username == null || username.trim().isBlank() ||
                password == null || password.isBlank() ||
                role == null || role.name().trim().isBlank()) {
            logger.warning("Registration failed: empty username/password");
            return false;
        }

        String trimmedUsername = username.trim();

        if (password.length() < 6) {
            logger.warning("Registration failed: password must be > 6 characters");
            return false;
        }

        if (users.stream().anyMatch(u ->
                u.getUsername().equalsIgnoreCase(trimmedUsername))) {
                logger.warning("Registration failed: username already exists.");
            return false;
        }

        String hash = HashUtil.md5(password);
        User newUser = new User(trimmedUsername, hash, role);
        users.add(newUser);
        return userRepo.saveUsers(users);
    }
}
