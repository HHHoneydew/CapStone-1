import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
public class Homescreen {
    private String name = null;
    private List<Option> options;
    private boolean isActive = false;
    private CSVFileProcessor csvFileProcessor;

    public Homescreen (CSVFileProcessor csvFileProcessor) {
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
        setCsvFileProcessor(csvFileProcessor);
    }
    public String processUserInput(Scanner scanner, LedgerScreen ledgerScreen) {
        String choice = scanner.nextLine();
        if (choice.toLowerCase().equals("d")) {
            deposit(scanner);
        } else if (choice.toLowerCase().equals("p")) {
            payment(scanner);
        } else if (choice.toLowerCase().equals("l")) {
            activateLedgerScreen(ledgerScreen);
        }
        return choice;
    }
    private void deposit(Scanner scanner) {
        String userInformation = promptDepositOrPayment(true, scanner);
        csvFileProcessor.writeToFile(userInformation);
    }
    private void payment(Scanner scanner) {
       String userInformation =  promptDepositOrPayment(false,scanner);
        csvFileProcessor.writeToFile(userInformation);
    }
    private String promptDepositOrPayment(boolean isDeposit, Scanner scanner) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = promptDate(scanner,dateFormatter);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = promptTime(scanner, timeFormatter);
        String description = promptString(scanner,"description");
       String vendor = promptString(scanner, "vendor");
       Double amount = promptAmouunt(scanner, isDeposit);
       return convertUserInfoToString(date, time, description, vendor, amount, dateFormatter, timeFormatter);
    }
    private String convertUserInfoToString(LocalDate date, LocalTime time, String description, String vendor, Double amount, DateTimeFormatter dateFormatter,DateTimeFormatter timeFormatter ) {
        String formattedDate = date.format(dateFormatter);
        String formattedTime= time.format(timeFormatter);
        String formattedAmount = Double.toString(amount);
        return formattedDate + " | " + formattedTime + " | " + description + " | " + vendor + " | " + formattedAmount;
    }
    private LocalDate promptDate(Scanner scanner, DateTimeFormatter dateFormatter) {
       LocalDate date = null;
       while (date == null) {
           System.out.println("Enter the date");
           String dateInput =  scanner.nextLine();
           try {
               date = LocalDate.parse(dateInput, dateFormatter);
           }catch (DateTimeParseException e) {
               System.out.println("Invalid date format, try again! (yyyy-MM-dd)");
           }
       }
       return date;
    }
    private LocalTime promptTime(Scanner scanner, DateTimeFormatter timeFormatter) {
        LocalTime time = null;
        while (time == null) {
            System.out.println("Enter the time");
            String timeInput = scanner.nextLine();
            try {
                time = LocalTime.parse(timeInput, timeFormatter);
            }catch (DateTimeParseException e) {
                System.out.println("Invalid time format, try again! (HH:mm:ss)");
            }
        }
        return time;
    }
    private String promptString(Scanner scanner, String entity) {
        System.out.println("Enter the " + entity);
        return scanner.nextLine();
    }
    private Double promptAmouunt(Scanner scanner, boolean isDeposit) {
        Double amount = null;
        while (amount == null) {
            try {
                System.out.println("Enter the amount");
                Double amountInput = scanner.nextDouble();
                if (amountInput >= 0) {
                    amount = amountInput;
                } else {
                    System.out.println("Invalid value. Value must be numeric and greater or equal to 0");
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid value. Value must be numeric and greater or equal to 0");
            }
        }
        if (!isDeposit) {
            amount *= -1;
        }
        return amount;
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
    public CSVFileProcessor getCsvFileProcessor() {
        return csvFileProcessor;
    }

    public void setCsvFileProcessor(CSVFileProcessor csvFileProcessor) {
        this.csvFileProcessor = csvFileProcessor;
    }
}
