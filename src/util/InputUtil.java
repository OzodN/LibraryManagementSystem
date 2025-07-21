/*
 * Copyright © 2025 Your Name
 *
 * This code is licensed under the MIT License.
 * See the LICENSE.txt file for details.
 */

package util;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner sc = new Scanner(System.in);

    public static String promptNonBlank(String promptMessage, String fieldName) {
        while (true) {
            System.out.print(promptMessage);
            String input = sc.nextLine().trim();
            if (input.equals("0")) return null;
            if (input.isBlank()) {
                System.out.println("Please, enter " + fieldName + "!");
                continue;
            }
            return input;
        }
    }

    public static int promptInt(String promptMessage) {
        while (true) {
            System.out.print(promptMessage);
            String input = sc.nextLine().trim();
            if (input.equals("0")) return 0;
            if (input.isBlank()) {
                System.out.println("Input cannot be blank. Please enter a number.");
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
            }
        }
    }

    public static void pause() {
        System.out.print("\nPress ENTER to return to the menu...");
        sc.nextLine();
    }
}
