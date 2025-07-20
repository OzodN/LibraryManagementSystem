package service;

import model.User;
import model.Role;
import service.UserService;
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private final UserService userService = UserService.getInstance();

    public boolean register(String username, String password, Role role) {
        return userService.register(username, password, role);
    }

    public User login(String username, String password) {
        return userService.login(username, password);
    }
}
