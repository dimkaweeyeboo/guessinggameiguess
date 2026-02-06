import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class Example3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ThreadLocalRandom rand = ThreadLocalRandom.current();

        System.out.println("Welcome to the Guessing Game!");
        System.out.println("I'm thinking of a number between 1 and 100.");

        // Optional test mode: pass `--target N` to force the secret number (useful for automated tests)
        int forcedTarget = -1;
        if (args.length >= 2 && ("--target".equals(args[0]) || "-t".equals(args[0]))) {
            try {
                int t = Integer.parseInt(args[1]);
                if (t >= 1 && t <= 100) {
                    forcedTarget = t;
                }
            } catch (NumberFormatException ignored) {
            }
        }

        boolean play = true;
        while (play) {
            int target = (forcedTarget > 0) ? forcedTarget : rand.nextInt(1, 101); // 1..100 inclusive
            int attempts = 0;

            System.out.println("I have a number. Try to guess it.");
            while (true) {
                System.out.print("Enter your guess: ");
                String line = sc.nextLine();
                int guess;
                try {
                    guess = Integer.parseInt(line.trim());
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid integer.");
                    continue;
                }

                if (guess < 1 || guess > 100) {
                    System.out.println("Please guess a number between 1 and 100.");
                    continue;
                }

                attempts++;
                if (guess < target) {
                    System.out.println("Too low.");
                } else if (guess > target) {
                    System.out.println("Too high.");
                } else {
                    System.out.println("Correct! You guessed the number in " + attempts + " attempts.");
                    break;
                }
            }

            System.out.print("Play again? (y/n): ");
            String resp = sc.nextLine().trim().toLowerCase();
            if (!resp.equals("y") && !resp.equals("yes")) {
                play = false;
            }

            // If we were running in forced-target test mode, only play once.
            if (forcedTarget > 0) {
                play = false;
            }
        }

        System.out.println("Thanks for playing!");
        sc.close();
    }
}
