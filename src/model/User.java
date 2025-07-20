package model;

public class User {
    private String username;
    private String passwordHash;
    private Role role;

    public User(String username, String passwordHash, Role role) {
        this.passwordHash = passwordHash;
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Username:" + username +
                "PasswordHash='" + passwordHash +
                "Role:" + role;
    }
}
