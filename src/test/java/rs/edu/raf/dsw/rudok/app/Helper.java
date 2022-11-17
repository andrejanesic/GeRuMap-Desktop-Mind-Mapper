package rs.edu.raf.dsw.rudok.app;

import java.util.Random;

/**
 * Helper class for conducting tests.
 */
public class Helper {

    /**
     * Creates a random string of given length with the given input charset.
     *
     * @param length  String length.
     * @param charset Chars to use when constructing the string.
     * @return Random string.
     */
    public static String randString(int length, char[] charset) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while (length-- > 0) {
            sb.append(charset[r.nextInt(charset.length)]);
        }
        return sb.toString();
    }

    /**
     * Creates a random string of given length with the charset [a-zA-Z0-9-].
     *
     * @param length String length.
     * @return Random string.
     */
    public static String randString(int length) {
        return Helper.randString(
                length,
                new char[]{
                        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                        't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4',
                        '5', '6', '7', '8', '9', '-'
                }
        );
    }

    /**
     * Creates a random string of up to 16 characters.
     *
     * @return Random string.
     */
    public static String randString() {
        return Helper.randString(
                new Random().nextInt(16) + 1
        );
    }
}
