package sample.snakesAndLadders;

import java.util.Scanner;

public class Player extends Dice {


    private static Dice die = new Dice();
    private String name;
    private boolean hasPowerup;
    private boolean hasDisadv;

    //Initialize the fields
    Player(String players) {
        this.name = players;
    }

    /**
     * Sets hasPowerup to true
     */
    public void earnPowerup() {
        this.hasPowerup = true;
        System.out.println(name + " earned powerup");
    }

    /**
     * Sets hasDisadv to true
     */
    public void earnDisadv() {
        this.hasDisadv = true;
        System.out.println(name + " has disadvantage");
    }


    /**
     * This method plays out the player's turn.
     *
     * @return The number of spaces to move on the board.
     */
    public int takeTurn() {

        //Initialize scanner
        Scanner scan = new Scanner(System.in);
        //Prompt user for roll
        System.out.print(name + "'s turn: ");
        String input = scan.nextLine();

        //Translate the input to some numeric value between 1 and 9
        int val = 0;
        for (int idx = 0; idx < input.length(); idx++) {
            val += input.charAt(idx);
        }
        val = val % 10;
        if (val == 0) {
            val = 1;
        }

        //Throw away the next 'val' random values
        for (int idx = 0; idx < val; idx++) {
            die.roll_a_number();
        }

        //Get the roll
        int roll;
        if (hasPowerup) {
            //If the player has the powerup, roll 2D6
            roll = die.roll_a_number() + die.roll_a_number();
            hasPowerup = false;
            System.out.print("Using powerup. ");
        } else if (hasDisadv) {
            //If the player has disadvantage, then roll two
            //D6's and take the lower value
            int first = die.roll_a_number();
            int second = die.roll_a_number();

            if (first > second) {
                roll = second;
            } else {
                roll = first;
            }
            hasDisadv = false;
            System.out.print("Using disadvantage. ");
        } else {
            //Otherwise, roll normally
            roll = die.roll_a_number();
        }

        System.out.println(name + " rolled " + roll + ".");

        return roll;
    }

    //prints all the formatting into a String Chracter.
    public String toString() {
        return name;
    }
}