/*
 * By:  Danh Huynh, Brenna Eckel, Steven Hunt, Guadalupe Alejo, Norma Sanchez
 * CST338 Software Design
 * Assignment 3
 * May 16, 2017
 * 
 * Description:  This program simulates a deck of playing cards.  The user is prompted for the number of players
 * then deals a deck of cards to each player one at a time.  It then shuffles the deck and deals again.
 * This program contains a Card, Hand, and Deck class that can be used in other application.
 * 
 * */

import java.util.*; 

public class Assign3
{
   public static void main( String[] args )
   {
      int players = 0;
      Deck cardDeck = new Deck();

      Scanner key = new Scanner( System.in );

      // Prompt user for number of players between 1 and 10
      while ( players < 1 || players > 10 )
      {
         System.out.print("Please enter a number of players between 1 and 10:  ");
         players = key.nextInt();
      }

      // Create an array of hands 
      Hand[] handsArray = new Hand[ players ];
      
      // Initialize hand array
      for ( int m = 0; m < players; m++ )
      {
         handsArray[m] = new Hand();
      }

      // Deal cards to each player until deck is empty
      while ( cardDeck.getTopCard() > 0 )
      {
         for ( int i = 0; i < players; i++ )
         {
            if ( cardDeck.getTopCard() > 0 )
            {
               handsArray[i].takeCard( cardDeck.dealCard() );
            }
         }
      }
      
      System.out.print( "\nHere are our hands, from UNSHUFFLED deck: \n" );
      // Print each hand then reset it
      for ( int j = 0; j < players; j++ )
      {
         System.out.println( "Player " + ( j + 1 ) + " " + handsArray[j].toString() );
         handsArray[j].resetHand();
      }
      
      System.out.print( "\nHere are our hands, from SHUFFLED deck:\n" );
      
      // Reset deck of cards
      cardDeck.init(1);
      
      // Shuffle cards
      cardDeck.shuffle();

      //  Deal cards to each player until deck is empty
      while ( cardDeck.getTopCard() > 0 )
      {
         for(int k = 0; k < players; k++)
         {
            if( cardDeck.getTopCard() > 0 )
            {
               handsArray[k].takeCard( cardDeck.dealCard() );
            }
         }
      }
      
      // Print each hand
      for ( int l = 0; l < players; l++ )
      {
         System.out.println("Player " + ( l + 1 ) + " " + handsArray[l].toString());
      }
      key.close();
   }
}

// Simulates a card in a deck of playing cards
class Card
{
   enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES };  
   private char value;
   private Suit suit;
   private boolean errorFlag;

   // Default Constructor
   Card()
   {
      set( 'A', Suit.SPADES );
   }

   // Constructor
   Card( char value, Suit suit )
   {
      set( value, suit );
   }

   // Mutators
   public void setValue( char newValue )
   {
      value = newValue;
   }

   public void setSuit( Suit newSuit )
   {
      suit = newSuit;
   }
   
   public boolean set( char value, Suit suit )
   {
      setValue( value );
      setSuit( suit );
      errorFlag = isValid( value, suit );

      return true;
   }

   // Accessors
   public char getValue()
   {
      return value;
   }

   public Suit getSuit()
   {
      return suit;
   }

   public boolean getFlag()
   {
      return errorFlag;
   }

   // Public Methods
   // Returns the value and suit of a card object as a single string
   public String toString()
   {
      if ( errorFlag == true )
      {
         return "[ invalid ]";
      }
      else 
      {
         return "[" + value + ", " + suit +"]";
      }
   }

   // Returns whether a card is equivalent to the source card
   public boolean equals( Card card )
   {
      if ( getValue() == card.getValue() && getFlag() == card.getFlag() && getSuit() == card.getSuit() )
      {
         return true;
      }
      else
         return false;
   }

   // Private Methods
   // Returns whether a card has a valid value
   private boolean isValid( char value, Suit suit )
   {
      if (value == 'A' || value == '2' || value == '3' || value == '4' || value == '5' || value == '6' || value == '7'
            || value == '8' || value == '9' || value == 'T' || value == 'J' || value == 'Q' || value == 'K')
      {
         return false;
      }
      else
         return true;
   }
}

class Hand {
   
   static public int  MAX_CARDS = 100;
   private Card[] myCards;
   private int numCards;

   // Default constructor for a hand of cards.
   public Hand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   // Remove all cards from the hand
   public void resetHand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   // Adds a card to the nest available position in the myCards array.
   public boolean takeCard( Card card )
   {
      boolean valid;
      
      if ( numCards >= MAX_CARDS )
      {
         valid = false;
      }
      
      else
      {
         myCards[numCards] = card;
         numCards++;
         valid = true;
      }
      return valid;
   }

   // This method will remove and return the top card in the array
   public Card playCard()
   {      
      Card topCard =  myCards[numCards - 1];
      myCards[numCards - 1] = null; 
      numCards-- ;
      return topCard;
   }

   // Returns the string built up by the Stringizer
   public String toString()
   {
      int i;
      String cardInfo = "Hand = ( ";

      for ( i = 0; i < numCards; i++ )
      {
         cardInfo += myCards[i].toString();
         if ( i < numCards - 1 )
            cardInfo += ", ";
      }
      
      cardInfo +=" )";
      return cardInfo;
   }

   //Returns number of cards
   public int getNumCards(){
      return numCards;
   }

   //Returns a card at a given index
   public Card inspectCard( int k ){
      return myCards[k];
   }
}

class Deck {

   // Final constant ( 6 packs maximum ): 
   public static final int MAX_CARDS = 6*52; 

   // Private static member data: 
   private static Card [] masterPack; // Array, containing exactly 52 card references.

   // Private member data: 
   private Card [] cards; 
   private int topCard; 
   private int numPacks; 

   // Constructor : Populates the arrays and assigns initial values to members. 
   public Deck ( int numPacks ) {
      allocateMasterPack (); // Lupe's Method (needs to be called in constructor).  
      this.cards = masterPack; 
      init ( numPacks );
      topCard = 52*numPacks;
   }

   // Overloaded Constructor : If no parameters are passed, 1 pack is assumed (default). 
   public Deck () {
      this(1); 
   }

   // Re-populates Card array with 52 * numPacks. ( Doesn't re-populate masterPack ). 
   void init ( int numPacks ) {
      this.cards = masterPack;
      this.numPacks = numPacks;
      this.topCard = 52*numPacks;
   }

   // Mix up cards with the help of standard random num generator: 
   public void shuffle () {   
      Random randObj = new Random(); 
      int a; 

      for (a = cards.length -1 ; a > 0; a--) {

         // nextInt() method to get a pseudorandom value from 0 to cards.length. 
         int randNum = randObj.nextInt( cards.length - 1);

         // Changing the cards value: 
         Card temp = cards [randNum];
         cards [randNum] = cards [a]; 
         cards [a] = temp; 
      }
   } 
   
   // Decrements card from the deck
   public Card dealCard(){
      if ( topCard != 0 )
      {
         topCard--;
      }

      return cards[topCard];
   }

   // Accessor for topCard
   public int getTopCard(){
      return topCard;
   }

   // Accessor for an individual card. Returns a card with errorFlag = true if k is bad. 
   public Card inspectCard ( int k ) {   
      return cards[k];    
   }

   // Defines masterpack
   private static void allocateMasterPack()
   { 
      int x, y;
      Card.Suit cardSuit;
      char cardValue;

      // allocate 
      masterPack = new Card[52];
      for (y = 0; y < 52; y++)
         masterPack[y] = new Card();

      // loop for the suits 
      // set values for suit
      for (y = 0; y < 4; y++)
      {
         cardSuit = Card.Suit.values()[y];
         masterPack[13*y].set( 'A', cardSuit );  

         for ( cardValue='2', x = 1; cardValue<='9'; cardValue++, x++ )
         {
            masterPack[13*y + x].set( cardValue, cardSuit );
         }

         masterPack[13*y+9].set( 'T', cardSuit );
         masterPack[13*y+10].set( 'J', cardSuit );
         masterPack[13*y+11].set( 'Q', cardSuit );
         masterPack[13*y+12].set( 'K', cardSuit );
      }
   }
}

/************************************OUTPUT****************************************************************************
Please enter a number of players between 1 and 10:  8

Here are our hands, from UNSHUFFLED deck: 
Player 1 Hand = ( [K, SPADES], [5, SPADES], [T, HEARTS], [2, HEARTS], [7, DIAMONDS], [Q, CLUBS], [4, CLUBS] )
Player 2 Hand = ( [Q, SPADES], [4, SPADES], [9, HEARTS], [A, HEARTS], [6, DIAMONDS], [J, CLUBS], [3, CLUBS] )
Player 3 Hand = ( [J, SPADES], [3, SPADES], [8, HEARTS], [K, DIAMONDS], [5, DIAMONDS], [T, CLUBS], [2, CLUBS] )
Player 4 Hand = ( [T, SPADES], [2, SPADES], [7, HEARTS], [Q, DIAMONDS], [4, DIAMONDS], [9, CLUBS], [A, CLUBS] )
Player 5 Hand = ( [9, SPADES], [A, SPADES], [6, HEARTS], [J, DIAMONDS], [3, DIAMONDS], [8, CLUBS] )
Player 6 Hand = ( [8, SPADES], [K, HEARTS], [5, HEARTS], [T, DIAMONDS], [2, DIAMONDS], [7, CLUBS] )
Player 7 Hand = ( [7, SPADES], [Q, HEARTS], [4, HEARTS], [9, DIAMONDS], [A, DIAMONDS], [6, CLUBS] )
Player 8 Hand = ( [6, SPADES], [J, HEARTS], [3, HEARTS], [8, DIAMONDS], [K, CLUBS], [5, CLUBS] )

Here are our hands, from SHUFFLED deck:
Player 1 Hand = ( [8, CLUBS], [7, DIAMONDS], [8, SPADES], [5, DIAMONDS], [Q, DIAMONDS], [6, DIAMONDS], [J, CLUBS] )
Player 2 Hand = ( [A, HEARTS], [9, DIAMONDS], [2, CLUBS], [4, CLUBS], [7, HEARTS], [T, HEARTS], [J, SPADES] )
Player 3 Hand = ( [T, SPADES], [6, HEARTS], [9, HEARTS], [7, SPADES], [Q, CLUBS], [T, CLUBS], [Q, SPADES] )
Player 4 Hand = ( [2, DIAMONDS], [6, SPADES], [A, DIAMONDS], [8, HEARTS], [3, CLUBS], [6, CLUBS], [A, CLUBS] )
Player 5 Hand = ( [K, HEARTS], [3, DIAMONDS], [4, HEARTS], [7, CLUBS], [4, DIAMONDS], [J, HEARTS] )
Player 6 Hand = ( [3, SPADES], [8, DIAMONDS], [5, SPADES], [Q, HEARTS], [5, CLUBS], [9, SPADES] )
Player 7 Hand = ( [5, HEARTS], [2, SPADES], [4, SPADES], [9, CLUBS], [2, HEARTS], [K, DIAMONDS] )
Player 8 Hand = ( [K, CLUBS], [3, HEARTS], [K, SPADES], [T, DIAMONDS], [A, SPADES], [J, DIAMONDS] )

 */
