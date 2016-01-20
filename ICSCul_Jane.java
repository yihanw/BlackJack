/*Jane Wang
  ICS3U1_01
  Jun.2.2014
  the program is a game calles black jack, it is a text-based on game, the aim of the game for the player is to get the closest number to 21,
  but not exceed 21.*/
// The "ICSCul_Jane" class.
//http://www.egtry.com/java/awt/draw_save
//http://www.javakode.com/applets/05-keyboardInput/
import java.awt.*;
import java.applet.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.*;
import hsa.Console;

public class ICSCul_Jane
{
    static Console c; // The output console

    public static void main (String[] args)
    {
	c = new Console ();
	String[] cardName = new String [52]; //card names
	int[] cardPoint = new int [52]; //card points
	String playerName; //player's name
	double wager; //player's wager on the game

	//ask about player's information
	c.println ("hello! welcome to the game of BLACK JACK!");
	c.println ("this is a text-based on game");
	c.println ("your rival is the computer");
	c.println ("the aim of this game is to get the closest number to 21 without exceeding it");
	c.println ("you can wage at the beginning of the game between 0 and 10");

	c.readChar ();
	c.println ("what's your name,please?");
	playerName = c.readLine (); //read in player's name
	c.println ();
	c.println ("hello " + playerName + ", how much money would you like to get to wage?");
	wager = c.readDouble (); //read in player's wager
	while (wager < 0 || wager > 10) //focus player to enter a number between 0 and 10
	{
	    c.println ("please enter a number between 0 and 10");
	    wager = c.readDouble (); //read in player's wager
	}
	c.println ();
	c.println ("the game starts, good luck!");
	c.readChar ();

	//initializes card names and card points
	for (int i = 0 ; i < 13 ; i++) //cards of hearts
	{
	    cardName [i] = "heart " + (i + 1); //initializes card names
	    if (i < 10) //initializes card points from ace to 10
	    {
		cardPoint [i] = i + 1;
	    }
	    if (i > 9 && i < 13) //initializes card points from jake to king
	    {
		cardPoint [i] = 10;
	    }
	}
	for (int i = 13 ; i < 26 ; i++) //cards of spade
	{
	    cardName [i] = "spade " + (i - 12); //intializes card names
	    if (i < 23) //initializes card points from ace to 10
	    {
		cardPoint [i] = i - 12;
	    }
	    if (i > 22 && i < 26) //initializes card points from jake to king
	    {
		cardPoint [i] = 10;
	    }
	}
	for (int i = 26 ; i < 39 ; i++) //cards of club
	{
	    cardName [i] = "club " + (i - 25); //initializes card points from 1 to 10
	    if (i < 36) //initializes card points from 1 to 10
	    {
		cardPoint [i] = i - 25;
	    }
	    if (i > 35 && i < 39) //initializes card points from jake to king
	    {
		cardPoint [i] = 10;
	    }
	}
	for (int i = 39 ; i < 52 ; i++) //cards of diamond
	{
	    cardName [i] = "diamond " + (i - 38); //initializes card names
	    if (i < 49) //initializes card points from ace to 10
	    {
		cardPoint [i] = i - 38;
	    }
	    if (i > 48 && i < 52) //initializes card points from jake to king
	    {
		cardPoint [i] = 10;
	    }
	}
	gameProcess (cardName, cardPoint, wager); //call from the gameProcess method
	c.println ();
	// Place your program here.  'c' is the output console
    } // main method


    public static void gameProcess (String[] cardName, int[] cardPoint, double wager)
    {
	String playerCard = ""; //store player's cards
	String dealerCard = ""; //store dealer's cards
	int cardNum = 52; //the number of the cards
	String restCard[] = new String [(cardNum - 1)]; //store the rest cards, in order to give the player and the dealer a different card
	int playerPoint = 0; //initializes palyer's point
	int dealerPoint = 0; //initializes dealer's point
	int randomNum; //a random number
	char playerChoice; //player's chioce on wheather or not to recieve a card
	char dealerChoice; //dealer's choice on wheather or not to recieve a card
	char finalChoice; //choice on wheater or not to continue the game
	double restMoney; //player's rest of money
	char reply; //player's reply on whether or nor he/she wants to paly again
	Vector v = new Vector (); //initializes a vector to store player's card images
	Vector u = new Vector (); //initializes a vetor to store back side dealer's card images
	Vector p = new Vector (); //initializes a vetor to store the front side of dealer's card images

	//initialize the card images
	c.setCursor (15, 0); //set the cursor
	Image[] cardImage = new Image [52]; //initializes the card images
	Image back = null;
	for (int i = 0 ; i < 52 ; i++)
	{
	    cardImage [i] = null;
	}
	try
	{
	    for (int i = 0 ; i < 52 ; i++) //read in the card images
	    {
		cardImage [i] = ImageIO.read (new File ("card_images/" + Integer.toString (i) + ".jpg")); //read in the front side of the images
	    }
	    back = ImageIO.read (new File ("card_images/back.jpg")); //read in the back side image
	}
	catch (Exception e)
	{
	    c.println ("Exception caught" + e);
	    e.printStackTrace ();
	}

	//game process
	do
	{
	    //initializes all the variables to zero
	    playerCard = ""; //store player's cards
	    dealerCard = ""; //store dealer's cards
	    playerPoint = 0; //initializes palyer's point
	    dealerPoint = 0; //initializes dealer's point
	    randomNum = 0; //initializes a random number
	    playerChoice = 0; //player's chioce on wheather or not to recieve a card
	    dealerChoice = 0; //dealer's choice on wheather or not to recieve a card
	    finalChoice = 0; //choice on wheater or not to continue the game
	    reply = 0; //player's reply on whether or nor he/she wants to paly again
	    v.clear (); //clear vector v
	    u.clear (); //clear vector u

	    //initializes player's initial cards
	    c.print ("your initial cards are ");
	    for (int i = 0 ; i < 2 ; i++) //randomly give player two initial cards
	    {
		randomNum = (int) (Math.random () * cardNum); //initializes the random number
		c.print (cardName [randomNum] + " "); //displays player's initial card names
		v.add (cardImage [randomNum]); //add the image of the random image
		drawCard_player (v); //display the card images
		playerPoint = playerPoint + cardPoint [randomNum]; //calculate player's point
		playerCard += cardName [randomNum] + " "; //store player's cards
		//initializes restCard by dividing them into two parts, the first part is from 0 to the randomNum,
		//the second part is from the randomNum=1 to the end
		for (int a = 0 ; a < randomNum ; a++) //initializes card number which is less than the random number
		{
		    restCard [i] = cardName [a];
		}
		for (int a = randomNum ; a < (cardNum - 1) ; a++) //initializes card number which is greater than the random number
		{
		    restCard [a] = cardName [a + 1];
		}
		cardNum = cardNum - 1;
	    }
	    c.println ();
	    c.println ("your total point is " + playerPoint); //displayer player's points

	    //initializes dealer's initial cards
	    c.readChar ();
	    c.println ("dealer is given two initial cards");
	    for (int i = 0 ; i < 2 ; i++) //randomly give dealer two initial cards
	    {
		randomNum = (int) (Math.random () * cardNum); //initializes the random number

		dealerPoint = dealerPoint + cardPoint [randomNum]; //calculate player's point
		u.add (back); //add the image of the random card
		p.add (cardImage [randomNum]);
		drawCard_dealer (u); //display card images

		dealerCard += " " + cardName [randomNum]; //store dealer's cards
		//initializes restCard
		for (int b = 0 ; b < randomNum ; b++) //initializes card number which is less than the random number
		{
		    restCard [b] = cardName [b];
		}
		for (int b = randomNum ; b < (cardNum - 1) ; b++) //initializes card number which is greater than the random number
		{
		    restCard [b] = cardName [b + 1];
		}
		cardNum = cardNum - 1;
	    }
	    //loop for player
	    do
	    {
		//player's turn
		c.readChar ();
		c.println ("it's your turn");
		c.println ("do you want to recieve a card? answer with 'y' or 'n'"); //ask player's chioce
		playerChoice = c.readChar (); //read in player's chioce
		while (playerChoice != 'y' && playerChoice != 'n') //focus player to reply with 'y' or 'n'
		{
		    c.println ("please answer with 'y' or 'n'");
		    playerChoice = c.readChar (); //read in player's chioce
		}
		if (playerChoice == 'y') //if the player wish to recieve a card
		{
		    randomNum = (int) (Math.random () * cardNum); //initializes the random number
		    c.println ("your next card is " + cardName [randomNum]); //displayer player's card for next round
		    v.add (cardImage [randomNum]); //add the image of the random card
		    drawCard_player (v); //display card images
		    c.readLine ();
		    playerCard += " " + cardName [randomNum]; //store player's cards
		    playerPoint = playerPoint + cardPoint [randomNum]; //calculate player's points
		    c.println ("your cards are " + playerCard); //display player's cards
		    c.println ("your points are " + playerPoint); //displayer player's cards
		    c.readChar ();
		    //initializes restCard
		    for (int c = 0 ; c < randomNum ; c++) //initializes card number which is less than the random number
		    {
			restCard [c] = cardName [c];
		    }
		    for (int c = randomNum ; c < (cardNum - 1) ; c++) //initializes card number which is greater than the random number
		    {
			restCard [c] = cardName [c + 1];
		    }
		    cardNum = cardNum - 1;
		}
		if (playerChoice == 'n')
		{
		    c.readChar ();
		}

		//dealer's turn
		c.println ("dealer's turn");

		if (dealerPoint < 17) //dealer recieve a card if point is less than 17
		{
		    dealerChoice = 'y';
		    c.println ("dealer decides to recieve a card");
		    randomNum = (int) (Math.random () * cardNum); //initializes the random number
		    dealerCard += " " + cardName [randomNum]; //store dealer's cards
		    u.add (back); //add the image of the random card
		    p.add (cardImage [randomNum]);
		    drawCard_dealer (u); //display card images
		    dealerPoint = dealerPoint + cardPoint [randomNum]; //store dealer's points
		    //initializes restCard
		    for (int d = 0 ; d < randomNum ; d++) //initializes card number which is less than the random number
		    {
			restCard [d] = cardName [d];
		    }
		    for (int d = randomNum ; d < (cardNum - 1) ; d++) //initializes card number which is greater than the random number
		    {
			restCard [d] = cardName [d + 1];
		    }
		    cardNum = cardNum - 1;
		}
		//dealer doesnot recieve a card if points is greater or equal than 17
		else
		{
		    dealerChoice = 'n';
		    c.println ("dealer does not want to recieve a card");
		}

		//judge if the game ends
		if (playerChoice == 'n' && dealerChoice == 'n') //if player and dealer both choose not to recieve a card
		{
		    finalChoice = 'y';
		    c.println ("the game ends"); //the game ends
		}
		else //if either of them choose to recieve a card
		{
		    finalChoice = 'n';
		}
	    }
	    while (finalChoice == 'n'); //decide at the end


	    //calculate the money of the palyer and display the result
	    c.readChar ();
	    c.println ("your cards are " + playerCard);
	    drawCard_player (v); //display card images
	    c.readChar ();
	    c.println ("your final point is " + playerPoint);
	    c.readChar ();
	    c.println ("dealer's cards are " + dealerCard);
	    drawCard_dealerFront (p); //display card images
	    c.readChar ();
	    c.println ("dealer's final point is " + dealerPoint);
	    c.readChar ();

	    if (playerPoint > 21) //player loses if he/she busts
	    {
		restMoney = 10 - 2 * wager; //calculate player's wager
		c.println ("you loses! :("); //display the result
		c.println ("your money is decreased by double of your wager"); //display the money status
	    }
	    else
	    {
		if (dealerPoint > 21) //if dealer busts, player wins
		{
		    restMoney = 10 + 3 * wager; //calculate player's wager
		    c.println ("congratulations! you win! :)"); //diaplay the result
		    c.readChar ();
		    c.println ("your money is increased by triple of your wager"); //display the money status
		}
		else
		{
		    if (playerPoint > dealerPoint) //if player's point is greater than dealer's point, player wins
		    {
			restMoney = 10 + 2 * wager; //calculate player's wager
			c.println ("congratulations! you win!");
			c.readChar ();
			c.println ("your money is increased by double of your wager");
		    }
		    else
			if (playerPoint == dealerPoint) //if the points are equal, no one wins
			{
			    restMoney = 10; //calculate player's wager
			    c.println ("no one wins"); //display the result
			    c.readChar ();
			}
			else //player loses
			{
			    restMoney = 10 - 2 * wager; //calculate player's wager
			    c.println ("you loses"); //diaplay the result
			    c.readChar ();
			    c.println ("your money is decreased by double of your wager"); //display the money status
			}
		}
	    }
	    c.readChar ();
	    c.println ("your rest money is " + restMoney); //print player's money status
	    c.readChar ();
	    feedBack (restMoney);
	    c.println ();

	    //ask player's choice on whether or nor to play again
	    c.println ("do you wanna play again? reply with 'y' or 'n'");
	    reply = c.readChar (); //read in player's choice
	    while (reply != 'y' && reply != 'n') //focus player to enter with 'y' or 'n'
	    {
		c.println ("please answer with 'y' or 'n'");
		reply = c.readChar (); //read in the reply
	    }
	    c.readChar ();
	    if (reply == 'y') //if player wish to play again, judge his/her rest money
	    {
		if (restMoney > 0) //can play again
		{
		    c.println ("hope you enjoy!");
		    c.readChar ();
		    c.println ("how much would you like to wage this time?"); //ask player about the wager
		    wager = c.readDouble (); //read in the wager
		    c.readChar ();
		    while (wager > restMoney) //focus the player to enter a significant number
		    {
			c.print ("please enter a number between 0 and " + restMoney);
			wager = c.readDouble (); //read in the wager
			c.readChar ();
		    }
		}
		else //play doesnot have enough money to support the game
		{
		    c.println ("oops! you donot have enough money to support the game!");
		    c.println ("sorry! and have a nice day!");
		}
	    }
	    else //repeat the game
	    {
		c.println ("have a nice day!");
	    }
	}
	while (reply == 'y' && restMoney > 0); //repeat the game process
    }


    public static void drawCard_player (Vector v)  //set the positions for player's card images
    {
	int x = 0; //x-coordinates for images of player's cards
	for (int i = 0 ; i < v.size () ; i++)
	{
	    c.drawImage ((Image) v.elementAt (i), x, 0, null); //draw the images
	    x += 70; //ready for next position
	}

    }


    public static void drawCard_dealer (Vector u)  //set the positions for dealer's card images
    {
	int y = 0; //x-coordinates for images of the dealer's cards
	for (int i = 0 ; i < u.size () ; i++)
	{
	    c.drawImage ((Image) u.elementAt (i), y, 100, null); //draw the back side
	    y += 70; //ready for next position
	}
    }


    public static void drawCard_dealerFront (Vector p)
    {
	int z = 0;
	for (int i = 0 ; i < p.size () ; i++)
	{
	    c.drawImage ((Image) p.elementAt (i), z, 100, null); //draw the back side
	    z += 70; //ready for next position
	}
    }



    public static void feedBack (double restMoney)  //display the feed back for player
    {
	String[] feedBack = new String [6]; //assigns the feed back
	feedBack [0] = "need to play hard!";
	feedBack [1] = "not bad!";
	feedBack [2] = "good job!";
	feedBack [3] = "you are so smart!";
	feedBack [4] = "wow! that's really amazing!";
	feedBack [5] = "I have never seen that such a high mark!!";
	if (restMoney < 0) //display the feed back based on the rest money
	{
	    c.println (feedBack [0]);
	} //display the feed back
	else
	    if (restMoney < 5)
	    {
		c.println (feedBack [1]);
	    } //display the feed back
	    else
		if (restMoney < 10)
		{
		    c.println (feedBack [2]);
		} //display the feed back
		else
		    if (restMoney < 20)
		    {
			c.println (feedBack [3]);
		    } //display the feed back
		    else
			if (restMoney < 30)
			{
			    c.println (feedBack [4]);
			} //display the feed back
			else
			{
			    c.println (feedBack [5]);
			} //display the feed back
    }



} // ICSCul_Jane class
