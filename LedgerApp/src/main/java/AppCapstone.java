import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppCapstone {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Homescreen homeScreen = new Homescreen();
        LedgerScreen ledgerScreen = new LedgerScreen();
        ReportScreen reportscreen = new ReportScreen();
        String choice = null;
        while(true) {
            if(choice != null && choice.toLowerCase().equals("x")) {
                break;
            }
          if (homeScreen.isActive()) {
              homeScreen.displayOption();
              choice = homeScreen.processUserInput(scanner, ledgerScreen);
          } else if (ledgerScreen.isActive()) {
              ledgerScreen.displayOption();
              choice = ledgerScreen.processUserInput(scanner, reportscreen, homeScreen);
          } else {
              reportscreen.displayOption();
            choice = reportscreen.processUserInput(scanner, ledgerScreen);
          }
        }
        System.out.println("Thanks Have A Good Day!");
    }
}


