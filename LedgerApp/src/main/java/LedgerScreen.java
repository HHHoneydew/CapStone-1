import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LedgerScreen {
    private String name = null;
    private List<Option> options;
    private boolean isActive = false;
    private CSVFileProcessor csvFileProcessor;

    public LedgerScreen(CSVFileProcessor csvFileProcessor) {
        Option all = new  Option("A", "All");
        Option deposits= new  Option("D", "Deposits");
        Option payments = new  Option("P", "Payments");
        Option reports = new  Option("R", "Reports");
        Option home = new Option("H", "Home");
        addOption(all);
        addOption(deposits);
        addOption(payments);
        addOption(reports);
        addOption(home);
        setName("Ledger");
        setCsvFileProcessor(csvFileProcessor);
        setActive(false);
    }
    public void displayOption() {
        System.out.println(this.name + ":");
        if (this.options == null || this.options.isEmpty()) {
            System.out.println("No options available for " + this.name);
        } else {
            for (int i = 0; i < options.size(); i++) {
                //  [D] "Add Deposit"
                Option currentOption = this.options.get(i);
                String display = "[" + currentOption.getInputSymbol() + "] "
                        + currentOption.getDescription();
                System.out.println(display);

            }
        }
    }
    public List<Record> readFileAndMap() {
      List<String> recordsFromFile = csvFileProcessor.readFile(false);
      List<Record> records = new ArrayList<>();
      for (int i = 0; i < recordsFromFile.size(); i++) {
          String[] parts = recordsFromFile.get(i).split("\\|");
          LocalDate date = LocalDate.parse(parts[0].trim());
          LocalTime time = LocalTime.parse(parts[1].trim());
          String description = parts[2].trim();
          String vendor = parts[3].trim();
          Double amount = Double.valueOf(parts[4].trim());
          Record record = new Record(date, time, description, vendor, amount);
          records.add(record);
      }
      return records;
    }
    public void displayRecords(List<Record> records, String noRecordMessage){
        if (records.size() == 0) {
            System.out.println(noRecordMessage);
        }
        else {
            for (int i = 0; i < records.size(); i++) {
                System.out.println(records.get(i).toString());
            }
        }
    }
    public void displayAll() {
        System.out.println("All Transactions");
        List<Record> records = readFileAndMap();
        displayRecords(records, "There is no history available");
    }
    public List<Record> filterDepositAndPayment(List<Record> records, boolean isDeposit ) {
        List<Record> filteredList = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            Record currentRecord = records.get(i);
            if ((isDeposit && currentRecord.getAmount() >= 0) || (!isDeposit && currentRecord.getAmount() < 0)) {
                filteredList.add(currentRecord);
            }
        }
        return filteredList;
    }
    public void displayDeposit() {
        System.out.println("All Deposits");
        List<Record> records = readFileAndMap();
        List<Record> deposit = filterDepositAndPayment(records, true);
        displayRecords(deposit, "There are no deposits");
    }
    public void displayPayment() {
        System.out.println("All Payments");
        List<Record> records = readFileAndMap();
        List<Record> payment = filterDepositAndPayment(records, false);
        displayRecords(payment, "There are no payments");
    }
    public void addOption(Option option) {
        if (this.options == null) {
            this.options = new ArrayList<>();
        }

        this.options.add(option);
    }
    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
    public String processUserInput(Scanner scanner, ReportScreen reportScreen, Homescreen homescreen) {
        String choice = scanner.nextLine();
        if (choice.toLowerCase().equals("a")) {
            displayAll();
        } else if (choice.toLowerCase().equals("d")) {
            displayDeposit();
        } else if (choice.toLowerCase().equals("p")) {
            displayPayment();
        } else if (choice.toLowerCase().equals("r")) {
            activateReportScreen(reportScreen);
        } else if (choice.toLowerCase().equals("h")) {
            activatehomeScreen(homescreen);
        }
        return choice;
    }
    private void activateReportScreen(ReportScreen reportScreen) {
        reportScreen.setActive(true);
        setActive(false);
    }
    private void activatehomeScreen(Homescreen homescreen) {
        homescreen.setActive(true);
        setActive(false);
    }
    public CSVFileProcessor getCsvFileProcessor() {
        return csvFileProcessor;
    }

    public void setCsvFileProcessor(CSVFileProcessor csvFileProcessor) {
        this.csvFileProcessor = csvFileProcessor;
    }

}
