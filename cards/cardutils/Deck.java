package cardutils;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    /** Skapar en statisk lista (protoDeck) som fungerar som en mall för en fullständig kortlek.
     * Eftersom den är static, är den inte kopplad till någon specifik instans av Deck-klassen,
     * och den skapas bara en gång när klassen laddas.
     */
    private static final ArrayList<Card> protoDeck = new ArrayList<>();
    private final ArrayList<Card> theCards; //används för att spela, kopia

    /**
     * Statisk block som fyller protoDeck med en fullständig kortlek.
     * Det går igenom alla möjliga kombinationer av färg (Suit) och rank (Rank)
     * och skapar ett nytt Card-objekt för varje kombination.
     * Detta sker en gång när klassen laddas och ger oss en "mall" för en kortlek.
     */
    static {
        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                protoDeck.add(new Card(r, s));
            }
        }
    }

    /**
     * Initialisera den spelbara kortleken som fylls
     * med kopiorna av protoDeck
     */
    public Deck() {
        theCards = new ArrayList<>(); //skapar spelbara kortleken
        fill(); //fyller theCards med kopior av korten från protoDeck
    }

    /**
     * tar bort möjliga kort kvar i theCards och
     * adderar korten från protoDeck till spelbara kortleken
     */
    public void fill() {
        theCards.clear();
        theCards.addAll(protoDeck);
    }
    public int getSize() {
        return theCards.size();
    } //antal kort kvar i kortlek

    public Card dealCard() {
        return theCards.remove(theCards.size() - 1);
    }
    //tar bort sista kortet och returnerar det

    public void shuffleCards() {
        Collections.shuffle(theCards);
    } //blandar korten

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        if (!theCards.isEmpty()) {
            builder.append(theCards.get(0).toShortString());
            for (int i = 1; i < theCards.size(); i++) {
                builder.append(", ").append(theCards.get(i).toShortString());
            }
        }
        builder.append("]");

        return builder.toString();
    }
}
