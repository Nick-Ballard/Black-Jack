import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {

    static class Card {
        
        public String Name;
        public String Type;
        public int Value;
        public boolean isAce = false;

        public void SetLow()
        {
        	if(isAce)
        	{
        		this.Value = 1;
        	}
        }
        
        public void SetHigh()
        {
        	if(isAce)
        	{
        		this.Value = 1;
        	}
        }

        public String toString()
        {
            return Name + " Of " + Type;
        }
    }

    static class Hand {
        public ArrayList<Card> Cards = new ArrayList<Card>();
        public boolean hasAces = true;
        public boolean Bust = false;
        public boolean Won = false;
        public int Value = 0;
        
        public void EvalHand()
        {
        	this.Value = 0;
        	
        	for(Card c : Cards)
        	{
        		this.Value += c.Value;
        	}
        	
        	if(Value > 21)
        	{
        		Bust = true;
        	} else if( Value == 21){
        		Bust = false;
        		Won = true;
        	} else {
        		Bust = false;
        	}
        }
        
        public void GiveCard(Card card)
        {
        	Cards.add(card);
            if(card.isAce)
            {
                hasAces = true;
            }
            
            EvalHand();
            
            if(Bust)
            {
                if(hasAces)
                {
                	for(Card c : Cards)
                	{
                		c.SetLow();
                	}
                	EvalHand();
                }
            }
        }
        
        public String toString()
        {
        	String Hand = "";
        	for(Card card : Cards)
        	{
        		Hand = Hand.concat(card + ", ");
        	}
        	Hand = Hand.substring(0, Hand.length() - 2);
        	return Hand;
        }
        
    }

    // Card Deck
    public static ArrayList<Card> Deck;
    
    // User's Hands
    public static Hand Player;
    public static Hand AI;
    
    // Used To Generate Random Number
    public static Random ran = new Random();
    
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
    	
    	Boolean Playing = true;
    	Boolean UserTurn = true;
    	
    	int option = 0;
    	int PlayAgain = 0;
    	
    	String LastMove = "";
    	
        CreateGame();
        
        while(Playing)
        {
        	System.out.println("\n---------------------------------------------------------------\n");
        	if(Player.Value == 21)
        		UserTurn = false;

        	option = 0;
        	
        	if(UserTurn)
        	{	
        		PrintHands(UserTurn);
        		
        		System.out.println("");
        		System.out.println("Option 1.) Hit");
        		System.out.println("Option 2.) Stay");
        		
        		while(option != 1 && option != 2)
        		{
        			System.out.println("Input: ");
        			option = scanner.nextInt();
        			if(option != 1 && option != 2)
        			{
        				System.out.println("Please enter a valid option...");
        			} else {
        				if(option == 1)
        				{
        					Player.GiveCard(DrawCard());
        					LastMove = "You Hit!";
        					System.out.println(LastMove);
        					System.out.println("You Drew The " + Player.Cards.get(Player.Cards.size() - 1));
        				}else if(option == 2){
        					UserTurn = false;
        					LastMove = "You Chose To Stay. It's The AI's Turn Now";
        				}
        			}
        		}
        		
        		if(Player.Bust || Player.Won)
        		{
        			UserTurn = false;
        		}
        	} else {
        		PrintHands(UserTurn);
        		if((AI.Value >= 17 && AI.Value > Player.Value) || AI.Value == 21 || AI.Value > Player.Value)
        		{
        			Playing = false;
        			LastMove = "The AI Has Chose To Stay";
        			System.out.println(LastMove);
        		} else if(AI.Value <= Player.Value){
        			LastMove = "The AI Has Hit!";
        			System.out.println(LastMove);
        			AI.GiveCard(DrawCard());
        			System.out.println("AI Drew The " + Player.Cards.get(Player.Cards.size() - 1));
        		}
        	}
        	
        	if(Player.Bust || AI.Bust)
        	{
        		if(Player.Bust)
        		{
        			System.out.println("You Have Busted!");
        			System.out.println("The AI Has Won!");
        			System.out.println("");
        			System.out.println("Your Hand Value: " + Player.Value + ", AI Hand Value: " + AI.Value);
        		}else if(AI.Bust)
        		{
        			System.out.println("The AI Has Busted!");
        			System.out.println("You Have Won!");
        			System.out.println("");
        			System.out.println("Your Hand Value: " + Player.Value + ", AI Hand Value: " + AI.Value);

        		}
        		Playing = false;
        	}else if(Player.Won && AI.Won)
        	{
        		System.out.println("It's A Tie!");
        		System.out.println("Your Hand Value: " + Player.Value + ", AI Hand Value: " + AI.Value);
        		Playing = false;
        	}else if(Playing == false)
        	{
        		if(Player.Value > AI.Value)
        		{
        			System.out.println("You Have Won!");
        			System.out.println("Your Hand Value: " + Player.Value + ", AI Hand Value: " + AI.Value);
        		}
        		
        		if(Player.Value < AI.Value)
        		{
        			System.out.println("The AI Has Won!");
        			System.out.println("Your Hand Value: " + Player.Value + ", AI Hand Value: " + AI.Value);
        		}
        			
        	}
        	
        	if(!Playing)
        	{
        		int play = 0;
        		System.out.println("");
        		System.out.println("Would You Like To Play Again?");
        		System.out.println("1.) Yes");
        		System.out.println("2.) No");
        		System.out.println("Input: ");
        		
        		while(play != 1 && play != 2)
        		{
        			play = scanner.nextInt();
        			if(play != 1 && play != 2)
        			{
        				System.out.println("Please Enter A Valid Option!");
        			}
        		}
        		
        		 if(play == 1)
        			{
        				Playing = true;
        		    	UserTurn = true;
        		    	LastMove = "";
        				CreateGame();
        			}else if(play == 2)
        			{
        				System.out.println("Thanks For Playing!");
        			}
        		 System.out.println("\n---------------------------------------------------------------\n");
        	}
        }
    }
    
    public static void ClearConsole()
    {
    	System.out.print("\033[H\033[2J");
    }
    
    public static void PrintHands(Boolean UserTurn) {
    	System.out.println("");
    	System.out.println("Your Hand:");
    	System.out.println(Player);
    	System.out.println("Value: " + Player.Value);
    	System.out.println("");
    	if(!UserTurn)
    	{
        	System.out.println("AI's Hand:");
        	System.out.println(AI);
        	System.out.println("Value: " + AI.Value);
        	System.out.println("");
    	} else {
        	System.out.println("AI's Hand:");
        	System.out.println(AI.Cards.get(0) + ", Hidden");
        	System.out.println("Value: " + AI.Cards.get(0).Value);
        	System.out.println("");
    	}
    }
    
    public static void CreateGame()
    {
    	Deck = PopulateDeck();
    	Player = new Hand();
    	AI = new Hand();
    	
    	Player.GiveCard(DrawCard());
    	Player.GiveCard(DrawCard());
    	
    	AI.GiveCard(DrawCard());
    	AI.GiveCard(DrawCard());
    	
    	if(Player.Value == 21 && AI.Value == 21)
    		CreateGame();
    	
    }
    
    public static Card DrawCard() {
        int index = ran.nextInt(Deck.size() - 1);
        Card card = Deck.get(index);
        Deck.remove(card);
        return card;
    }
    
    // Populates All The Card Types In The Game Deck
    public static ArrayList<Card> PopulateDeck()
    {
        ArrayList<Card> Deck = new ArrayList<Card>();
        
        String[] CardTypes = new String[]{"Hearts", "Clubs", "Diamonds", "Spades"};
        String[] FaceCards = new String[]{"Jack", "Queen", "King"};
        
        for(int i = 0; i < CardTypes.length; i++)
        {
            for(int e = 2; e <= 10; e++)
            {
                Card card = new Card();
                card.Name = Integer.toString(e);
                card.Type = CardTypes[i];
                card.Value = e;
                
                Deck.add(card);
            }
            
            for(int e = 0; e < FaceCards.length; e++)
            {
                Card card = new Card();
                card.Name = FaceCards[e];
                card.Type = CardTypes[i];
                card.Value = 10;
                
                Deck.add(card);
            }
            
            Card card = new Card();
            card.Name = "Ace";
            card.Type = CardTypes[i];
            card.Value = 11;
            card.isAce = true;
            
            Deck.add(card);
        }
        
        return Deck;
    }
    
}