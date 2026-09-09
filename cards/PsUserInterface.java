import cardutils.Card;
import psmodel.PsLogic;
import java.util.Scanner;
public class PsUserInterface {
    private PsLogic psLogic; // Interfacet används för låg koppling, blir lågt kopplad till spelets logik.
    private Scanner scan;

    /**
     * Konstruktor skapar ny instans för spellogiken PsLogic
     * och en scanner för användarval
     */
    public PsUserInterface(){
        this.psLogic = new PsLogic();
        this.scan = new Scanner(System.in);
    }

    /**
     * Kör spelets huvudloop där användaren kan välja att starta ett nytt spel (N)
     * eller avsluta programmet (X). Menyn visas upprepade gånger tills användaren väljer att avsluta.
     */
    public void run(){
        char choice = ' ';
        String answer;

        do{
            printMenu();
            answer = scan.nextLine();
            answer = answer.toUpperCase();
            choice = answer.charAt(0);

            switch (choice){
                case 'N':
                    newGame();
                    break;
                case 'X':
                    System.out.println("Bye bye!");
                    break;
                default:
                    System.out.println("Unknown command");
            }
        }while(choice != 'X');
    }

    /**
     * Startar en ny spelomgång genom att återställa spelet och dra kort tills det är över.
     * Visar spelets status och poäng när rundan är slut.
     */
    public void newGame(){
        System.out.println("Initializing a new game...");
        psLogic.initNewGame();
        System.out.println("Ready.");

        while(!psLogic.isGameOver()){
            pickACard();
        }
        System.out.println(psLogic.toString());
        System.out.println("Game is over!");
        System.out.println("You got " + psLogic.getPoints() + " points.");
    }

    /**
     * Drar ett nytt kort från kortleken, visar spelets aktuella status och låter spelaren
     * välja en hög att placera kortet i.
     */
    public void pickACard(){
        Card nextCard = psLogic.pickNextCard();
        System.out.println(psLogic.toString());
        System.out.println("Next card: " + nextCard.toShortString());
        System.out.println("Select a pile [0...4]: ");
        String ans = scan.nextLine();
        int index = Integer.parseInt(ans.trim());
        psLogic.addCardToPile(index);
    }

    /**
     * Skriver ut menyn så användaren vet vilka val som finns
     */
    public void printMenu(){
        System.out.println("---Menu---");
        System.out.println("N - Start a new game");
        System.out.println("X - Exit the game");
        System.out.println("----------");
    }
}

