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

    // Constructors for initialize Homescreen instance
    public Homescreen (CSVFileProcessor csvFileProcessor) {
        // Define default option
        Option deposit = new  Option("D", "Add Deposit");
        Option payment = new  Option("P", "Make Payment (Debit)");
        Option ledger = new  Option("L", "Ledger");
        Option exit = new  Option("X", "Exit");

        // Add default option
        addOption(deposit);
        addOption(payment);
        addOption(ledger);
        addOption(exit);

        // Set screen name to Home
        setName("Home");

        // Set screen activation to true at initialize because program always start with home screen
        setActive(true);

        // Set the csv file processor
        setCsvFileProcessor(csvFileProcessor);
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
        if (choice.toLowerCase().equals("d")) {
            deposit(scanner);
        } else if (choice.toLowerCase().equals("p")) {
            payment(scanner);
        } else if (choice.toLowerCase().equals("l")) {
            activateLedgerScreen(ledgerScreen);
        }
        return choice;
    }

    // Display option with format '[ButtonKey] Description', example: [D] Add Deposit
    public void displayOption() {
        // Print the name of the screen
        System.out.println(this.name + ":" );

        // If the options is empty, then let them know there is no options
        if (this.options == null || this.options.isEmpty()) {
            System.out.println("No options available for " + this.name);
        } else {
            // Display options
            for (int i = 0; i < options.size(); i++){
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

    private void deposit(Scanner scanner) {
        // Prompt the user to input deposit information
        String userInformation = promptDepositOrPayment(true, scanner);

        // Write the deposit user information to file
        csvFileProcessor.writeToFile(userInformation);
    }
    private void payment(Scanner scanner) {
        // Prompt the user to input payment information
        String userInformation =  promptDepositOrPayment(false,scanner);

        // Write the payment user information to file
        csvFileProcessor.writeToFile(userInformation);
    }

    private String promptDepositOrPayment(boolean isDeposit, Scanner scanner) {
        // Initialize date and time formatter
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Prompt user for information
        LocalDate date = promptDate(scanner,dateFormatter);
        LocalTime time = promptTime(scanner, timeFormatter);
        String description = promptString(scanner,"description");
        String vendor = promptString(scanner, "vendor");
        Double amount = promptAmount(scanner, isDeposit);

        // Convert user information to string so that we can write to file easier
        return convertUserInfoToString(date, time, description, vendor, amount, dateFormatter, timeFormatter);
    }

    private String convertUserInfoToString(LocalDate date, LocalTime time, String description, String vendor, Double amount, DateTimeFormatter dateFormatter,DateTimeFormatter timeFormatter ) {
        // Format the necessary data
        String formattedDate = date.format(dateFormatter);
        String formattedTime= time.format(timeFormatter);
        String formattedAmount = Double.toString(amount);

        // Convert it to string in the form of (date | time | description | vendor | amount)
        return formattedDate + " | " + formattedTime + " | " + description + " | " + vendor + " | " + formattedAmount;
    }

    private LocalDate promptDate(Scanner scanner, DateTimeFormatter dateFormatter) {
        LocalDate date = null;

        // Ask the user to enter date until they get the right format
        while (date == null) {
            System.out.println("Enter the date");
            String dateInput =  scanner.nextLine();

            try {
                // Attempt to parse the date with dateFormatter to validate if user enter the right format
                date = LocalDate.parse(dateInput, dateFormatter);
            } catch (DateTimeParseException e) {
                // If user enter wrong format, let them know and try again.
                System.out.println("Invalid date format, try again! (yyyy-MM-dd)");
            }
        }
        return date;
    }

    private LocalTime promptTime(Scanner scanner, DateTimeFormatter timeFormatter) {
        LocalTime time = null;

        // Ask the user to enter time until they get the right format
        while (time == null) {
            System.out.println("Enter the time");
            String timeInput = scanner.nextLine();

            try {
                // Attempt to parse the time with timeFormatter to validate if user enter the right format
                time = LocalTime.parse(timeInput, timeFormatter);
            }catch (DateTimeParseException e) {
                // If user enter wrong format, let them know and try again.
                System.out.println("Invalid time format, try again! (HH:mm:ss)");
            }
        }
        return time;
    }

    // Prompt the user to enter the information of the entity, such as description, vendor
    private String promptString(Scanner scanner, String entity) {
        System.out.println("Enter the " + entity);
        return scanner.nextLine();
    }

    // Prompt the user to enter the amount
    private Double promptAmount(Scanner scanner, boolean isDeposit) {
        Double amount = null;

        while (amount == null) {
            try {
                System.out.println("Enter the amount");
                Double amountInput = scanner.nextDouble();

                if (amountInput >= 0) {
                    // Ensure user always enter positive number
                    amount = amountInput;
                } else {
                    System.out.println("Invalid value. Value must be numeric and greater or equal to 0");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid value. Value must be numeric and greater or equal to 0");
            }
        }

        if (!isDeposit) {
            // If it is payment, convert it negative
            amount *= -1;
        }

        return amount;
    }

    private void activateLedgerScreen(LedgerScreen ledgerScreen) {
        // Deactivate the current screen
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

