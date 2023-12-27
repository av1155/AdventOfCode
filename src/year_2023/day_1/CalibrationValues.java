package year_2023.day_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CalibrationValues
{
    public static void main( String[] args ) throws FileNotFoundException
    {
        File inputFile = new File( "/Users/andreaventi/Developer/AdventOfCode/src/year_2023/day_1/input.txt" );

        if ( inputFile.exists() )
        {
            Scanner fileReader = new Scanner( inputFile );
            System.out.println( getCalibrationValue( fileReader ) );
        }

        else
        {
            System.out.println( "File not found" );
        }
    }

    public static int getCalibrationValue( Scanner fileReader )
    {
        String line;
        int lineCounter = 0, sum = 0;

        String firstNumber = "";
        String secondNumber = "";

        while ( fileReader.hasNextLine() )
        {
            firstNumber = "";

            line = fileReader.nextLine();
            lineCounter++;

            for ( int i = 0; i < line.length(); i++ )
            {
                if ( Character.isDigit( line.charAt( i ) ) )
                {
                    if ( firstNumber.isEmpty() )
                    {
                        firstNumber = String.valueOf( line.charAt( i ) );
                    }

                    secondNumber = String.valueOf( line.charAt( i ) );
                }
            }

            String concatenatedNumbers = firstNumber + secondNumber;
            System.out.printf( "Line %d: %s\n", lineCounter, concatenatedNumbers );
            sum += Integer.parseInt( concatenatedNumbers );
        }

        return sum;
    }

    // For each line of the file, scan all characters on each line, check if the character is a digit, and find the
    // first and last numbers for each line of the file. Then, concatenate both numbers, and add the concatenated number
    // to a variable 'sum', which will keep adding the concatenated numbers of each line. Then, return the total sum of
    // all the concatenated numbers.
}
