package dk.shift6;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * This program calculates the number of days between two dates provided as
 * arguments to the arguments list. Dates should be formatted as dd/MM/yyyy
 *
 * Example: java dk.shift6.Main 12/11/1975 31/08/2021
 *
 * The purpose of this program is to offer a solution without using any
 * date libraries.
 * NOTE: A confirmation via Chronounit is done and printed to console only as
 * validation of the result and is NOT part of the initial calculation.
 *
 *
 */

public class Main {


    private final static int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static void main(String[] args) {
        //initialize variables from argument list:
        String[] startDate = args[0].split("/");
        String[] endDate = args[1].split("/");
        
        int startDay = Integer.parseInt(startDate[0]);
        int startMonth = Integer.parseInt(startDate[1]);
        int startYear = Integer.parseInt((startDate[2]));

        int endDay = Integer.parseInt(endDate[0]);
        int endMonth = Integer.parseInt(endDate[1]);
        int endYear = Integer.parseInt((endDate[2]));

        //initialize variables for the calculation
        int currMonth = startMonth;
        int currYear = startYear;
        int accumDays = getMonthLength(currMonth++, currYear) - startDay;

        //running through the months until reaching the end date
        while(currYear < endYear || currMonth <= endMonth) {
            if(currMonth > 12) { //jump to next year
                currMonth = 1;
                currYear += 1;
            }

            accumDays += (currYear == endYear && currMonth == endMonth) ?
                    endDay : getMonthLength(currMonth, currYear);

            currMonth++;
        }

        //print the result:
        System.out.printf("Total number of days between %s and %s: %s%n", args[0], args[1], accumDays);


        // Confirmation using Chronounit
        try {
            LocalDate startDate2 = LocalDate.parse(args[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate endDate2 = LocalDate.parse(args[1], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.println("VERIFICATION via ChronoUnit: " + ChronoUnit.DAYS.between(startDate2, endDate2));
        } catch (Exception e)
        {
            System.out.println("DateTimeException during the Chronounit check. Remember year should be >= 0001");
        }
    }

    /**
     * This method will find the length of a month taking leap rules into account
     * @param month Month identified by position 1-12
     * @param year Year in format YYYY
     * @return Length of month in days
     */
    private static int getMonthLength(int month, int year) {
        int m = month - 1;
        if (m == 1) {
            return year % 4 == 0 && (year % 100 > 0 || year % 400 == 0) ?
                    months[m] + 1 : months[m];
        }
        return months[m];
    }

}
