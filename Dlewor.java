import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Dlewor {
    //-----------------------------------------------------------------------
    // constants to allow colored text and backgrounds
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
//-----------------------------------------------------------------------

    // creates a array of integers from 1 to 3 that represent the matching colors for the
    // guessed word
    public static int[] matchDlewor(String targetWord, String guess) {
        int[] numberColors = new int[5]; // creates the empty array

        String[] arrGuess = new String[5]; // creates an empty array for one guess with an index of 5
        String[] arrTarget = new String[5]; // creates an empty array for the targetWord  with an index of 5

        arrGuess = guess.split(""); // changes the String guess into a one word array

        arrTarget = targetWord.split(""); //changes the String targetWord into a one word array

        // Checking match conditions
        for (int i = 0; i < numberColors.length; i++) {
            // if the index i is equal to 1
            if (arrGuess[i].equals(arrTarget[i])) {
                // store 1 in the array numberColors
                numberColors[i] = 1;
                // if the index i is equal to 2
            } else if (arrGuess[i].equals(arrTarget[0]) || arrGuess[i].equals(arrTarget[1]) || arrGuess[i].equals(arrTarget[2]) || arrGuess[i].equals(arrTarget[3]) || arrGuess[i].equals(arrTarget[4])) {
                // store 1 in the array numberColors
                numberColors[i] = 2;
                // if the index i is equal to 2
            } else if (!(arrGuess[i].contains(arrTarget[i]))) {
                // store 1 in the array numberColors
                numberColors[i] = 3;
            }
        }
        return numberColors;
    }

    // creates a method where numberColors gets traversed through and returns false if
    // index i equals -1
    public static boolean foundMatch(int[] numberColors) {
        for (int i = 0; i < numberColors.length; i++) {
            // if index i equals 1 then the method returns true
            if (numberColors[i] == 1) {
                continue;
            }
            // otherwise the method returns false
            else {
                return false;
            }
        }
        return true;
    }

    public static boolean isSorted(ArrayList<String> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                return false; // If any element is greater than the next one, list is not sorted
            }
        }
        return true;
    }

    public static int binarySearch(ArrayList<String> list, int low, int high, String target) {
        if (high >= low) {
            int mid = low + (high - low) / 2;
            int compare = list.get(mid).compareTo(target);
            if (compare == 0) {
                return mid;
            } else if (compare > 0) {
                return binarySearch(list, low, mid - 1, target);
            } else {
                return binarySearch(list, mid + 1, high, target);
            }
        }
        return -1;
    }


    // creates a method that checks if our text file, which has been turned into an arrayList,
    // contains the guess of the user

    public static int linearSearch(ArrayList<String> list, int start, int end, String target) {
        // Base case: if start index is greater than end index, target not found
        if (start > end) {
            return -1;
        }

        // If the current element equals the target, return its index
        if (list.get(start).equals(target)) {
            return start + 1;
        } else if (!(list.get(start).equals(target))){
            return -1;
        }

        // Recursive call to search in the remaining list
        return linearSearch(list, start + 1, end, target);
    }


    // Creates an array that takes the numberColors array amd for each number is an array that
    // that sets the background of the guess index i to the color assigned with the color
    // of numberColors
    public static void printDelwor(String targetWord, int[] numberColors, String[] arrGuess) {
        // iterate though number colors
        for (int i = 0; i < numberColors.length; i++) {
            // if the numberColors index i equals 1, print a green background with the index i of arr guess
            if (numberColors[i] == 1) {
                System.out.print(ANSI_GREEN_BACKGROUND + ANSI_BLACK + arrGuess[i]);
                System.out.print(ANSI_RESET);
            }
            // if the numberColors index 2 equals 1, print a yellow background with the index i of arr guess
            else if (numberColors[i] == 2) {
                System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_BLACK + arrGuess[i]);
                System.out.print(ANSI_RESET);
            }
            // if the numberColors index 3 equals 1, print a white background with the index i of arr guess
            else if (numberColors[i] == 3) {
                System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + arrGuess[i]);
                System.out.print(ANSI_RESET);
            }

        }
        System.out.println(" ");
    }


    // Our main method that calls all the other methods and formats the whole program
    public static void main(String[] args) throws IOException {
        // Print welcome message
        System.out.println("Welcome to Dlewor(TM)");

        // Creates an array targetWords (that will hold the txt file)
        ArrayList<String> targetWords = new ArrayList<String>();

        // open file for reading
        FileReader f = new FileReader("/Users/jason/IdeaProjects/Wordle2/src/vocab.nytimes.sorted.txt");

        // create Scanner object for reading
        Scanner in = new Scanner(f);

        // read in text from file, one word at a time
        while (in.hasNext()) {
            String line = in.next();
            // write message to the file
            if (line.length() == 5) {
                targetWords.add(line);
            }
        }
        // close input file
        f.close();

        Random rand = new Random(); // new random

        int rand_int = rand.nextInt(targetWords.size()); // gets a random number from 0 to the targetWord size

        String targetWord = targetWords.get(rand_int); // gets a random number and finds that word at the random index

        System.out.println(" ");


        Scanner scnr = new Scanner(System.in);
        int[] numberColors = new int[5];
        int attempt = 1;

        // if the list is sorted then use binary search
        if(isSorted(targetWords))
        {
            for (attempt = 1; attempt <= 6; attempt++) {
                // allow the user to input their attempt guess
                System.out.print("Enter Word (" + attempt + "):  ");
                String guess = scnr.next();

                // if the guess is not in the directory
                if (binarySearch(targetWords, 0,  targetWords.size(), guess) == -1) {
                    // make the user input words until their guess equals a valid term
                    // while keeping them on the same attempt
                    while (!(targetWords.contains(guess))) {
                        System.out.println(" ");
                        System.out.println("Invalid Input");
                        System.out.print("Enter Word (" + attempt + "):  ");
                        guess = scnr.next();
                    }

                    // once the guess is valid then use method printDelwor
                    printDelwor(targetWord, matchDlewor(targetWord, guess), guess.split(""));

                    // update numberColors array
                    numberColors = matchDlewor(targetWord, guess);

                    // if found match is true then print the win statement
                    if (foundMatch(numberColors)) {
                        System.out.println(" ");
                        System.out.println("The word was " + targetWord);
                        System.out.println("You guessed the word in " + attempt + " attempts. Nice job");
                        attempt = 100;
                    }

                } else{
                    // if the guess is valid then use method printDelwor
                    printDelwor(targetWord, matchDlewor(targetWord, guess), guess.split(""));

                    // update numberColors array
                    numberColors = matchDlewor(targetWord, guess);

                    /*
                     * if found match is true then print the win statement
                     */
                    if ((foundMatch(numberColors))) {
                        System.out.println(" ");
                        System.out.println("The word was " + targetWord);
                        System.out.println("You guessed the word in " + attempt + " attempts. Nice job");
                        attempt = 100;
                    }
                }
            }
        }
        else{ // if the list isn't sorted
            for (attempt = 1; attempt <= 6; attempt++) {
                // allow the user to input their attempt guess
                System.out.print("Enter Word (" + attempt + "):  ");
                String guess = scnr.next();

                // if the guess is not in the directory
                if (linearSearch(targetWords, 0,  targetWords.size(), guess) == -1) {
                    // make the user input words until their guess equals a valid term
                    // while keeping them on the same attempt
                    while (!(targetWords.contains(guess))) {
                        System.out.println(" ");
                        System.out.println("Invalid Input");
                        System.out.print("Enter Word (" + attempt + "):  ");
                        guess = scnr.next();
                    }

                    // once the guess is valid then use method printDelwor
                    printDelwor(targetWord, matchDlewor(targetWord, guess), guess.split(""));

                    // update numberColors array
                    numberColors = matchDlewor(targetWord, guess);

                    // if found match is true then print the win statement
                    if (foundMatch(numberColors)) {
                        System.out.println(" ");
                        System.out.println("The word was " + targetWord);
                        System.out.println("You guessed the word in " + attempt + " attempts. Nice job");
                        attempt = 100;
                    }

                } else{
                    // if the guess is valid then use method printDelwor
                    printDelwor(targetWord, matchDlewor(targetWord, guess), guess.split(""));

                    // update numberColors array
                    numberColors = matchDlewor(targetWord, guess);

                    /*
                     * if found match is true then print the win statement
                     */
                    if ((foundMatch(numberColors))) {
                        System.out.println(" ");
                        System.out.println("The word was " + targetWord);
                        System.out.println("You guessed the word in " + attempt + " attempts. Nice job");
                        attempt = 100;
                    }
                }
            }

        }

        // until the attempt is greater than 6

        if (!(foundMatch(numberColors))) {
            System.out.println(" ");
            System.out.println("The word was " + targetWord);
            System.out.println("Better luck next time");
            attempt = 100;
        }
    }
}