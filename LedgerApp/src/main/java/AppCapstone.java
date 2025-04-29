import java.util.Scanner;

public class AppCapstone {
    public static void main(String[] args) {
    // ryan helped me do my initial commit to the new repository
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
    // creating homepage interface
        while(running) {
            System.out.println("\nHome Screen");
            System.out.println("[D] Add Deposit");
            System.out.println("[P] Make a Payment(Debit)");
            System.out.println("[L] Ledger");
            System.out.println("[X] Exit");
            System.out.println("Please Select One: ");

            String choice = scanner.nextLine().toUpperCase();

        }


    }
}


