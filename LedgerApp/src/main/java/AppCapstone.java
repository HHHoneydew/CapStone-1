import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppCapstone {
    public static void main(String[] args) {
        Homescreen homeScreen = new Homescreen();
        homeScreen.displayOption();
        LedgerScreen ledgerScreen = new LedgerScreen();
        ledgerScreen.displayOption();
        ReportScreen reportscreen = new ReportScreen();
        reportscreen.displayOption();

    }
}


