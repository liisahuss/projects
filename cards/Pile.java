package cardutils;

import java.util.ArrayList;
import java.util.Collection;
public class Pile {
    /**
     * Privat lista som håller reda på korten i högen.
     * Listan lagrar korten som läggs till i högen och manipuleras när kort tas bort
     * eller läggs till.
     * Den är privat så att ingen annan kod direkt kan ändra på listan utan
     * att använda de metoder som definieras i klassen -> inkapsling.
     */
    private ArrayList<Card> theCards;

    /**
     * Konstruktoren allokerar minne för en ny ArrayList-instans som
     * ska hålla en samling av objekt av typen Card.
     */
    public Pile() {
        this.theCards = new ArrayList<>();
    }
    // tom hög av kort

    /**
     * Den här konstruktorn tar emot en lista med kort (Pile) och skapar en kopia av den
     * listan för att initiera högen. Detta görs för att skydda den interna representationen av
     * korten, så att om någon ändrar på den ursprungliga listan, så påverkas inte högen.
     */
    public Pile(ArrayList<Card> Pile) {
        this.theCards = new ArrayList<>(Pile);
    }//kopia, skydda interna representationen

    public int getSize() {
        return theCards.size();
    }

    public void clear() {
        theCards.clear();
    }

    public void add(Card c) {
        theCards.add(c);
    }

    public void add(Collection<Card> cards) {
        theCards.addAll(cards);
    }
    //lägger till en samling kort

    public Card get(int index) {
        return theCards.get(index);
    }

    public Card remove(int index) {
        return theCards.remove(index);
    }
    public boolean remove(Card c){
        return theCards.remove(c);
    }
    public boolean contains(Card c){
        return theCards.contains(c);
    }

    public boolean remove(ArrayList<Card> cards){
        return theCards.removeAll(cards);
    }

    /**
     * Den här metoden returnerar en skyddad kopia av listan theCards.
     * Genom att skapa en ny ArrayList från theCards undviker direkt
     * tillgång till den interna representationen av korten,
     * -> inkapsling och skyddar data från att ändras utanför klassen.
     */
    public ArrayList<Card> getCards() {
        return new ArrayList<>(theCards); // Skyddad kopia
    }

    /**
     * Räknar och returnerar antalet kort i leken som har den angivna färgen (Suit).
     * @param s Färgen (Suit) som ska räknas.
     * @return Antalet kort med färgen s.
     */
    public int noOfSuit(Suit s) {
        int count = 0;
        for (Card c : theCards) {
            if (c.getSuit() == s) count++;
        }
        return count;
    }

    /**
     * Räknar och returnerar antalet kort i leken som har den angivna valören (Rank).
     * @param r Valören (Rank) som ska räknas.
     * @return Antalet kort med valören r.
     */
    public int noOfRank(Rank r) {
        int count = 0;
        for (Card c : theCards) {
            if (c.getRank() == r) count++;
        }
        return count;
    }

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


