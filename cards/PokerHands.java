package psmodel;

import cardutils.Pile;

public class PokerHands {

    /**
     * Denna konstruktor är privat och används för att förhindra att objekt av klassen PokerHands
     * skapas direkt. Den har ingen implementation, eftersom PokerHands enbart innehåller statiska metoder
     * och inte har några instansvariabler.
     */
    private PokerHands(){}

    /**
     * Bestämmer vilken poängkombination högen har.
     * @param pile den hög som ska utvärderas
     * @return en PokerCombo som representerar den bästa handen i högen
     */
    public static PokerCombo getPokerCombo(Pile pile) {
        if(FourOfAKind(pile)) {
            return PokerCombo.FOUR_OF_A_KIND;
        } else if(ThreeOfAKind(pile)) {
            return PokerCombo.THREE_OF_A_KIND;
        } else if(Flush(pile)){
            return PokerCombo.FLUSH;
        } else if(TwoPairs(pile)) {
            return PokerCombo.TWO_PAIRS;
        } else if(Pair(pile)){
            return PokerCombo.PAIR;
        } else {
            return PokerCombo.NONE;
        }
    }
    private static boolean TwoPairs(Pile pile){
        int count = 0;
        for(int i=0; i<pile.getSize(); i++){
            if(pile.noOfRank(pile.get(i).getRank())==2){ //hämtar valör på index och kollar noOfRank
                count++;
            }
        }
        if(count == 4){
            return true;
        }
        return false;
    }
    private static boolean Pair(Pile pile){
        for(int i = 0; i < pile.getSize(); i++){
            if(pile.noOfRank(pile.get(i).getRank()) == 2) {
                return true;
            }
        }
        return false;
    }
    private static boolean Flush(Pile pile){
        for(int i = 0; i < pile.getSize(); i++){
            if(pile.noOfSuit(pile.get(i).getSuit()) == 5) { //hämtar färg och kollar noOfSuit
                return true;
            }
        }
        return false;
    }

    private static boolean ThreeOfAKind(Pile pile){
        for(int i = 0; i < pile.getSize(); i++){
            if(pile.noOfRank(pile.get(i).getRank()) == 3) {
                return true;
            }
        }
        return false;
    }
    private static boolean FourOfAKind(Pile pile){
        for(int i = 0; i < pile.getSize(); i++){
            if(pile.noOfRank(pile.get(i).getRank()) == 4) {
                return true;
            }
        }
        return false;
    }
}




