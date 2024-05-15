import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Puzzle {

    private static Map<Character, Integer> letterToDigit = new HashMap<>();
    private static boolean[] usedDigits = new boolean[10];
    private static int solutionCount = 0;

    public static void main(String[] args) {        
        String[][] puzzles = { args };

        for (String[] puzzle : puzzles) {
            solvePuzzle(puzzle);
        }
    }

    private static void solvePuzzle(String[] puzzle) {
        if (puzzle.length != 3) {
            System.out.println("Please provide three words as input.");
            return;
        }

        for (String word : puzzle) {
            if (!word.matches("[A-Z]+") || word.length() > 18) {
                System.out.println("Invalid word: \"" + word
                        + "\". Each word must consist of uppercase letters and be no longer than 18 letters.");
                return;
            }
        }

        String firstAddend = puzzle[0];
        String secondAddend = puzzle[1];
        String sum = puzzle[2];

        long startTime = System.nanoTime();

        // Reset state for new puzzle
        letterToDigit.clear();
        usedDigits = new boolean[10];
        solutionCount = 0;

        solve(firstAddend, secondAddend, sum, 0);
        
        System.out.println("Puzzle: " + firstAddend + " + " + secondAddend + " = " + sum);
        if (solutionCount > 0) {
            System.out.println("Number of solutions: " + solutionCount);
        } else {
            System.out.println("No solution exists for the given puzzle.");
        }

        long endTime = System.nanoTime();
        long totalTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Execution time in milliseconds: " + totalTime);
    }

    private static boolean solve(String firstAddend, String secondAddend, String sum, int index) {
        if (index == firstAddend.length() + secondAddend.length() + sum.length()) {
            if (isValidSolution(firstAddend, secondAddend, sum)) {
                solutionCount++;
                printSolution();
            }
            // Return false to indicate that the search should continue
            return false;
        }

        String allWords = firstAddend + secondAddend + sum;
        char currentChar = allWords.charAt(index);

        if (letterToDigit.containsKey(currentChar)) {
            // Continue with the next character if the current one is already assigned
            return solve(firstAddend, secondAddend, sum, index + 1);
        }

        for (int digit = 0; digit <= 9; digit++) {
            if (!usedDigits[digit] && (digit != 0 || !isLeadingZero(currentChar, firstAddend, secondAddend, sum))) {
                letterToDigit.put(currentChar, digit);
                usedDigits[digit] = true;

                // Continue the search, do not terminate after one solution
                solve(firstAddend, secondAddend, sum, index + 1);

                letterToDigit.remove(currentChar);
                usedDigits[digit] = false;
            }
        }
        // Return false to indicate that the search should continue
        return false;
    }

    private static boolean isValidSolution(String firstAddend, String secondAddend, String sum) {
        return wordToNumber(firstAddend) + wordToNumber(secondAddend) == wordToNumber(sum);
    }

    private static long wordToNumber(String word) {
        long number = 0;
        for (char letter : word.toCharArray()) {
            number = number * 10 + letterToDigit.get(letter);
        }
        return number;
    }

    private static void printSolution() {
        letterToDigit.forEach((key, value) -> System.out.println(key + " = " + value));
        System.out.println();
    }

    private static boolean isLeadingZero(char ch, String... words) {
        for (String word : words) {
            if (word.charAt(0) == ch) {
                return true;
            }
        }
        return false;
    }
}