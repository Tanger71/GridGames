package snakes_and_ladders;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SnakesandLadders extends Board{

	public SnakesandLadders(List<Player> players) {
		super(players);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args){
		//Prints welcome message.
		System.out.println("Snakes and Ladders: game by Naod");

		//Initializes for scanner input.
		Scanner scan = new Scanner (System.in);
		
		//Asks you for the number of players, if it is more than 10, then it will ask you again until number between 1 & 10 is given.
		int numPlayers = 0;
		while (numPlayers <= 0 || numPlayers >10){
			System.out.print("Please enter the number of player (1-10): " );
			numPlayers = scan.nextInt();
		}
		
		//Adds all of the players to an index offset by one since the array is zero-based.
		List<Player> players = new ArrayList<Player>();
		for (int idx = 0; idx < numPlayers; idx++){
			Player player = new Player("P" + (idx+1));
			players.add(player);
		}
		
		//Initializes the board which is inherited from the board class.
		Board board = new Board(players);
		
		//Loops thorugh every index of players until a player reaches the winning spot.
		//Players take turns to roll the die and move on the board
		boolean done = false;
		int playerIdx = 0;
		while (!done){
			//Player takes turn
			Player currPlayer = players.get(playerIdx);
			int roll = currPlayer.takeTurn();
			
			//Update the board
			done = board.movePlayer(currPlayer, roll);
			
			//Print the board
			System.out.println(board);
			System.out.println("-----------------------\n");
			
			//If someone
			if (done){
				System.out.println(currPlayer + " wins");
			}
			
			//Sets up for the cycle of turns to restart.
			playerIdx++;
			if (playerIdx == numPlayers){
				playerIdx = 0;
			}
			
		}
	}
}
