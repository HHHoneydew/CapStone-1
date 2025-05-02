import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Record {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private Double amount;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    public Record(LocalDate date, LocalTime time, String description, String vendor, Double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // Convert record information into string with separator '|' (For example: 2025-05-01 | 12:18:25 | idk | random thing | 10.25)
    public String toString(){
        String formattedDate = date.format(dateFormatter);
        String formattedTime= time.format(timeFormatter);
        String formattedAmount = Double.toString(amount);
        return formattedDate + " | " + formattedTime + " | " + description + " | " + vendor + " | " + formattedAmount;
    }
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

