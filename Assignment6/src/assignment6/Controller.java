package assignment6;

public class Controller
{
   public static void playGame(int index, Model.CardGameOutline suitMatchGame)
   {
      Assignment6.humanLabels[index].setVisible(false);
      Assignment6.playedCardLabels[1].setIcon(Assignment6.humanLabels[index].getIcon());
      String suit = (suitMatchGame.getHand(1).inspectCard(index).getSuit()).toString();
      int number = 0; //Keeps track of the value of the suit
      if(suit.equals("CLUBS")) number = 1;
      else if(suit.equals("DIAMONDS")) number = 2;
      else if(suit.equals("HEARTS")) number = 3;
      else if(suit.equals("SPADES")) number = 4;
      //System.out.println(suit + " " + number); //Test to see if values are correct for what is in hand
      computerPlay(number,suitMatchGame);
   }
   
   public static void computerPlay(int matchCard, Model.CardGameOutline suitMatchGame)
   {
      int bestCard = 0;
      int index = 0;
      for (int count = 0; count < Assignment6.NUM_CARDS_PER_HAND; count++)
      {
          int cardValue = Assignment6.computerCards[count];
          if (cardValue == -1) continue;
          if (cardValue > bestCard) 
          {
              bestCard = cardValue;
              index = count;
          }
      }
      Assignment6.computerLabels[index].setVisible(false);
      Assignment6.playedCardLabels[0].setIcon(View.GUICard.getIcon(suitMatchGame.getHand(0).inspectCard(index)));
      Assignment6.computerCards[index] = -1;

      //Decide winner and display the score of the game
      //if ((bestCard > matchCard) || (bestCard == matchCard)) Assignment6.computerScore++;
      if(bestCard == matchCard) Assignment6.computerScore++;
      else Assignment6.playerScore++;
      updateGame();
      
      
   }
   
   //Display the score from the game between the computer and the user.
   private static void updateGame()
   {
      Assignment6.gameStatus.setText("Score: " + Assignment6.computerScore + "-" + Assignment6.playerScore);
      if (Assignment6.computerScore + Assignment6.playerScore == Assignment6.NUM_CARDS_PER_HAND) 
      {
          if (Assignment6.computerScore > Assignment6.playerScore)
              Assignment6.gameText.setText("Computer Wins!");
          else if (Assignment6.playerScore > Assignment6.computerScore)
              Assignment6.gameText.setText("You win!");
          else Assignment6.gameText.setText("Tie game!");
      }
   }   
}
