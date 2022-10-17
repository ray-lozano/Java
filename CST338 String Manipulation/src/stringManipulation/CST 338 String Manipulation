/*
 * Raymond Lozano CST 338: Software Design
 * This program will ask for the first and last name of the user. Then it will
 * output that name and say how long the name is, and then output the name in
 * upper case and then lower case.
 * The program will next ask the user how many hours they have spent this week
 * on this course, then output it to 3 decimal places.
 */
package stringManipulation;
import java.util.Scanner;
import java.text.DecimalFormat;

public class StringManipulation
{
   public static void main(String[] args)
   {
      //Create keyboard scanner for input
      Scanner keyboard = new Scanner(System.in);
      
      //Ask for first and last name
      System.out.println("Please enter your first name, capitalize the first"
            + " letter: ");
      String firstName = keyboard.next();
      
      System.out.println("Please enter your last name, capitalize the first"
            + " letter: ");
      String lastName = keyboard.next();
      
      //Create the full name and output
      String fullName = firstName + " " + lastName;
      System.out.println(fullName + " " + fullName.length());
      System.out.println(fullName.toUpperCase() + " " + fullName.toLowerCase());
      
      //Creating code block for asking amount of hours spent.
      final double MIN_HOURS = 12.000;
      final double MAX_HOURS = 20.000;
      DecimalFormat df = new DecimalFormat("##.000");
      
      //Ask user how many hours they have spent
      System.out.println("Between " + MIN_HOURS + " to " + MAX_HOURS + " hours,"
            + " how many hours have you spent this week to 3 decimal places? ");
      double hours = keyboard.nextDouble();
      
      //Output hours
      System.out.println(df.format(hours) + " hours.");
      
      //Close keyboard
      keyboard.close();
      
      /*
       * Please enter your first name, capitalize the first letter: 
         Raymond
         Please enter your last name, capitalize the first letter: 
         Lozano
         Raymond Lozano 14
         RAYMOND LOZANO raymond lozano
         Between 12.0 to 20.0 hours, how many hours have you spent this week to 
         3 decimal places? 
         14.56789
         14.568 hours.
         Please enter your first name, capitalize the first letter: 
         Jesse
         Please enter your last name, capitalize the first letter: 
         Cecil
         Jesse Cecil 11
         JESSE CECIL jesse cecil
         Between 12.0 to 20.0 hours, how many hours have you spent this week to 
         3 decimal places? 
         19.89341
         19.893 hours.
       */
   }   
}
