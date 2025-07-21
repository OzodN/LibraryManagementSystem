/*
 * Copyright © 2025 Your Name
 *
 * This code is licensed under the MIT License.
 * See the LICENSE.txt file for details.
 */

package ui;

import model.User;
import model.Role;
import service.AuthService;

import java.util.Scanner;

public class MainMenu {
    private static final AuthService authService = new AuthService();
    private static final Scanner sc = new Scanner(System.in);
    private static final AdminMenu adminMenu = new AdminMenu();
    private static final UserMenu userMenu = new UserMenu();

    public static void start(){
        while (true) {
            System.out.println("\n" +
                    "Choose your option:\n" +
                    "1. Registration\n" +
                    "2. Login\n" +
                    "0. Exit");

            String input = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                return;
            }

            switch (choice){
                case 1 -> register();
                case 2 -> login();
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void register(){
        while (true) {
            String roleStr = promptNonBlank("Role (ADMIN/USER or 0 to cancel): ", "role");
            if (roleStr == null) {
                System.out.println("Registration cancelled.");
                return;
            }

            Role role;
            if (roleStr.equalsIgnoreCase("ADMIN")) {
                role = Role.ADMIN;
            } else if (roleStr.equalsIgnoreCase("USER")) {
                role = Role.USER;
            } else {
                System.out.println("Invalid role! Must be ADMIN or USER.");
                continue;
            }

            String username = promptNonBlank("Enter username (or 0 to cancel): ", "username");
            if (username == null) {
                System.out.println("Registration cancelled.");
                return;
            }

            String password = promptNonBlank("Enter password (or 0 to cancel): ", "password");
            if (password == null) {
                System.out.println("Registration cancelled.");
                return;
            }

            boolean success = authService.register(username, password, role);
            if (success) {
                System.out.println(roleStr.toUpperCase() + " registered successfully!");
                System.out.println("-----------------------------");
                System.out.println("1. Login");
                System.out.println("0. Exit");

                String input = sc.nextLine();
                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Returning to main menu.");
                    return;
                }

                switch (choice) {
                    case 1 -> login();
                    case 0 -> {
                        System.out.println("Goodbye!");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid option!");
                }
            } else {
                System.out.println("Registration failed. Try again.");
                System.out.print("Try again? (Y/N): ");
                String again = sc.nextLine();
                if (!again.equalsIgnoreCase("Y")) {
                    System.out.println("Login cancelled.");
                    return;
                }
            }
        }
    }

    private static void login() {
        while (true) {
            String username = promptNonBlank("Enter username (or 0 to cancel): ", "username");
            if (username == null) {
                System.out.println("Login cancelled.");
                return;
            }

            String password = promptNonBlank("Enter password (or 0 to cancel): ", "password");
            if (password == null) {
                System.out.println("Login cancelled.");
                return;
            }

            User user = authService.login(username, password);
            if (user != null) {
                System.out.println("\nWelcome " + user.getUsername() +
                        "!\nRole: " + user.getRole());
                if (user.getRole().equals(Role.ADMIN)) adminMenu.start();
                else userMenu.start();
                return;
            } else {
                System.out.println("Invalid credentials.");
                System.out.print("Try again? (Y/N): ");
                String again = sc.nextLine();
                if (!again.equalsIgnoreCase("Y")) {
                    System.out.println("Login cancelled.");
                    return;
                }
            }
        }
    }

    private static String promptNonBlank(String promptMessage, String fieldName) {
        while (true) {
            System.out.print(promptMessage);
            String input = sc.nextLine().trim();
            if (input.equals("0")) return null;
            if (input.isBlank()) {
                System.out.println("Please, enter your " + fieldName + "!");
                continue;
            }
            return input;
        }
    }
}
