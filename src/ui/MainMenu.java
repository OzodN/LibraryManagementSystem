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
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice){
                case 1 -> register();
                case 2 -> login();
                case 0 -> {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void register(){
        System.out.print("Role (ADMIN/USER): ");
        String roleStr = sc.nextLine();
        if (roleStr.equals("0")) return;
        Role role = roleStr.equalsIgnoreCase("ADMIN") ? Role.ADMIN : Role.USER;

        System.out.print("Enter username: ");
        String username = sc.nextLine();
        if (username.equals("0")) return;
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        if (password.equals("0")) return;

        boolean success = authService.register(username, password, role);

        if (success) {
            System.out.println("User registered successfully!" +
                    "\n-----------------------------" +
                    "\n1. Login" +
                    "\n0. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> login();
                case 0 -> {
                    System.exit(0);
                }
            }
        }
        else {
            System.out.println("Username is already exists!");
            return;
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        if (username.equals("0")) return;
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        if (password.equals("0")) return;

        User user = authService.login(username, password);
        if (user != null) {
            System.out.println("Welcome " + user.getUsername() + "!\n" +
                    "Role: " + user.getRole());
            if (user.getRole().equals(Role.ADMIN)) adminMenu.start();
            else userMenu.start();
        }
        else {
            System.out.println("Invalid credentials.");
            return;
        }
    }
}
