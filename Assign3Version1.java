//change emum to caps
public class Assign3
{

   public static void main(String[] args)
   {
      // TODO Auto-generated method stub

   }

   class Card
   {
      public enum Suit { clubs, diamonds, hearts, spades }  
      private char value;
      private Suit suit;
      private boolean errorFlag;
      
      //Default Constructor
      Card()
      {
         set('A', Suit.spades);
      }
      
      //Constructor
      Card(char value, Suit suit)
      {
              set(value, suit);
      }
      
      //Mutators
      public void setValue(char newValue)
      {
              value = newValue;
      }
      
      public void setSuit(Suit newSuit)
      {
              suit = newSuit;
      }
      
      //Accessors
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
      
      //Public Methods
      public String toString()
      {
         if (errorFlag == true)
         {
            return "[ invalid ]";
         }
         else 
         {
            return "(" + value + ", " + suit +")";
         }
      }
      
      public boolean set(char value, Suit suit)
      {
         setValue(value);
         setSuit(suit);
         errorFlag = isValid(value, suit);
         
         return true;
      }
      
      public boolean equals(Card card)
      {
         if (getValue() == card.getValue() && getFlag() == card.getFlag() && getSuit() == card.getSuit())
         {
            return true;
         }
         else
            return false;
      }
      
      //Private Methods
      private boolean isValid(char value, Suit suit)
      {
         if (value == 'A' || value == '2' || value == '3' || value == '4' || value == '5' || value == '6' || value == '7' || value == '8' || value == '9' || value == 'T' || value == 'J' || value == 'Q' || value == 'K')
         {
            return false;
         }
         else
            return true;
      }
      
   public class Hand {
      static public int  MAX_CARDS = 100;
      private Card[] myCards;
      private int numCards;
      

      //Hand() Norma
      //void resetHand()Norma
      //boolean takeCard(Card card) Norma
      //Card playCard() Lupe

      //String toString() Lupe
      
      //Accessor for numCards.Brenna
      public int getNumCards(){
         int qtyCards;
         qtyCards = numCards;
         return qtyCards;
      }
      //Card inspectCard(int k) Brenna
      public Card inspectCard(int k){
         Card myCard = new Card();
         
         if(myCards[k].errorFlag() == false)
         {
            myCard = myCards[k];
         }
         return myCard;
      }

   }
   public class Deck {

      // Final constant ( 6 packs maximum ): 
      public static final int MAX_CARDS = 6*52; 

      // Private static member data: 
      private static Card [] masterPack; // Array, containing exactly 52 card references.
      
      // Private member data: 
      private Card [] cards; 
      private int topCard; 
      private int numPacks; 

      // Public Methods ===================================================

      // Constructor : Populates the arrays and assigns initial valuees to members. 
      public Deck (int numPacks) {

         allocateMasterPack (); // Lupes Method (needs to be called in constructor).  
         this.cards = masterPack; 
         init (numPacks);
      }
      
      // Overloaded Constructor : If no parameters are passed, 1 pack is assumed (default). 
      public Deck () {

         this(1); 
      }

      // Re-populates Card array with 52 * numPacks. ( Doesn't re-populate masterPack ). 
      void init (int numPacks) {

         this.numPacks = numPacks; 
         int a, b, c;   
         
         // Need to get top card via Brenna's accessor...
         if ((topCard) <= MAX_CARDS && numPacks != 0) {
            cards = new Card [topCard]; 
            
            for (a = 0; a < cards.length; a++) {
               cards [a] = new Card ();      
         
               for (a = 0; a < numPacks; a++) {
               
                  for (b = 52 * a; c = 0; b < 52 * a + 52; b++, c++) {
                     cards [b] = masterPack [c]; 
                  }
               }
            }
         else 
            return;  
         }
      } // Close init method. 

      // Mix up cards with the help of standard random num generator: 
      public void shuffle () {   
         
         Random randObj = new Random(); 
         int a; 
         
         for (a = cards.length - 1; a > 0; a--) {
            
            // nextInt() method to get a pseudorandom value from 0 to cards.length. 
            int randNum = randObj.nextInt(k +1);
               
            // Changing the cards value: 
            Card temp = cards [randNum];
            cards [randNum] = cards [a]; 
            cards [a] = temp; 
         }
      } // Close shuffle method. 
      //      Card dealCard() -      Brenna
      public Card dealCard(){
         Card myCard;
         
         if (topCard != 0 && cards[topCard - 1].errorFlag == false )
         {
            topCard--;
            return cards[topCard];
         }
         else
         {
            //print error invalid card or no cards remaining in deck
         }
         
         
      }
      //accessor for the int, topCard (no mutator.)      Brenna
      public int getTopCard(){
         
         return topCard;
         
      }


      // Accessor for an individual card. Returns a card with errorFlag = true if k is bad. 
      public Card inspectCard (int k) {

         Card card; 

         if (k < 0) {
            // Triggers errorFlag as there is no value 'R'. 
            card = new Card('R', Suit.hearts);
         }
         else {
            card = card [k]; 
         }
         
         return card;  
      }
      
   } // Close deck class. 

}
