/*
 * Raymond Lozano and Richard Rivera
 * CST 338-40_SP22: Software Design
 * Deck of Cards
 * Create a program that will draw hands for a set amount of players up to 10.
 * Three defined classes Card, Deck, and Hand to allow each player to draw an
 * even amount of cards.
 */
package deckofcards;
import java.util.Scanner;

import deckofcards.Card.Suit;

//Main class for Phases 3 and 4. By Raymond Lozano
public class MainDeckofCards
{

   public static void main(String[] args)
   {
      //Phase 3 testing deck class.
      //Initializing the deck with 2 decks
      Deck deck1 = new Deck(2);
      deck1.init(2);
      for(int i = 0; i < 52 * 2; i++)
      {
         System.out.println(deck1.inspectCard(i).toString() + " ");
      }
      
      //Initialize again and shuffle the two decks
      deck1.init(2);
      deck1.shuffle(); 
      for(int i = 0; i < 52 * 2; i++)
      {
         System.out.println(deck1.inspectCard(i).toString() + " ");
      }
      
      //Double deal
      deck1.init(1);
      for(int i = 1; i < 52; i += 2)
      {         
         System.out.print(deck1.inspectCard(i - 1).toString() + " / ");
         System.out.println(deck1.inspectCard(i).toString() + " ");
      }
      
      //Shuffled double deal
      deck1.init(1);
      deck1.shuffle();
      for(int i = 1; i < 52; i += 2)
      {         
         System.out.print(deck1.inspectCard(i - 1).toString() + " / ");
         System.out.println(deck1.inspectCard(i).toString() + " ");
      }
      
      
      //Play cards! Phase 4 testing, combining deck and hand.
      int players = 0;
      Scanner keyboard = new Scanner(System.in);
      do
      {
         //Check if player count is within range.
         System.out.println("How many players (1 - 10)?");
         players = keyboard.nextInt();
      }while(players > 10 || players < 1);
      
      //Initialize the deck and hands.
      Deck deck2 = new Deck(1);
      Hand[] hand = new Hand[players];
      for(int i = 0; i < players; i++) hand[i] = new Hand();
      
      //Deal the cards.
      for(int i = 0; i < 52 / players; i++)
      {
         for(int j = 0; j < players; j++) hand[j].takeCard(deck2.dealCard());
      }
      
      //Print the unshuffled hands.
      System.out.println("Here are our hands, from unshuffled deck: ");
      for(int i = 0; i < players; i++)
      {
         System.out.println(hand[i].toString() + "\n");
      }
      
      //Reset hand and shuffle the deck
      for(int i = 0; i < players; i++) hand[i].resetHand();
      Deck deck3 = new Deck(1);
      deck3.shuffle();
      
      //Deal the cards
      for(int i = 0; i < 52 / players; i++)
      {
         for(int j = 0; j < players; j++) hand[j].takeCard(deck3.dealCard());
      }
      
      //Print the shuffled hands.
      System.out.println("Here are our hands, from SHUFFLED deck: ");
      for(int i = 0; i < players; i++)
      {
         System.out.println(hand[i].toString() + "\n");
      }
   }

}

//Card Class
class Card
{
   public enum Suit {clubs, diamonds, hearts, spades};
   
   private char value;
   private Suit suit;
   private boolean cardError;
 
   //Card Method for our mutators, plus overloaded method for default value.
   public Card(char value, Suit suit) {
      set(value, suit); 
   }
   public Card(char value) {
      this(value, Suit.spades);
   }
   public Card() {
      this('A', Suit.spades);
   }
   
   //Copy Constructor
   public Card(Card origCard) {
      this(origCard.value, origCard.suit);
   }
   
   //cardError Accessor
   public boolean CardError()
   {
      return this.cardError;
   }
   
   //Displays the card, if cardError == true, then returns invalid.
   public String toString() {
      String rejectVal;
      if (CardError()) {
         rejectVal = "[invalid]";
         return rejectVal;
      } else {
         rejectVal = value + " of " + suit;
         return rejectVal;
      }
   }
   
   //Mutator, if bad values are passed, cardError = true. Good values stored.
   public boolean set(char value, Suit suit) {
      char highVal;
      boolean verify;
      this.suit = suit;
    
      highVal = Character.toUpperCase(value);
    
      verify = isValid(highVal, suit);
      if (verify) {
         this.value = highVal;
         this.cardError = false;
      } else {
         this.cardError = true;
         verify = false;
         this.value = 'A';
      }
      return verify;
   }
   
   //Accessors
   public char getVal() {
      return value;
   }
   public Suit getSuit() {
      return suit;
   }
   
   //Checks the validity of the card's value.
   private boolean isValid(char highVal, Suit suit) {
      if (highVal == 'A' || highVal == 'K' || highVal == 'J' || highVal == 'Q'
          || highVal == 'T' || (highVal >= '2' && highVal <= '9')) {
         return true;
      } else {
         return false;
      }
   }
}

//Hand Class by Richard Rivera
class Hand
{
   public static final int MAX_CARDS = 100;

   private Card[] myCards;
   private int numCards;

   //Default constructor
   public Hand() {
      myCards = new Card[MAX_CARDS];
      resetHand();
   }

   //Removes all cards from the hand.
   public void resetHand() {
      numCards = 0;
   }

   //Adds card to the next available position.
   public boolean takeCard(Card card) {
      if (numCards < MAX_CARDS) {
         this.myCards[numCards++] = card;
         return true;
      } else {
         return false;
      }
   }

   //Returns and removes the card in the top occupied position.
   public Card playCard() {
      Card rturnPlayCard = this.myCards[numCards - 1];
      myCards[numCards - 1] = null;
      numCards--;
      return rturnPlayCard;
   }

   //Displays the hand.
   public String toString() {
      if (numCards == 0) {
         return "Hand = ( )";
      }
      String rturnHand = myCards[0].toString();
      for (int i = 1; i < numCards; i++) {
         rturnHand = rturnHand + ", " + myCards[i].toString();
      }
      rturnHand = new String("Hand = ( " + rturnHand + " )");
      return rturnHand;
   }

   //Accessor for numCards.
   public int getNumCards() {
      return numCards;
   }

   //Accessor for individual card and checks if it is valid.
   public Card inspectCard(int k) {
      if (numCards == 0 || k < 0 || k > numCards) {
         return new Card('F', Card.Suit.spades);
      } else {
         return myCards[k];
      }
   }
}

//Deck Class by Raymond Lozano
class Deck
{
   public static final int MAX_CARDS = 6 * 52;
   private static Card[] masterPack;
   private static boolean masterPackAllocate;
   
   private Card[] cards;
   private int topCard;
   private int numPacks;
   /*
    * public static Card array masterPack[]
    * exactly 52 references, might be a 2d array
    * Card[] masterPack
    */
   
   
  //Public Methods
   
   //Overloading Deck() for default values if no parameters taken.
   public Deck()
   {
      this.numPacks = 1;
   }
   
   public Deck(int numPacks)
   {
      /*
       * populates the arrays and assigns initial values to members
       * Overload so that if no parameters are passed, 1 pack is assumed.
       */
      allocateMasterPack();
      this.topCard = numPacks * 52;
      this.cards = masterPack;
      
        
      init(numPacks);
   }
   
   
   //Re-populate cards[] with the standard 52 * numPacks cards
   public void init(int numPacks)
   {
      this.numPacks = numPacks;
      if((topCard) <= MAX_CARDS && (numPacks != 0))
      {
         cards = new Card[topCard];
         for(int i = 0; i < this.topCard; i++) cards[i] = masterPack[i % 52];      
      }
      
   }
   
   //Shuffles the deck(s) using randomizer
   public void shuffle()
   {
      for(int i = 0; i < this.topCard; i++)
      {
         double random = Math.random() * this.topCard;
         int shuffleDeck = (int)random;
         Card cardShuffle = cards[i];
         cards[i] = cards[shuffleDeck];
         cards[shuffleDeck] = cardShuffle;
      }
   }
   
   //Deals the next card of the deck(s).
   public Card dealCard()
   {
      //Checks if there are no more cards in the deck, otherwise deal next.
      if(this.topCard == 0) return null;
      else
      {
         //Calls the card class to get the value/suit of next card.
         Card nextCard = new Card(cards[this.topCard - 1].getVal(), 
               cards[this.topCard - 1].getSuit());
         cards[this.topCard - 1] = null;
         topCard--;
         return nextCard;
      }
   }
   
   //Accessor for topCard
   public int topCard()
   {
      return topCard;
   }
   
   //Checks the individual card
   public Card inspectCard(int k)
   {
      //Causes invalid card if true.
      if(topCard == 0 || k < 0 || k > topCard)
      {
         Card invalidCard = new Card('b', Card.Suit.clubs);
         return invalidCard;
      }
      else return cards[k];
   }
   
   
   //Private methods
   private static void allocateMasterPack()
   {
      //Checks if method has already been ran, if true then returns.
      if(masterPackAllocate == true) return;
      else
      {
         masterPack = new Card[52];
         Card.Suit suit;
         
         
         for(int i = 0; i < masterPack.length; i++) masterPack[i] = new Card();
         for(int i = 0; i < 4; i++)
         {
            if(i == 0) suit = Card.Suit.clubs;
            else if(i == 1) suit = Card.Suit.diamonds;
            else if(i == 2) suit = Card.Suit.hearts;
            else suit = Card.Suit.spades;            
            
            //Build the masterPack, starting with Ace
            masterPack[13 * i].set('A', suit);
            
            //Builds 2 through 9
            for(char j = '2', k = 1; j <= '9'; j++, k++)
            {
               masterPack[13 * i + k].set(j, suit);
            }
            //Builds 10 through King
            masterPack[13 * i + 9].set('T', suit);
            masterPack[13 * i + 10].set('J', suit);
            masterPack[13 * i + 11].set('Q', suit);
            masterPack[13 * i + 12].set('K', suit);
         }
         masterPackAllocate = true;
      }
   }   
}
