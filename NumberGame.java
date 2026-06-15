package CodeSoft;

import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class NumberGame {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int score = 0;
        char playAgain;

        System.out.println("=================================");
        System.out.println("      NUMBER GUESSING GAME");
        System.out.println("=================================");

        do {

            int secretNumber = random.nextInt(100) + 1;
            int attempts = 0;
            int maxAttempts = 5;
            boolean guessed = false;

            System.out.println("\nGuess a number between 1 and 100");
            System.out.println("Maximum Attempts: " + maxAttempts);

            while (attempts < maxAttempts) {

                try {

                    System.out.print("\nEnter your guess: ");
                    int guess = sc.nextInt();

                    if (guess < 1 || guess > 100) {
                        System.out.println("Enter a number between 1 and 100.");
                        continue;
                    }

                    attempts++;

                    if (guess == secretNumber) {

                        System.out.println("Congratulations! You guessed correctly.");
                        System.out.println("Attempts Used: " + attempts);

                        score++;
                        guessed = true;
                        break;
                    }

                    if (guess > secretNumber) {
                        System.out.println("Too High!");
                    } else {
                        System.out.println("Too Low!");
                    }

                    System.out.println("Attempts Left: "
                            + (maxAttempts - attempts));

                }

                catch (InputMismatchException e) {

                    System.out.println("Invalid input. Enter numbers only.");
                    sc.next();
                }
            }

            if (!guessed) {

                System.out.println("\nGame Over!");
                System.out.println("Correct Number: "
                        + secretNumber);
            }

            System.out.println("Score: " + score);

            do {
                System.out.print("\nPlay Again? (Y/N): ");
                playAgain = sc.next().charAt(0);

            } while (playAgain != 'Y' &&
                     playAgain != 'y' &&
                     playAgain != 'N' &&
                     playAgain != 'n');

        } while (playAgain == 'Y' || playAgain == 'y');

        System.out.println("\nFinal Score: " + score);
        System.out.println("Thank You For Playing!");

        sc.close();
    }
}
