import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LedgerScreen {
    private String name = null;
    private List<Option> options;
    private boolean isActive = false;

    public LedgerScreen() {
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
    public String processUserInput(Scanner scanner, ReportScreen reportScreen, Homescreen homescreen) {
        String choice = scanner.nextLine();
        if (choice.toLowerCase().equals("a")) {
            // perform all
        } else if (choice.toLowerCase().equals("d")) {
            // perform deposits
        } else if (choice.toLowerCase().equals("p")) {
            // perform payments
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

}
