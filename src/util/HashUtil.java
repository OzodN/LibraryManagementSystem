/*
 * Copyright © 2025 Nuritdinov Ozod
 *
 * This code is licensed under the MIT License.
 * See the LICENSE.txt file for details.
 */

package util;

import java.security.MessageDigest;

public class HashUtil {
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hash)
                sb.append(String.format("%02x", b));

            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5 Error: " + e.getMessage());
        }
    }
}

