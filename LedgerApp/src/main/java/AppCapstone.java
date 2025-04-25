import java.util.Scanner;

public class AppCapstone {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

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


