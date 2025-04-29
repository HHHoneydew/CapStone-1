import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppCapstone {
    public static void main(String[] args) {
        Homescreen homeScreen = new Homescreen();
        LedgerScreen ledgerScreen = new LedgerScreen();
        ReportScreen reportscreen = new ReportScreen();
        String choice = null;
        while(choice == null || choice != "x" || choice != "X") {
          if (homeScreen.isActive()) {
              homeScreen.displayOption();
          } else if (ledgerScreen.isActive()) {
              ledgerScreen.displayOption();
          } else {
              reportscreen.displayOption();
          }
        }
    }
}


