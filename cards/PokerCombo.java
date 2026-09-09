package psmodel;
/**
 * Enum som representerar olika typer av pokerhänder och deras tillhörande poängvärde.
 * Varje konstant representerar en pokerkombination och tilldelas ett poängvärde baserat
 * på spelets regler.
 */
public enum PokerCombo {
    NONE(0), PAIR(1), TWO_PAIRS(3), THREE_OF_A_KIND(6), FOUR_OF_A_KIND(16), FLUSH(5);

    private final int points;

    /**
     * Konstruktorn som tilldelar poängvärdet till en specifik pokerkombination.
     * @param points poängvärdet för pokerkombinationen.
     */
    private PokerCombo(int points) {
        this.points = points;
    }

    /**
     * Hämtar poängvärdet för denna pokerkombination.
     * @return poängvärdet för den aktuella pokerkombinationen.
     */
    public int getValue() {
        return points;
    }

}
