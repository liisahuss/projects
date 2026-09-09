package cardutils;

import java.util.ArrayList;

public class PileMain {

    public static void main(String[] args) {

        // Create a new deck and shuffle it
        Deck deck = new Deck();
        deck.shuffleCards();

        // Create two hands in a card game
        Pile hand1 = new Pile();
        Pile hand2 = new Pile();

        // Deal five cards to each hand
        for (int i = 0; i < 5; i++) {
            hand1.add(deck.dealCard());
            hand2.add(deck.dealCard());
        }

        // Print both hands
        System.out.println("First hand: " + hand1);
        System.out.println("Second hand: " + hand2);

        // Count the number of a specific suit in each hand
        System.out.println("Number of Hearts in First Hand: " + hand1.noOfSuit(Suit.HEARTS));
        System.out.println("Number of Spades in Second Hand: " + hand2.noOfSuit(Suit.SPADES));

        // Move the first card in the first hand to the second hand
        Card cardMoved = hand1.remove(0);
        hand2.add(cardMoved);

        // Print updated hands
        System.out.println("After moving a card...");
        System.out.println("First hand: " + hand1);
        System.out.println("Second hand: " + hand2);



        // Clear both hands
        hand1.clear();
        hand2.clear();
        System.out.println("After clearing both hands...");
        System.out.println("First hand: " + hand1);
        System.out.println("Second hand: " + hand2);
    }
}

