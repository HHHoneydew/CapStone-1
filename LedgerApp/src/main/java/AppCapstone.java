import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AppCapstone {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String tranasctionFilePath = "C:\\pluralsight\\LearnToCodeAcademyCapstones\\CapStone-1\\LedgerApp\\src\\assets\\transactions.csv";
        String[] headerFields = { "date", "time", "description", "vendor", "amount"};
        CSVFileProcessor fileProcessor = new CSVFileProcessor(tranasctionFilePath, Arrays.asList(headerFields));
        fileProcessor.createFile();
        Homescreen homeScreen = new Homescreen(fileProcessor);
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


