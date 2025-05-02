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

    // Constructors for initialize LedgerScreen instance
    public LedgerScreen(CSVFileProcessor csvFileProcessor) {
        // Define default option
        Option all = new  Option("A", "All");
        Option deposits= new  Option("D", "Deposits");
        Option payments = new  Option("P", "Payments");
        Option reports = new  Option("R", "Reports");
        Option home = new Option("H", "Home");

        // Add default option
        addOption(all);
        addOption(deposits);
        addOption(payments);
        addOption(reports);
        addOption(home);

        // Set screen name to Ledger
        setName("Ledger");

        // Set csv file processor
        setCsvFileProcessor(csvFileProcessor);

        // Set screen activation to false since this is not the first screen when run
        setActive(false);
    }

    // Display option with format '[ButtonKey] Description', example: [D] Add Deposit
    public void displayOption() {
        // Print the name of the screen
        System.out.println(this.name + ":");

        // If the options is empty, then let them know there is no options
        if (this.options == null || this.options.isEmpty()) {
            System.out.println("No options available for " + this.name);
        } else {
            // Display options
            for (int i = 0; i < options.size(); i++) {
                // Get the current option
                Option currentOption = this.options.get(i);

                // Display current option with format
                String display = "[" + currentOption.getInputSymbol() + "] "
                        + currentOption.getDescription();

                // Print the display
                System.out.println(display);

            }
        }
    }

    // Process user input based on their input
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

    public void addOption(Option option) {
        if (this.options == null) {
            this.options = new ArrayList<>();
        }

        this.options.add(option);
    }

    // Read file and map each line to become Record instances
    private List<Record> readFileAndMap() {
        // Read file and get record information as String (For example: 2025-05-01 | 12:18:25 | idk | random thing | 10.25)
        List<String> recordsFromFile = csvFileProcessor.readFile(false);

        // Create empty list of records
        List<Record> records = new ArrayList<>();

        for (int i = 0; i < recordsFromFile.size(); i++) {
            // Split the current record string into parts with separator '|' because we are using '|' as separator
            String[] parts = recordsFromFile.get(i).split("\\|");

            // Map the first part to date
            LocalDate date = LocalDate.parse(parts[0].trim());

            // Map the second part to time
            LocalTime time = LocalTime.parse(parts[1].trim());

            // Map the third part to description
            String description = parts[2].trim();

            // Map the fourth part to vendor
            String vendor = parts[3].trim();

            // Map the fifth part to amount
            Double amount = Double.valueOf(parts[4].trim());

            // Add the record to the records list
            records.add(new Record(date, time, description, vendor, amount));
        }

        // Return the list of records
        return records;
    }

    private void displayRecords(List<Record> records, String noRecordMessage) {
        // If the record list is empty, log the message to let user know there is no record
        if (records.isEmpty()) {
            System.out.println(noRecordMessage);
        }
        else {
            // Print the records information by using its toString method
            for (int i = 0; i < records.size(); i++) {
                System.out.println(records.get(i).toString());
            }
        }
    }

    private void displayAll() {
        // Print the option name ("All Transactions")
        System.out.println("All Transactions");

        // Get record from file
        List<Record> records = readFileAndMap();

        // Display the records with appropriate no record message
        displayRecords(records, "There is no history available");
    }

    // Filter the record list based on deposit or payment condition
    private List<Record> filterDepositAndPayment(List<Record> records, boolean isDeposit ) {
        // Initialize empty filter list
        List<Record> filteredList = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            // Get the current record
            Record currentRecord = records.get(i);

            // If it is deposit then the record's amount must be >= 0 to be added to filtered list and if it is payment then the record's amount must be < 0 to be added to filtered list
            if ((isDeposit && currentRecord.getAmount() >= 0) || (!isDeposit && currentRecord.getAmount() < 0)) {
                filteredList.add(currentRecord);
            }
        }

        // Return the filtered list
        return filteredList;
    }

    private void displayDeposit() {
        // Print the option name ("All Deposits")
        System.out.println("All Deposits");

        // Get all records from file
        List<Record> records = readFileAndMap();

        // Filter the record with deposit
        List<Record> deposit = filterDepositAndPayment(records, true);

        // Display the records with appropriate no record message
        displayRecords(deposit, "There are no deposits");
    }

    private void displayPayment() {
        // Print the option name ("All Payments")
        System.out.println("All Payments");

        // Get all records from file
        List<Record> records = readFileAndMap();

        // Filter the record with payment
        List<Record> payment = filterDepositAndPayment(records, false);

        // Display the records with appropriate no record message
        displayRecords(payment, "There are no payments");
    }

    private void activateReportScreen(ReportScreen reportScreen) {
        // Deactivate the current screen
        setActive(false);

        // Activate report screen
        reportScreen.setActive(true);
    }

    private void activatehomeScreen(Homescreen homescreen) {
        // Deactivate the current screen
        setActive(false);

        // Activate home screen
        homescreen.setActive(true);
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

    public CSVFileProcessor getCsvFileProcessor() {
        return csvFileProcessor;
    }

    public void setCsvFileProcessor(CSVFileProcessor csvFileProcessor) {
        this.csvFileProcessor = csvFileProcessor;
    }

}
