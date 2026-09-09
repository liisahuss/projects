package cardutils;

import cardutils.Card;
import cardutils.Deck;

import java.util.ArrayList;

public class DeckMain {

    public static void main(String[] args) {

        Deck deck = new Deck();
        System.out.println(deck);

        deck.shuffleCards();
        System.out.println(deck);

        // deal 5 cards to a "hand"
        ArrayList<Card> hand = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Card c = deck.dealCard();
            System.out.println(c);
            hand.add(c);
        }
        System.out.println(hand);

        // fill the deck
        deck.fill();
        System.out.println(deck);
    }
}