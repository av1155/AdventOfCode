package year_2023.day_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RetrieveCalibrationValues_PartOne
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

        int sum = 0, lineCounter = 0;

        // Loop through each line in the text file
        while (fileReader.hasNextLine())
        {
            // Store the line in a variable 'line' and increment the line counter
            String line = fileReader.nextLine();
            lineCounter++;

            // Loop through each character in the line, and create two variables to store the first and last digit
            String firstDigit = "", lastDigit = "";
            for (int i = 0; i < line.length(); i++)
            {
                // Check if the character is a digit
                if (Character.isDigit(line.charAt(i))) // Character.isDigit() checks if a character is a digit
                {
                    // If the first digit is empty, store the first digit in the firstDigit variable
                    if (firstDigit.isEmpty())
                    {
                        firstDigit = String.valueOf(line.charAt(i)); // String.valueOf() converts char to String
                    }

                    // Store the last digit in the lastDigit variable
                    lastDigit = String.valueOf(line.charAt(i));
                }
            }

            // Check if the first and last digit are not empty
            if (!firstDigit.isEmpty() && !lastDigit.isEmpty())
            {
                // Concatenate the first and last digit, and convert it to an integer
                int concatenatedNumber = Integer.parseInt(firstDigit + lastDigit);

                // Add the concatenated number to the sum and print the line number and the concatenated number
                sum += concatenatedNumber;
                System.out.printf("Line %d: %d\n", lineCounter, concatenatedNumber);
            }
        }

        fileReader.close();

        // Print the calibration value
        System.out.printf("The calibration value is: %d\n", sum);
    }
}
