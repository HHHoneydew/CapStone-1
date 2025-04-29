import java.util.ArrayList;
import java.util.List;

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

}
