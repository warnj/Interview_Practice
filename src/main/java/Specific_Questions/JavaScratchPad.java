package Specific_Questions;

import java.time.*;
import java.time.format.DateTimeFormatter;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.Instant;

public class JavaScratchPad {


    public static class Meal {
        private String entree;
        private double cost;

        public Meal(String entree, double cost) {
            this.entree = entree;
            this.cost = cost;
        }

        public String getEntree() {
            return entree;
        }

        public double getCost() {
            return cost;
        }

        @Override
        public String toString() {
            return entree + " meal, $" + cost;
        }
    }
    public static class DeluxeMeal extends Meal {
        private String sideDish;
        private String drink;

        public DeluxeMeal(String entree, String sideDish, String drink, double entreeCost) {
            super(entree, entreeCost + 3.0); // Deluxe meal includes a side dish and a drink for an additional cost of $3
            this.sideDish = sideDish;
            this.drink = drink;
        }

        public String getSideDish() {
            return sideDish;
        }

        public String getDrink() {
            return drink;
        }

        @Override
        public String toString() {
            return "deluxe " + super.toString();
        }
    }
    public static void main(String[] args) {
        Meal burger = new Meal("hamburger", 7.99);
        System.out.println(burger.toString());

        DeluxeMeal burritoCombo = new DeluxeMeal("burrito", "chips", "lemonade", 7.49);
        System.out.println(burritoCombo.toString());
    }

//
//
//        public static int findEndingIndex(String input) {
//            int n = input.length();
//
//            if (n < 14) {
//                // If the string is shorter than 14 characters, no sequence is possible.
//                return -1;
//            }
//
//            for (int i = 0; i <= n - 14; i++) {
//                String substring = input.substring(i, i + 14);
//                if (hasUniqueCharacters(substring)) {
//                    return i + 13; // Ending index of the sequence
//                }
//            }
//
//            return -1; // No sequence found
//        }
//
//        private static boolean hasUniqueCharacters(String str) {
//            // Check if the string has 14 unique characters
//            boolean[] charSet = new boolean[256];
//            for (char c : str.toCharArray()) {
//                if (charSet[c]) {
//                    return false; // Duplicate character found
//                }
//                charSet[c] = true;
//            }
//            return true;
//        }
//
//        public static void main(String[] args) {
////            String input = "abcaaaaaaaaaaa"; // Replace this with your input string
//            String input = "aaaaaaaabcdefghijklmnop"; // Replace this with your input string
//            int endingIndex = findEndingIndex(input);
//
//            if (endingIndex != -1) {
//                System.out.println("Ending index of the sequence: " + endingIndex);
//            } else {
//                System.out.println("No sequence of 14 unique characters found.");
//            }
//        }


//    public static int findEndingIndex(String str) {
//        int length = str.length();
//        int count = 0;
//        int lastUniqueIndex = -1;
//
//        for (int i = 0; i < length; i++) {
//            char currentChar = str.charAt(i);
//
//            if (count == 0) {
//                lastUniqueIndex = i;
//            }
//
//            if (count == 14) {
//                return lastUniqueIndex;
//            }
//
//            boolean isUnique = true;
//            for (int j = 0; j < count; j++) {
//                if (currentChar == str.charAt(lastUniqueIndex + j)) {
//                    isUnique = false;
//                    break;
//                }
//            }
//
//            if (isUnique) {
//                count++;
//            }
//        }
//
//        return -1; // No sequence of 14 unique characters found
//    }

}
