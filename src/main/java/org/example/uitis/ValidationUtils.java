package org.example.uitis;

import java.util.Scanner;

public class ValidationUtils {
    public static int validateNumber() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a number: ");
            String input = sc.nextLine();

            if (input.isBlank()) {
                System.out.println("Number cannot be blank");
                continue;
            }

            if (!input.matches("-?[0-9]+")) {
                System.out.println("Invalid input. Please enter a valid number (no spaces or letters).");
                continue;
            }

            try {
                int a = Integer.parseInt(input);
                if (a <= 0) {
                    System.out.println("Number cannot be less than or equal to 0");
                } else if (a >= 10000000) {
                    System.out.println("Number cannot be greater than or equal to 10000000");
                } else {
                    return a;
                }
            } catch (NumberFormatException e) {
                System.out.println("Number is out of valid integer range.");
            }
        }
    }

}
