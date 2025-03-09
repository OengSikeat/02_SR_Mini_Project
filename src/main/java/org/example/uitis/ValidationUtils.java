package org.example.uitis;
import java.util.Scanner;

public class ValidationUtils {
    static Scanner sc = new Scanner(System.in);
    public static String validateName() {
        while (true) {
            System.out.print("Enter Product Name: ");
            String input = sc.nextLine();

            if (input.isBlank()) {
                System.out.println("\nNumber cannot be blank");
                continue;
            }
            if (!input.matches("^[a-zA-Z0-9 ]+$")) {
                System.out.println("\nInvalid input.Letter Only.");
                continue;
            }
            return input;
        }

    }

    public static double validateUnitPrice() {
        while (true) {
            System.out.print("Enter Product Price: ");
            String input = sc.nextLine();

            if (input.isBlank()) {
                System.out.println("\nNumber cannot be blank");
                continue;
            }
            if (!input.matches("-?[0-9]+\\.?[0-9]{0,2}")) {
                System.out.println("\nInvalid input. Allow only number.");
                continue;
            }
            try {
                double a = Double.parseDouble(input);
                if (a <= 0) {
                    System.out.println("\nNumber cannot be less than or equal to 0");
                } else if (a >= 10000000) {
                    System.out.println("\nNumber cannot be greater than or equal to 10000000");
                } else {
                    return a;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nNumber is out of valid integer range.");
            }
        }
    }

    public static int validateQuantity() {
        while (true) {
            System.out.print("Enter Product Quantity: ");
            String input = sc.nextLine();

            if (input.isBlank()) {
                System.out.println("\nNumber cannot be blank");
                continue;
            }
            if (!input.matches("-?[0-9]+")) {
                System.out.println("\nInvalid input. Please enter a valid number (no spaces or letters).");
                continue;
            }
            try {
                int a = Integer.parseInt(
                        input);
                if (a <= 0) {
                    System.out.println("\nNumber cannot be less than or equal to 0");
                } else if (a >= 10000000) {
                    System.out.println("\nNumber cannot be greater than or equal to 10000000");
                } else {
                    return a;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nNumber is out of valid integer range.");
            }
        }
    }

    public static String validateMainOption() {
        while (true) {
            System.out.print("=> Choose your option() : ");
            String input = sc.nextLine().toLowerCase().trim();

            if (input.isBlank()) {
                System.out.println("\nOption cannot be blank");
                continue;
            }

            if (input.matches("^[a-zA-Z]+$")) {
                return input;
            } else {
                System.out.println("\nInvalid option.");
            }
        }
    }

    public static int validateUpdate() {
        while (true) {
            System.out.println("\n=> Choose an option: ");
            String input = sc.nextLine().trim();

            if (input.isBlank()) {
                System.out.println("\nOption cannot be blank.");
                continue; // Ask again
            }

            if (input.matches("^[1-5]$")) {
                return Integer.parseInt(input);
            } else {
                System.out.println("\nInvalid input. Please enter a number between 1 and 5.");
            }
        }
    }

    public static int validateID(){
        while (true) {
            System.out.print("Enter ID: ");
            String input = sc.nextLine();
            if (input.isBlank()) {
                System.out.println("\nID cannot be blank");
                continue;
            }
            if (!input.matches("-?[0-9]+")) {
                System.out.println("\nInvalid input. Please enter a valid ID (no spaces or letters).");
                continue;
            }
            try {
                int a = Integer.parseInt(
                        input);
                if (a <= 0) {
                    System.out.println("\nID cannot be less than or equal to 0");
                } else if (a >= 10000000) {
                    System.out.println("\nID cannot be greater than or equal to 10000000");
                } else {
                    return a;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nID is out of valid integer range.");
            }
        }
    }
    public static int validateNumber(){
        while (true) {

            String input = sc.nextLine();
            if (input.isBlank()) {
                System.out.println("\nID cannot be blank");
                continue;
            }
            if (!input.matches("-?[0-9]+")) {
                System.out.println("\nInvalid input. Please enter a valid ID (no spaces or letters).");
                continue;
            }
            try {
                int a = Integer.parseInt(
                        input);
                if (a <= 0) {
                    System.out.println("\nID cannot be less than or equal to 0");
                } else if (a >= 10000000) {
                    System.out.println("\nID cannot be greater than or equal to 10000000");
                } else {
                    return a;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nID is out of valid integer range.");
            }
        }
    }
}