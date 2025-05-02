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

    public ReportScreen(CSVFileProcessor csvFileProcessor) {
        Option monthToDate = new Option("1", "Month To Date");
        Option previousMonth = new Option("2", "Previous Month");
        Option yearToDate = new Option("3", "Year To Date");
        Option previousYear = new Option("4", "Previous Year");
        Option searchByVendor = new Option("5", "Search By Vendor");
        Option back = new Option("6", "Back");
        addOption(monthToDate);
        addOption(previousMonth);
        addOption(yearToDate);
        addOption(previousYear);
        addOption(searchByVendor);
        addOption(back);
        setName("Reports");
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
    public CSVFileProcessor getCsvFileProcessor() {
        return csvFileProcessor;
    }

    public void setCsvFileProcessor(CSVFileProcessor csvFileProcessor) {
        this.csvFileProcessor = csvFileProcessor;
    }

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
    private void displayMonthToDateReport() {
        List<Record> records = readFileAndMap();
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);
        List<Record> filteredRecords = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            LocalDate recordDate = records.get(i).getDate();
            if (!recordDate.isBefore(firstDayOfMonth) && !recordDate.isAfter(now)) {
                filteredRecords.add(records.get(i));
            }
        }
        displayRecords(filteredRecords, "There is no data for month to date. ");
    }
    private void displayPreviousMonthReport() {
        List<Record> records = readFileAndMap();
        LocalDate firstDayOfPreviousMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        int numberOfDaysOfPreviousMonth = firstDayOfPreviousMonth.lengthOfMonth();
        LocalDate lastDayOfPreviousMonth = firstDayOfPreviousMonth.withDayOfMonth(numberOfDaysOfPreviousMonth);
        List<Record> filteredRecords = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            LocalDate recordDate = records.get(i).getDate();
            if (!recordDate.isBefore(firstDayOfPreviousMonth) && !recordDate.isAfter(lastDayOfPreviousMonth)) {
                filteredRecords.add(records.get(i));
            }
        }
        displayRecords(filteredRecords, "There is no data for previous month. ");
    }
    private void displayYearToDateReport() {
        List<Record> records = readFileAndMap();
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfYear = now.withDayOfYear(1);
        List<Record> filteredRecords = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            LocalDate recordDate = records.get(i).getDate();
            if (!recordDate.isBefore(firstDayOfYear) && !recordDate.isAfter(now)) {
                filteredRecords.add(records.get(i));
            }
        }
        displayRecords(filteredRecords, "There is no data for year to date. ");
    }
    private void displayPreviousYearReport() {
        List<Record> records = readFileAndMap();
        LocalDate firstDayOfPreviousYear = LocalDate.now().minusYears(1).withDayOfYear(1);
        int numberOfDaysOfPreviousYear = firstDayOfPreviousYear.lengthOfYear();
        LocalDate lastDayOfPreviousYear = firstDayOfPreviousYear.withDayOfYear(numberOfDaysOfPreviousYear);
        List<Record> filteredRecords = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            LocalDate recordDate = records.get(i).getDate();
            if (!recordDate.isBefore(firstDayOfPreviousYear) && !recordDate.isAfter(lastDayOfPreviousYear)) {
                filteredRecords.add(records.get(i));
            }
        }
        displayRecords(filteredRecords, "There is no data for previous year. ");
    }
    private void displaySearchByVendor(Scanner scanner) {
        System.out.println("Enter the vendor name: ");
        String vendorName = scanner.nextLine();
        List<Record> records = readFileAndMap();
        List<Record> filteredRecords = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getVendor().toLowerCase().trim().equals(vendorName.toLowerCase().trim())) {
                filteredRecords.add(records.get(i));
            }
        }
        displayRecords(filteredRecords, "There is no data for that vendor");
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

    private void activateLedgerScreen(LedgerScreen ledgerScreen) {
        ledgerScreen.setActive(true);
        setActive(false);
    }
}
