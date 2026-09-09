package se.kth.liisabella.labb4.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuUtilities {
    public enum SudokuLevel {EASY, MEDIUM, HARD}
    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;

    private static final Random random = new Random();

    public static int[][][] generateSudokuMatrix(SudokuLevel level) {
        String original;
        switch (level) {
            case EASY: original = easy; break;
            case MEDIUM: original = medium; break;
            case HARD: original = hard; break;
            default: original = medium;
        }

        // Skapa variationer
        List<String> variations = new ArrayList<>();
        variations.add(original);
        variations.add(mirrorHorizontally(original));
        variations.add(mirrorVertically(original));
        variations.add(mirrorHorizontally(mirrorVertically(original)));

        // Välj en slumpmässig version
        String chosen = variations.get(random.nextInt(variations.size()));

        return convertStringToIntMatrix(chosen);
    }

    static int[][][] convertStringToIntMatrix(String stringRepresentation) {
        if (stringRepresentation.length() != GRID_SIZE * GRID_SIZE * 2)
            throw new IllegalArgumentException("representation length " + stringRepresentation.length());
        int[][][] values = new int[GRID_SIZE][GRID_SIZE][2];
        char[] charRepresentation = stringRepresentation.toCharArray();
        int charIndex = 0;
        // initial values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][0] = convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }
        // solution values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][1] = convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }
        return values;
    }

    private static int convertCharToSudokuInt(char ch) {
        if (ch < '0' || ch > '9') throw new IllegalArgumentException("character " + ch);
        return ch - '0';
    }

    // Horisontell spegelvändning
    private static String mirrorHorizontally(String s) {
        StringBuilder mirrored = new StringBuilder();
        for (int row = 0; row < 9; row++) {
            int start = row * 9;
            for (int col = 8; col >= 0; col--) {
                mirrored.append(s.charAt(start + col));
            }
        }
        // samma sak för lösningen
        for (int row = 0; row < 9; row++) {
            int start = 81 + row * 9;
            for (int col = 8; col >= 0; col--) {
                mirrored.append(s.charAt(start + col));
            }
        }
        return mirrored.toString();
    }

    // Vertikal spegelvändning
    private static String mirrorVertically(String s) {
        StringBuilder mirrored = new StringBuilder();
        for (int row = 8; row >= 0; row--) {
            int start = row * 9;
            mirrored.append(s, start, start + 9);
        }
        for (int row = 8; row >= 0; row--) {
            int start = 81 + row * 9;
            mirrored.append(s, start, start + 9);
        }
        return mirrored.toString();
    }

    // Originalsträngar (oförändrade)
    private static final String easy =
            "000914070" +
                    "010000054" +
                    "040002000" +
                    "007569001" +
                    "401000500" +
                    "300100000" +
                    "039000408" +
                    "650800030" +
                    "000403260" +
                    "583914672" +
                    "712386954" +
                    "946752183" +
                    "827569341" +
                    "461238597" +
                    "395147826" +
                    "239675418" +
                    "654821739" +
                    "178493265";

    private static final String medium =
            "300000010" +
                    "000050906" +
                    "050401200" +
                    "030000080" +
                    "002069400" +
                    "000000002" +
                    "900610000" +
                    "200300058" +
                    "100800090" +
                    "324976815" +
                    "718253946" +
                    "659481273" +
                    "536142789" +
                    "872569431" +
                    "491738562" +
                    "985617324" +
                    "267394158" +
                    "143825697";

    private static final String hard =
            "030600000" +
                    "000010070" +
                    "080000000" +
                    "000020000" +
                    "340000800" +
                    "500030094" +
                    "000400000" +
                    "150800200" +
                    "700006050" +
                    "931687542" +
                    "465219378" +
                    "287345916" +
                    "876924135" +
                    "349561827" +
                    "512738694" +
                    "693452781" +
                    "154873269" +
                    "728196453";
}



