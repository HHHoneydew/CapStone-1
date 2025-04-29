import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Homescreen {
    private String name = null;
    private List<Option> options;
    private boolean isActive = false;

    public Homescreen () {
        Option deposit = new  Option("D", "Add Deposit");
        Option payment = new  Option("P", "Make Payment (Debit)");
        Option ledger = new  Option("L", "Ledger");
        Option exit = new  Option("X", "Exit");
        addOption(deposit);
        addOption(payment);
        addOption(ledger);
        addOption(exit);
        setName("Home");
        setActive(true);
    }
    public String processUserInput(Scanner scanner, LedgerScreen ledgerScreen) {
        String choice = scanner.nextLine();
        if (choice.toLowerCase().equals("d")) {
            // perform deposit
        } else if (choice.toLowerCase().equals("p")) {
            // perform payment
        } else if (choice.toLowerCase().equals("l")) {
            activateLedgerScreen(ledgerScreen);
        }
        return choice;
    }
    public void displayOption() {
        System.out.println(this.name + ":" );
        if (this.options == null || this.options.isEmpty()) {
            System.out.println("No options available for " + this.name);
        }else {
            for (int i = 0; i < options.size(); i++){
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

    private void activateLedgerScreen(LedgerScreen ledgerScreen) {
        ledgerScreen.setActive(true);
        setActive(false);
    }
}
