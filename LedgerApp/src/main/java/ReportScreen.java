import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReportScreen {
    private String name = null;
    private List<Option> options;
    private boolean isActive = false;

    public ReportScreen() {
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

    public String processUserInput(Scanner scanner, LedgerScreen ledgerScreen) {
        String choice = scanner.nextLine();
        if (choice.toLowerCase().equals("1")) {
            // month to date
        } else if (choice.toLowerCase().equals("2")) {
            // previous month
        } else if (choice.toLowerCase().equals("3")) {
            // year to date
        } else if (choice.toLowerCase().equals("4")) {
            // previous year
        } else if (choice.toLowerCase().equals("5")) {
            // search by vendor
        } else if (choice.toLowerCase().equals("6")) {
            activateLedgerScreen(ledgerScreen);
        }
        return choice;
    }

    private void activateLedgerScreen(LedgerScreen ledgerScreen) {
        ledgerScreen.setActive(true);
        setActive(false);
    }
}
