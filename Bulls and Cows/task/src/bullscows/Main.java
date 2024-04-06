package bullscows;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        String input = scanner.nextLine();
        if (!input.matches("[0-9]+")) {
            System.out.println("Error: \"" + input + "\" isn't a valid number.");
            return;
        }

        int length = Integer.parseInt(input);
        StringBuilder answer = new StringBuilder();
        System.out.println("Input the number of possible symbols in the code:");
        int symbols = scanner.nextInt();

        if (symbols < length || length < 1) {
            System.out.println("Error: it's not possible to generate a code with a length of " + length
                    + " with " + symbols + " unique symbols.");
            return;
        } else if (symbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        List<String> chars = new ArrayList<>(List.of("0", "1", "2", "3", "4", "5", "6",
                "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
                "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));

        List<String> newChars = new ArrayList<>(chars).subList(0, symbols);

        if (length < 11) {
            answer = new StringBuilder(generateNumber(length, symbols, newChars));
            System.out.println(answer);
            System.out.print("The secret is prepared: ");
            for (int i = 0; i < length; i++) {
                System.out.print("*");
            }

            System.out.print(" (0-9");
            if (symbols > 10) {
                System.out.print(", a-" + chars.get(symbols - 1));
            }
            System.out.print(").\n");

            System.out.println("Okay, let's start a game!");
        } else {
            System.out.println("Error: can't generate a secret number with a length of "
                    + length + " because there aren't enough unique digits.");
        }

        boolean isCorrect = false;
        int turn = 0;
        while (!isCorrect) {
            System.out.println("Turn " + (turn + 1) + ":");
            String guess = scanner.next();
            int bull = checkBull(String.valueOf(answer), guess);
            int cow = checkCow(String.valueOf(answer), guess);
            checkAnswer(bull, cow, answer);
            if (bull == length) {
                isCorrect = true;
                System.out.println("Congratulations! You guessed the secret code.");
            } else {
                turn++;
            }
        }
    }

    public static String generateNumber(int length, int symbols, List<String> newChars) {
        HashSet<String> code = new HashSet<>();

        while (code.size() < length) {
            String random = newChars.get((int) (Math.random() * newChars.size()));
            code.add(random);
            newChars.remove(random);
        }

        ArrayList<String> number = new ArrayList<>(code);

        do {
            Collections.shuffle(number);
        } while (number.get(0).equals("0"));

        return code.toString().replace("[", "").replace("]",
                "").replace(",", "").replace(" ", "");
    }

    public static int checkBull(String answer, String guess) {
        int bull = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                bull++;
            }
        }
        return bull;
    }

    public static int checkCow(String answer, String guess) {
        int cow = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                continue;
            }
            for (int j = 0; j < answer.length(); j++) {
                if (guess.charAt(i) == answer.charAt(j)) {
                    cow++;
                    break;
                }
            }
        }
        return cow;
    }

    public static void checkAnswer(int bull, int cow, StringBuilder answer) {
        if (bull == 0 && cow == 0) {
            System.out.print("Grade: None. ");
        } else if (bull == 0) {
            System.out.print("Grade: " + cow + " cow(s).\n");
        } else if (cow == 0) {
            System.out.print("Grade: " + bull + " bull(s).\n");
        } else {
            System.out.print("Grade: " + bull + " bull(s) and " + cow + " cow(s).\n");
        }
    }
}
