package psmodel;

import cardutils.Card;
import cardutils.Pile;
import cardutils.Deck;
import java.util.ArrayList;
import java.util.List;

public class PsLogic implements IPsLogic {
    private Deck deck;
    private List<Pile> piles;
    private Card nextCard; // Lagrar nästa kort som ska läggas till i en hög

    /**
     * Skapar en ny spelinstans genom att initiera kortleken, skapa tomma högar
     * och starta en ny spelomgång.
     */
    public PsLogic() {
        this.deck = new Deck();
        this.piles = new ArrayList<>();
        this.nextCard = null; //inget kort har dragits än
        for(int i = 0; i < 5; i++){
            piles.add(new Pile());
        }
        initNewGame(); //Startar en ny spelomgång direkt vid skapandet av objektet
    }

    /**
     * Initierar en ny spelomgång genom att fylla och blanda kortleken samt
     * tömma alla högar.
     */
    @Override
    public void initNewGame() {
        deck.fill();
        deck.shuffleCards(); // Blanda kortleken
        for (int i = 0; i < 5; i++) { // töm varje hög
            piles.get(i).clear();
        }
    }

    /**
     * Hämtar nästa kort från kortleken om inget kort redan har dragits.
     * Om ett kort redan har dragits, kastas ett IllegalStateException.
     * @return det nästa kortet från kortleken
     * @throws IllegalStateException om ett kort redan har dragits
     */
    @Override
    public Card pickNextCard() throws IllegalStateException {
        if(nextCard == null) { //om det inte redan finns ett kort
            nextCard = deck.dealCard(); // Dra nästa kort från kortleken
            return nextCard;
        }
        throw new IllegalStateException();
    }

    /**
     * Lägger till det tidigare dragna kortet i en av högarna.
     * Om det inte finns ett kort att lägga till eller om högen är full, kastas ett IllegalStateException.
     * @param n index för den hög där kortet ska läggas till
     * @throws IllegalStateException om inget kort har dragits eller om högen är full
     */
    @Override
    public void addCardToPile(int n) throws IllegalStateException {
        if(nextCard != null && piles.get(n).getSize() < piles.size()){
            Pile thisPile = piles.get(n);
            thisPile.add(nextCard);
            nextCard = null; //Nollställ efter man lagt till kortet så nästa kan dras
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Kontrollerar om spelet är över. Spelet är över om alla högar är fulla,
     * vilket innebär att totalt 25 kort har lagts i högarna.
     * @return true om spelet är över, annars false
     */
    @Override
    public boolean isGameOver() {
        // Spelet är över om alla högar är fulla dvs har 25 kort totalt
        if(getCardCount()==25){
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return antalet kort som har dragits från kortleken
     */
    @Override
    public int getCardCount() {
        // Antalet dragna kort är 52 minus antalet kort kvar i kortleken
        return 52 - deck.getSize();
    }

    /**
     * Returnerar en lista med kopior av de interna högarna
     * för att skydda den ursprungliga listan.
     * @return en lista av kopior av högarna
     */
    @Override
    public List<Pile> getPiles() {
        ArrayList<Pile> copy = new ArrayList<>();
        for (Pile pile : piles) {
            copy.add(new Pile(pile.getCards())); // Skapa kopior av högarna, använder kopiorna från Pile
        }
        return copy;
    }

    /**
     * Beräknar och returnerar den totala poängen baserat på pokerhänderna i varje hög.
     * Poängen beräknas genom att använda PokerHands.getPokerCombo för varje hög.
     * @return den totala poängen för alla högar
     */
    @Override //Kompilatorn kontrollerar att metoden faktiskt finns i interface
    public int getPoints() {
        int noOfPoints = 0;
        for(int i = 0; i < 5; i++){
            noOfPoints += PokerHands.getPokerCombo(piles.get(i)).getValue();
        }
        return noOfPoints;
    }

    /** Skapar och returnerar en strängrepresentation av alla högar i spelet. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(getPiles().get(i).toString()).append("\n");
        }
        return sb.toString();
    }
}


