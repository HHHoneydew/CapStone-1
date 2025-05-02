import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReportScreen {
    private String name = null;
    private List<Option> options;
    private boolean isActive = false;
    private CSVFileProcessor csvFileProcessor;

    // Constructors for initialize ReportScreen instance
    public ReportScreen(CSVFileProcessor csvFileProcessor) {
        // Define default option
        Option monthToDate = new Option("1", "Month To Date");
        Option previousMonth = new Option("2", "Previous Month");
        Option yearToDate = new Option("3", "Year To Date");
        Option previousYear = new Option("4", "Previous Year");
        Option searchByVendor = new Option("5", "Search By Vendor");
        Option back = new Option("6", "Back");

        // Add default option
        addOption(monthToDate);
        addOption(previousMonth);
        addOption(yearToDate);
        addOption(previousYear);
        addOption(searchByVendor);
        addOption(back);

        // Set screen name to Reports
        setName("Reports");

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

    // Add more option manually to the options list
    public void addOption(Option option) {
        if (this.options == null) {
            // If there is no options, create empty list for options before adding option
            this.options = new ArrayList<>();
        }

        // Add option to options list
        this.options.add(option);
    }

    // Process user input based on their input
    public String processUserInput(Scanner scanner, LedgerScreen ledgerScreen) {
        String choice = scanner.nextLine();
        if (choice.toLowerCase().equals("1")) {
            displayMonthToDateReport();
        } else if (choice.toLowerCase().equals("2")) {
            displayPreviousMonthReport();
        } else if (choice.toLowerCase().equals("3")) {
            displayYearToDateReport();
        } else if (choice.toLowerCase().equals("4")) {
            displayPreviousYearReport();
        } else if (choice.toLowerCase().equals("5")) {
            displaySearchByVendor(scanner);
        } else if (choice.toLowerCase().equals("6")) {
            activateLedgerScreen(ledgerScreen);
        }
        return choice;
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

    private void displayMonthToDateReport() {
        // Get all records from file and empty filtered record list
        List<Record> records = readFileAndMap();
        List<Record> filteredRecords = new ArrayList<>();

        // Get today's date
        LocalDate now = LocalDate.now();

        // Get first day of the month
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);

        // Filter the record that is in range from first day of month and today
        for (int i = 0; i < records.size(); i++) {
            // Get record's date
            LocalDate recordDate = records.get(i).getDate();

            // Check if record's date is in range from first day of month and today, if so then add to filtered list
            if (!recordDate.isBefore(firstDayOfMonth) && !recordDate.isAfter(now)) {
                filteredRecords.add(records.get(i));
            }
        }

        // Display records after filtered with appropriate message
        displayRecords(filteredRecords, "There is no data for month to date. ");
    }

    private void displayPreviousMonthReport() {
        // Get all records from file and empty filtered record list
        List<Record> records = readFileAndMap();
        List<Record> filteredRecords = new ArrayList<>();

        // Get first day of the previous month, length of previous month and last day of previous month (either 28th/29th - If february, 30 or 31st)
        LocalDate firstDayOfPreviousMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        int numberOfDaysOfPreviousMonth = firstDayOfPreviousMonth.lengthOfMonth();
        LocalDate lastDayOfPreviousMonth = firstDayOfPreviousMonth.withDayOfMonth(numberOfDaysOfPreviousMonth);

        // Filter the record that is in range from first day of previous and last day of the previous month
        for (int i = 0; i < records.size(); i++) {
            // Get record's date
            LocalDate recordDate = records.get(i).getDate();

            // Check if record's date is in range, then add to filtered list
            if (!recordDate.isBefore(firstDayOfPreviousMonth) && !recordDate.isAfter(lastDayOfPreviousMonth)) {
                filteredRecords.add(records.get(i));
            }
        }

        // Display records after filtered with appropriate message
        displayRecords(filteredRecords, "There is no data for previous month. ");
    }

    private void displayYearToDateReport() {
        // Get all records from file and empty filtered record list
        List<Record> records = readFileAndMap();
        List<Record> filteredRecords = new ArrayList<>();

        // Get current date and first day of the year
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfYear = now.withDayOfYear(1);

        // Filter the record that is in range from first day of the year until today's date.
        for (int i = 0; i < records.size(); i++) {
            // Get record's date
            LocalDate recordDate = records.get(i).getDate();

            // Check if record's date is in range, then add to filtered list
            if (!recordDate.isBefore(firstDayOfYear) && !recordDate.isAfter(now)) {
                filteredRecords.add(records.get(i));
            }
        }

        // Display records after filtered with appropriate message
        displayRecords(filteredRecords, "There is no data for year to date. ");
    }

    private void displayPreviousYearReport() {
        // Get all records from file and empty filtered record list
        List<Record> records = readFileAndMap();
        List<Record> filteredRecords = new ArrayList<>();

        // Get first day of the previous year, length of the previous year (either 365 days, or 366 days) and the last day of the previous year.
        LocalDate firstDayOfPreviousYear = LocalDate.now().minusYears(1).withDayOfYear(1);
        int numberOfDaysOfPreviousYear = firstDayOfPreviousYear.lengthOfYear();
        LocalDate lastDayOfPreviousYear = firstDayOfPreviousYear.withDayOfYear(numberOfDaysOfPreviousYear);

        // Filter the record that is in range from first day of the previous year until last day of the previous year.
        for (int i = 0; i < records.size(); i++) {
            // Get record's date
            LocalDate recordDate = records.get(i).getDate();

            // Check if record's date is in range, then add to filtered list
            if (!recordDate.isBefore(firstDayOfPreviousYear) && !recordDate.isAfter(lastDayOfPreviousYear)) {
                filteredRecords.add(records.get(i));
            }
        }

        // Display records after filtered with appropriate message
        displayRecords(filteredRecords, "There is no data for previous year. ");
    }

    private void displaySearchByVendor(Scanner scanner) {
        // Prompt user to enter vendor name
        System.out.println("Enter the vendor name: ");
        String vendorName = scanner.nextLine();

        // Get all records from file and empty filtered record list
        List<Record> records = readFileAndMap();
        List<Record> filteredRecords = new ArrayList<>();

        // Filter the record that have the same vendor as user input, if so then add to filtered list
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getVendor().toLowerCase().trim().equals(vendorName.toLowerCase().trim())) {
                filteredRecords.add(records.get(i));
            }
        }

        // Display records after filtered with appropriate message
        displayRecords(filteredRecords, "There is no data for that vendor");
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

    private void activateLedgerScreen(LedgerScreen ledgerScreen) {
        // Deactivate current screen
        setActive(false);

        // Activate ledger screen
        ledgerScreen.setActive(true);
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
