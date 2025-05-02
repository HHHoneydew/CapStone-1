import java.util.Arrays;
import java.util.Scanner;

public class AppCapstone {
    public static void main(String[] args) {
        // Create scanner
        Scanner scanner = new Scanner(System.in);
        // initialize transactions file path and header fields of the file
        String transactionFilePath = "C:\\pluralsight\\LearnToCodeAcademyCapstones\\CapStone-1\\LedgerApp\\src\\assets\\transactions.csv";
        String[] headerFields = { "date", "time", "description", "vendor", "amount"};
        // create the file processor and create file
        CSVFileProcessor fileProcessor = new CSVFileProcessor(transactionFilePath, Arrays.asList(headerFields));
        fileProcessor.createFile();
        // create screens with file processor
        Homescreen homeScreen = new Homescreen(fileProcessor);
        LedgerScreen ledgerScreen = new LedgerScreen(fileProcessor);
        ReportScreen reportscreen = new ReportScreen(fileProcessor);
        // initialize user input
        String choice = null;
        // run the program until the user exits by inputting "x"
        while(true) {
            if(choice != null && choice.toLowerCase().equals("x")) {
                break;
            }
            // if the homescreen is active display the homescreen options and prompt user for input and process user input
          if (homeScreen.isActive()) {
              homeScreen.displayOption();
              choice = homeScreen.processUserInput(scanner, ledgerScreen);
          } else if (ledgerScreen.isActive()) {
              // if the ledger screen is active display the ledger screen options and prompt user for input and process user input
              ledgerScreen.displayOption();
              choice = ledgerScreen.processUserInput(scanner, reportscreen, homeScreen);
          } else {
              // if the report screen is active display the report screen options and prompt user for input and process user input
              reportscreen.displayOption();
            choice = reportscreen.processUserInput(scanner, ledgerScreen);
          }
        }
        System.out.println("Thanks Have A Good Day!");
    }
}


