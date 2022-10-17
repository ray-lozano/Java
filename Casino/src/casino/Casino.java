/*
 * This program will create a slot machine game asking the player to enter a 
 * betting amount between 0 and 100. If the player enters 0 they quit the game
 * and see their winnings. The player will be able to get obtain a total of
 * 40 winnings.
 */

package casino;
import java.util.*;
public class Casino
{

   public static void main(String[] args)
   {      
      ThreeString threeString = new ThreeString();
      int bet;
      do
      {
         //Get the player bet and check if they quit
         bet = getBet();
         if(bet == 0) break;
         
         //Get the slots pull and check for the payout
         ThreeString pullString = pull();
         int payMultiplier = bet * getPayMultiplier(pullString);
         
         //display message of each pull
         display(pullString, payMultiplier);
         
         //add winning to array         
         threeString.saveWinnings(payMultiplier);
         
      }while(bet != 0);
      
      //Game ends, display results
      System.out.println("Thank you for playing!");
      System.out.println(threeString.toStringWinnings());        
   }
   
   //Get user bet and check if within valid range
   public static int getBet()
   {
      int bet;
      do
      {
         //Ask user for their bet amount
         Scanner keyboard = new Scanner(System.in);
         System.out.println("How much would you like to bet (1 - 100) "
               + "or 0 to quit?");
         bet = keyboard.nextInt();
      
         //Check if bet is within range
         if(bet == 0) break;         
      }while(bet < 0 || bet > 100);
      
      return bet;
   }
   
   public static ThreeString pull()
   {
      ThreeString casino = new ThreeString();
      String pull = "";      
      //Generate 3 random strings
      for(int i = 0; i < 3; i++)
      {
         pull = randString();
         if(i == 0) casino.checkString1(pull);
         else if(i == 1) casino.checkString2(pull);
         else casino.checkString3(pull);
      }
      return casino;
   }
   
   //Generates a random slot
   private static String randString()
   {
      String[] slots = {"space", "space", "space", "space", "cherries", 
            "cherries", "BAR", "7"};
      int random = (int)(Math.random() * 8);
      
      return slots[random];
   }
   
   //Determine the payout
   public static int getPayMultiplier(ThreeString thePull)
   {          
      int payMultiplier = 0;     
      String slots = thePull.toString(); 
      if(slots.equals("[7 7 7]"))
      {
         payMultiplier = 100;         
      }
      else if(slots.equals("[BAR BAR BAR]"))
      {
         payMultiplier = 60;
      }
      else if(slots.equals("[cherries cherries cherries]"))
      {
         payMultiplier = 40;
      }
      else if(thePull.getString1().equals("cherries") &&
            thePull.getString2().equals("cherries"))
      {
         payMultiplier = 15;
      }     
      else if(thePull.getString1().equals("cherries"))
      {
         payMultiplier = 5;
      }     
      return payMultiplier;
   }
   
   //Displays win or lose message and shows winnings
   public static void display(ThreeString thePull, int winnings)
   {
      System.out.println(thePull);
      if(winnings == 0)
      {
         System.out.println("Sorry you lost.");
      }
      else
      {
         System.out.println("Congrats, you won $" + winnings);
      }
   }

}

/*
 * ThreeString class for keeping all relevant information for the slot
 * machine pulls for the player. This class will also store the winnings into
 * an array.
 */
class ThreeString
{
   private String string1, string2, string3;     
   public static final int MAX_LEN = 20;
   public static final int MAX_PULLS = 40;
   private static int[] pullWinnings = new int[MAX_PULLS];
   private static int numPulls;

   //Initialize the three strings
   public ThreeString()
   {
      string1 = "";
      string2 = "";
      string3 = "";
   }
   
   //Check if string is valid
   private boolean validString(String str)
   {     
      if((str != null) && (str.length() < MAX_LEN)) return true;  
      else return false;
   }   
   
   public boolean checkString1(String str)
   {
      if(validString(str))
      {
         string1 = str;
         return true;
      }
      else return false;
   }
   
   public boolean checkString2(String str)
   {
      if(validString(str))
      {
         string2 = str;
         return true;
      }
      else return false;
   }
   
   public boolean checkString3(String str)
   {
      if(validString(str)) 
      {
         string3 = str;
         return true;
      }
      else return false;
   }
   
   //Access the three strings
   public String getString1()
   {
      return string1;
   }
   
   public String getString2()
   {
      return string2;
   }
   
   public String getString3()
   {
      return string3;
   }
   
   //Combine the three strings
   public String toString()
   {
      String results = String.format("[%s %s %s]", string1, string2, string3);
      return results;
   }
   
   //Check if there is room for winnings
   public boolean saveWinnings(int winnings)
   {     
      if(numPulls != pullWinnings.length)
      {      
         pullWinnings[numPulls] = winnings;
         numPulls++;
         return true;                
      }
      else
      {
         return false;  
      }
   }
   
   //Store the winnings in a string along with the total
   public String toStringWinnings()
   {
      String stringWinnings = "";
      int total = 0;
      
      for(int i : pullWinnings)
      {
         total += i;
         stringWinnings += (i + " ");
      }
      String totalWinnings = ("Your individual winnings were:\n" 
            + stringWinnings + "\nYour total winnings were:\n" + total);
      return totalWinnings;
   }
}

/*
How much would you like to bet (1 - 100) or 0 to quit?
5
[BAR 7 space]
Sorry you lost.
How much would you like to bet (1 - 100) or 0 to quit?
5
[cherries cherries BAR]
Congrats, you won $75
How much would you like to bet (1 - 100) or 0 to quit?
5
[space BAR 7]
Sorry you lost.
How much would you like to bet (1 - 100) or 0 to quit?
5
[7 7 7]
Congrats, you won $500
How much would you like to bet (1 - 100) or 0 to quit?
5
[cherries cherries 7]
Congrats, you won $75
How much would you like to bet (1 - 100) or 0 to quit?
5
[7 cherries BAR]
Sorry you lost.
How much would you like to bet (1 - 100) or 0 to quit?
5
[space cherries cherries]
Sorry you lost.
How much would you like to bet (1 - 100) or 0 to quit?
5
[cherries space cherries]
Congrats, you won $25
How much would you like to bet (1 - 100) or 0 to quit?
5
[space cherries space]
Sorry you lost.
How much would you like to bet (1 - 100) or 0 to quit?
5
[space 7 cherries]
Sorry you lost.
How much would you like to bet (1 - 100) or 0 to quit?
0
Thank you for playing!
Your individual winnings were:
0 75 0 500 75 0 0 25 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
Your total winnings were:
675

How much would you like to bet (1 - 100) or 0 to quit?
5
[BAR cherries space]
Sorry you lost.
How much would you like to bet (1 - 100) or 0 to quit?
5
[7 space 7]
Sorry you lost.
How much would you like to bet (1 - 100) or 0 to quit?
5
[cherries space space]
Congrats, you won $25
How much would you like to bet (1 - 100) or 0 to quit?
5
[7 space BAR]
Sorry you lost.
How much would you like to bet (1 - 100) or 0 to quit?
5
[space space BAR]
Sorry you lost.
How much would you like to bet (1 - 100) or 0 to quit?
0
Thank you for playing!
Your individual winnings were:
0 0 25 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
Your total winnings were:
25

*/
