public class Option {
    private String inputSymbol;
    private String description;

    public String getInputSymbol() {
        return inputSymbol;
    }

    public void setInputSymbol(String inputSymbol) {
        this.inputSymbol = inputSymbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Option(String inputSymbol, String description) {
        this.inputSymbol = inputSymbol;
        this.description = description;
    }



}
