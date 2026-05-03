/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package waste.and.labour;
import java.util.*;

/*
 * ─────────────────────────────────────────────────────────────────────────────
 *  Validator.java  —  Data Validation Utility Class
 * ─────────────────────────────────────────────────────────────────────────────
 *
 *  OOP CONCEPT USED: ABSTRACTION
 *  ► We "abstract" (hide) all validation logic into simple method names.
 *  ► Other classes just call Validator.isEmptyOrNull(text) instead of
 *    writing the same if-check again and again.
 *
 *  WHY THIS CLASS?
 *  ► Without a Validator class, every form (Waste, Wage, Labour, Raw…)
 *    would repeat the same checks. That is bad design.
 *  ► Putting them here means: fix a bug once → it fixes everywhere.
 *
 *  All methods are "static" so you don't need to create an object:
 *      Validator.isEmptyOrNull("hello")   ← works directly
 *
 * @author SMT Dev Team
 */
public class Validator {

    // ── 1. Check if a text field is empty or null ─────────────────────────
    /**
     * Returns true if the given text is null, empty, or only whitespace.
     * Use this before saving any field to the database.
     *
     * Example:
     *     if (Validator.isEmptyOrNull(tfName.getText())) {
     *         JOptionPane.showMessageDialog(null, "Name is required!");
     *     }
     */
    public static boolean isEmptyOrNull(String text) {
        // trim() removes leading/trailing spaces before checking
        return text == null || text.trim().isEmpty();
    }

    // ── 2. Check if a string is a valid positive whole number ─────────────
    /**
     * Returns true if the text is a whole number greater than zero.
     * Use this for Quantity, Days, Wage, Ring ID, etc.
     *
     * Example:
     *     if (!Validator.isPositiveInt("50")) → false  (50 is valid)
     *     if (!Validator.isPositiveInt("abc")) → true  (invalid)
     */
    public static boolean isPositiveInt(String text) {
        try {
            return Integer.parseInt(text.trim()) > 0;
        } catch (NumberFormatException e) {
            return false; // text is not a number at all
        }
    }

    // ── 3. Check if a string is a valid positive decimal number ──────────
    /**
     * Returns true if the text is a decimal number greater than zero.
     * Use this for prices, rates, amounts (e.g. 12.50).
     */
    public static boolean isPositiveDouble(String text) {
        try {
            return Double.parseDouble(text.trim()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // ── 4. Check if a date string is in the correct YYYY-MM-DD format ────
    /**
     * Returns true if the text looks like a valid date (YYYY-MM-DD).
     * Java's LocalDate.parse() will throw an exception if the format is wrong.
     *
     * Example:
     *     Validator.isValidDate("2024-06-15") → true
     *     Validator.isValidDate("15-06-2024") → false
     */
    public static boolean isValidDate(String text) {
        try {
            java.time.LocalDate.parse(text.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ── 5. Check if a username meets the minimum length ───────────────────
    /**
     * Returns true if the username has at least 4 characters.
     */
    public static boolean isValidUsername(String username) {
        return username != null && username.trim().length() >= 4;
    }

    // ── 6. Check if a password meets all rules ────────────────────────────
    /**
     * Returns true if the password:
     *   - Is at least 6 characters long
     *   - Contains at least one letter
     *   - Contains at least one digit
     *
     * The .matches() method uses a "regex" (pattern) to check for letters/digits.
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) return false;
        boolean hasLetter = password.matches(".*[A-Za-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        return hasLetter && hasDigit;
    }

    // ── 7. Check that "used" does not exceed "total" ──────────────────────
    /**
     * Returns true if usedAmount is less than or equal to totalAmount.
     * Use this in the Waste module to prevent negative waste values.
     */
    public static boolean isUsedWithinTotal(int usedAmount, int totalAmount) {
        return usedAmount <= totalAmount;
    }
}

