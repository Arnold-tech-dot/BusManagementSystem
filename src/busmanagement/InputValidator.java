package busmanagement;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[0-9]{7,15}$");
    private static final Pattern ID_PATTERN =
            Pattern.compile("^[A-Za-z]{1,3}[0-9]{3,6}$");

    private Set<String> registeredIDs = new HashSet<>();

    // ── Empty / null checks ──────────────────────────────────────────────────

    public boolean isNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("[Validation ERROR] " + fieldName + " cannot be empty.");
            return false;
        }
        return true;
    }

    // ── Numeric validations ──────────────────────────────────────────────────

    public boolean isPositiveInt(String value, String fieldName) {
        if (!isNotEmpty(value, fieldName)) return false;
        try {
            int num = Integer.parseInt(value.trim());
            if (num <= 0) {
                System.out.println("[Validation ERROR] " + fieldName + " must be a positive number. Got: " + num);
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("[Validation ERROR] " + fieldName + " must be a number. Got: '" + value + "'");
            return false;
        }
    }

    public boolean isPositiveDouble(String value, String fieldName) {
        if (!isNotEmpty(value, fieldName)) return false;
        try {
            double num = Double.parseDouble(value.trim());
            if (num <= 0) {
                System.out.println("[Validation ERROR] " + fieldName + " must be a positive value. Got: " + num);
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("[Validation ERROR] " + fieldName + " must be a decimal number. Got: '" + value + "'");
            return false;
        }
    }

    public boolean isNonNegativeInt(String value, String fieldName) {
        if (!isNotEmpty(value, fieldName)) return false;
        try {
            int num = Integer.parseInt(value.trim());
            if (num < 0) {
                System.out.println("[Validation ERROR] " + fieldName + " cannot be negative. Got: " + num);
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("[Validation ERROR] " + fieldName + " must be a whole number. Got: '" + value + "'");
            return false;
        }
    }

    // ── Format validations ───────────────────────────────────────────────────

    public boolean isValidEmail(String email) {
        if (!isNotEmpty(email, "Email")) return false;
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            System.out.println("[Validation ERROR] Invalid email format: '" + email + "'. Expected: user@domain.com");
            return false;
        }
        return true;
    }

    public boolean isValidPhone(String phone) {
        if (!isNotEmpty(phone, "Phone number")) return false;
        String cleaned = phone.trim().replaceAll("\\s+", "");
        if (!PHONE_PATTERN.matcher(cleaned).matches()) {
            System.out.println("[Validation ERROR] Invalid phone format: '" + phone + "'. Expected: +1234567890 or 0712345678");
            return false;
        }
        return true;
    }

    public boolean isValidID(String id, String fieldName) {
        if (!isNotEmpty(id, fieldName)) return false;
        if (!ID_PATTERN.matcher(id.trim()).matches()) {
            System.out.println("[Validation ERROR] Invalid " + fieldName + " format: '" + id +
                    "'. Expected format: Letters followed by digits (e.g., P001, BUS123)");
            return false;
        }
        return true;
    }

    // ── Duplicate ID check ───────────────────────────────────────────────────

    public boolean isUniqueID(String id, String fieldName) {
        if (registeredIDs.contains(id.trim().toUpperCase())) {
            System.out.println("[Validation ERROR] Duplicate " + fieldName + ": '" + id + "' already exists.");
            return false;
        }
        return true;
    }

    public void registerID(String id) {
        registeredIDs.add(id.trim().toUpperCase());
    }

    // ── Bus type validation ──────────────────────────────────────────────────

    public boolean isValidBusType(String busType) {
        if (!isNotEmpty(busType, "Bus Type")) return false;
        String[] validTypes = {"CITY", "EXPRESS", "LUXURY", "SCHOOL", "TOURIST", "ELECTRIC"};
        for (String t : validTypes) {
            if (t.equalsIgnoreCase(busType.trim())) return true;
        }
        System.out.println("[Validation ERROR] Invalid bus type: '" + busType + "'.");
        System.out.println("  Valid types: CITY, EXPRESS, LUXURY, SCHOOL, TOURIST, ELECTRIC");
        return false;
    }

    // ── Seat number validation ───────────────────────────────────────────────

    public boolean isValidSeatNumber(String value, int capacity, String fieldName) {
        if (!isPositiveInt(value, fieldName)) return false;
        int seat = Integer.parseInt(value.trim());
        if (seat > capacity) {
            System.out.println("[Validation ERROR] Seat number " + seat +
                    " exceeds bus capacity of " + capacity + ".");
            return false;
        }
        return true;
    }

    // ── Payment method validation ────────────────────────────────────────────

    public boolean isValidPaymentMethod(String method) {
        if (!isNotEmpty(method, "Payment Method")) return false;
        String[] validMethods = {"CASH", "CARD", "MOBILE"};
        for (String m : validMethods) {
            if (m.equalsIgnoreCase(method.trim())) return true;
        }
        System.out.println("[Validation ERROR] Invalid payment method: '" + method + "'.");
        System.out.println("  Valid methods: CASH, CARD, MOBILE");
        return false;
    }
}
