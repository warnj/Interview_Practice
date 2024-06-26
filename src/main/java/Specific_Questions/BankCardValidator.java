package Specific_Questions;

import java.time.LocalDate;

public class BankCardValidator {
    private String cardNumber;
    private String expiryDate;

    public BankCardValidator(String cardNumber, String expiryDate) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
    }

    public boolean validateCard() {
        if (!isValidLuhn()) {
            return false;
        }

        try {
            String[] expiryDateParts = this.expiryDate.split("-");
            int expiryYear = Integer.parseInt(expiryDateParts[0]);
            int expiryMonth = Integer.parseInt(expiryDateParts[1]);
            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();
            int currentMonth = currentDate.getMonthValue();
            if (expiryYear < currentYear || (expiryYear == currentYear && expiryMonth < currentMonth)) {
                return false;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return false;
        }

        return true;
    }

    public boolean isValidLuhn() {
        String cardNumber = this.cardNumber;
        int[] cardDigits = new int[cardNumber.length()];
        for (int i = 0; i < cardNumber.length(); i++) {
            cardDigits[i] = Character.getNumericValue(cardNumber.charAt(i));
        }
        int checkDigit = cardDigits[cardDigits.length - 1];
        for (int i = cardDigits.length - 2; i >= 0; i -= 2) {
            int digit = cardDigits[i];
            digit *= 2;
            if (digit > 9) {
                digit -= 9;
            }
            cardDigits[i] = digit;
        }

        int sum = 0;
        for (int digit : cardDigits) {
            sum += digit;
        }
        return sum % 10 == 0;
    }

    public static void main(String[] args) {
        BankCardValidator validator = new BankCardValidator("4111111111111111", "2024-12");
        boolean isValid = validator.validateCard();
        System.out.println("Is Valid: " + isValid);
    }
}