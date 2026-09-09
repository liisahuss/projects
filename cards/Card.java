package cardutils;

public class Card {
    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) { //kortobjekt
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getRankValue() {
        return rank.getValue();
    } //numeriska värdet av rank

    public int getSuitValue() {
        return suit.ordinal(); // default värden, 0, 1, 2, 3
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Card) {
            Card otherCard = (Card) other; // downcast reference type to cardutils.Card
            return this.rank == otherCard.rank && this.suit == otherCard.suit;
        }
        return false;
    } //jämföra om två kort är likadana

    @Override
    public String toString() {
        String info = rank + " of " + suit;
        return info;
    }

    public String toShortString() {
        String info = "";
        switch (rank) {
            case Rank.ACE:
                info += 'A';
                break;
            case Rank.KING:
                info += 'K';
                break;
            case Rank.QUEEN:
                info += 'Q';
                break;
            case Rank.JACK:
                info += 'J';
                break;
            default:
                info += getRankValue();
        }
        switch (suit) {
            case Suit.SPADES:
                info += '\u2660';
                break;
            case Suit.HEARTS:
                info += '\u2764';
                break;
            case Suit.CLUBS:
                info += '\u2663';
                break;
            case Suit.DIAMONDS:
                info += '\u2666';
                break;
            default:
                ;
        }
        return info;
    }
}
