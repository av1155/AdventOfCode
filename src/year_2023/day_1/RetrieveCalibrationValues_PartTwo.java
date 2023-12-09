package year_2023.day_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RetrieveCalibrationValues_PartTwo
{
    public static void main(String[] args) throws FileNotFoundException
    {
        File inputFile = new File("/Users/andreaventi/Developer/AdventOfCode/src/year_2023/day_1/input.txt");
        if (!inputFile.exists())
        {
            System.out.println("The file does not exist.");
            System.exit(0);
        }

        Scanner fileReader = new Scanner(inputFile);

        // Two-dimensional array of Strings to identify the numbers in the text file, and their corresponding digits
        String[][] letterNumbers = {
            {"1", "one"  }, // letterNumbers[0][0] = "1", letterNumbers[0][1] = "one"
            {"2", "two"  }, // letterNumbers[1][0] = "2", letterNumbers[1][1] = "two"
            {"3", "three"}, // letterNumbers[2][0] = "3", letterNumbers[2][1] = "three"
            {"4", "four" }, // letterNumbers[3][0] = "4", letterNumbers[3][1] = "four"
            {"5", "five" }, // letterNumbers[4][0] = "5", letterNumbers[4][1] = "five"
            {"6", "six"  }, // letterNumbers[5][0] = "6", letterNumbers[5][1] = "six"
            {"7", "seven"}, // letterNumbers[6][0] = "7", letterNumbers[6][1] = "seven"
            {"8", "eight"}, // letterNumbers[7][0] = "8", letterNumbers[7][1] = "eight"
            {"9", "nine" }, // letterNumbers[8][0] = "9", letterNumbers[8][1] = "nine"
        };

        int sum = 0, lineCounter = 0;

        while (fileReader.hasNextLine())
        {
            String line = fileReader.nextLine();
            lineCounter++;

            // Print the line number and the line
            System.out.printf("Line %d: %s\n", lineCounter, line);

            String firstDigit = "", lastDigit = "";
            String currentDigit = "";
            boolean foundFirstDigit = false;

            // Iterate over each character and check for digits and number words
            for (int index = 0; index < line.length(); index++)
            {
                currentDigit = "";

                // Check direct digit
                if (Character.isDigit(line.charAt(index)))
                {
                    // If the current character is a digit, set the currentDigit to the digit
                    currentDigit = String.valueOf(line.charAt(index));
                }
                else
                {
                    // Check for number words
                    for (int j = 0; j < letterNumbers.length; j++)
                    {
                        // Iterate over each number word and check if the line starts with it at the current index
                        String numericWord = letterNumbers[j][1];
                        String correspondingDigit = letterNumbers[j][0];

                        // If the line starts with the number word, set the currentDigit to the corresponding digit
                        if (line.startsWith(numericWord, index))
                        {
                            // Set the current digit to the corresponding digit
                            currentDigit = correspondingDigit;

                            // Skip the length of the word
                            index += numericWord.length() - 1;

                            break;
                        }
                    }
                }

                // If the current digit is not empty, set the first digit to the current digit
                if (!currentDigit.isEmpty())
                {
                    // If the first digit has not been found, set the first digit to the current digit
                    if (!foundFirstDigit)
                    {
                        firstDigit = currentDigit;
                        foundFirstDigit = true;
                    }

                    lastDigit = currentDigit;

                    System.out.printf("Current digit: %s\n", currentDigit);
                }
            }

            // If the first and last digits are not empty, concatenate them and add them to the sum
            if (!firstDigit.isEmpty() && !lastDigit.isEmpty())
            {
                int concatenatedNumber = Integer.parseInt(firstDigit + lastDigit);
                sum += concatenatedNumber;

                System.out.printf("Line %d: %d\n", lineCounter, concatenatedNumber);
            }

            System.out.println();
        }

        fileReader.close();

        System.out.printf("The calibration value is: %d\n", sum);
    }
}
