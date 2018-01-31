package sample.snakesAndLadders;

import java.util.Random;

class Dice {


    private Random random;

    //defualt constructor that intializes random
    public Dice() {
        random = new Random();
    }


    /**
     * Rolls a D6 and returns the value.
     *
     * @return An random int value between 1-6 (inclusive)
     */
    public int roll_a_number() {
        return random.nextInt(6) + 1;
    }
}