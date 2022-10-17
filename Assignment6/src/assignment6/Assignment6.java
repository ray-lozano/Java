package assignment6;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Assignment6
{
   static int NUM_CARDS_PER_HAND = 7;
   static int  NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS];
   static JLabel gameText = new JLabel();
   static JLabel gameStatus = new JLabel();
   static int playerScore = 0, computerScore = 0;
   static int computerCards[] = new int[NUM_CARDS_PER_HAND];
   

   public static void main(String[] args)
   {
      // establish main frame in which program will run
      View.CardTable myCardTable = new View.CardTable("CardTable",
              NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      //Initiating the game from CardGameOutline
      int card;
      Icon tempIcon;
      int numPacksPerDeck = 1;
      int numJokersPerPack = 2;
      int numUnusedCardsPerPack = 0;
      Model.Card[] unusedCardsPerPack = null;

      Model.CardGameOutline suitmatchgame =
              new Model.CardGameOutline(numPacksPerDeck, numJokersPerPack,
                      numUnusedCardsPerPack,unusedCardsPerPack, NUM_PLAYERS,
                      NUM_CARDS_PER_HAND);
      suitmatchgame.deal();

      // CREATE LABELS ---------------------------------------------------
      View.GUICard.loadCardIcons();
      playLabelText[0] = new JLabel( "Computer", JLabel.CENTER );
      playLabelText[1] = new JLabel( "User", JLabel.CENTER );
      gameText = new JLabel("Welcome to the card game!", JLabel.CENTER);
      gameStatus = new JLabel("Click a card from your hand to play!", JLabel.CENTER);
      gameText.setForeground(Color.RED);
      gameStatus.setForeground(Color.MAGENTA);
      //Holds integer representations of card values for the computer    
      //Will hold the value of the suit for the computer
      for (int count = 0; count < NUM_CARDS_PER_HAND; count++)
      {
         int suitCount = 0;
         String computerSuit = (suitmatchgame.getHand(0).inspectCard(count).getSuit()).toString();
         if(computerSuit.equals("CLUBS")) suitCount = 1;
         else if(computerSuit.equals("DIAMONDS")) suitCount = 2;
         else if(computerSuit.equals("HEARTS")) suitCount = 3;
         else if(computerSuit.equals("SPADES")) suitCount = 4;
         computerCards[count] = suitCount;
         //System.out.print(suitCount); Test to see what suits in comp hand
      }
      for (card = 0; card < NUM_CARDS_PER_HAND; card++)
      {
          computerLabels[card] = new JLabel(View.GUICard.getBackCardIcon());
          tempIcon = View.GUICard.getIcon(suitmatchgame.getHand(1).inspectCard(card));
          humanLabels[card] = new JLabel(tempIcon);          
          //Mouse listener
          humanLabels[card].addMouseListener(
                  new MouseListener() {
                      @Override
                      public void mouseClicked(MouseEvent e) {
                          Controller.playGame(myCardTable.pnlHumanHand.getComponentZOrder
                                  (e.getComponent()), suitmatchgame);                         
                      }

                      @Override
                      public void mousePressed(MouseEvent e) { }

                      @Override
                      public void mouseReleased(MouseEvent e) { }

                      @Override
                      public void mouseEntered(MouseEvent e) { }

                      @Override
                      public void mouseExited(MouseEvent e) { }
                  }
          );   
      }      

      // ADD LABELS TO PANELS -----------------------------------------
      for (card = 0; card < NUM_CARDS_PER_HAND; card++)
      {
          myCardTable.pnlComputerHand.add(computerLabels[card]);
          myCardTable.pnlHumanHand.add(humanLabels[card]);
      }

      tempIcon = View.GUICard.getBackCardIcon();
      playedCardLabels[0] = new JLabel(tempIcon);
      playedCardLabels[1] = new JLabel(tempIcon);

      //Add the card labeling      
      myCardTable.pnlPlayArea.add(playedCardLabels[0]);
      myCardTable.pnlPlayArea.add(gameText);
      myCardTable.pnlPlayArea.add(playedCardLabels[1]);
      myCardTable.pnlPlayArea.add(playLabelText[0]);
      myCardTable.pnlPlayArea.add(gameStatus);
      myCardTable.pnlPlayArea.add(playLabelText[1]);

      // show everything to the user
      myCardTable.setVisible(true);

   }   
}