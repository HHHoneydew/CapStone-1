import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class CSVFileProcessor {
    private String filePath;
    private List<String> headers;

    public CSVFileProcessor (String filePath, List<String> headers) {
        this.filePath = filePath;
        this.headers = headers;
    }
    public void createFile() {
        try {
            FileWriter filewriter = new FileWriter(this.filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
            String header = "";
            for(int i = 0; i < headers.size(); i++) {
                String currentHeader = headers.get(i);
                header = header + currentHeader;
                if (i != headers.size() - 1)  {
                    header = header + " | ";
                }
            }
            bufferedWriter.write(header);
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("error while creating file. " + e.getMessage());
        }
    }
    public void writeToFile(String userInPut) {
        try {
            FileWriter fileWriter = new FileWriter(this.filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("\n"+ userInPut);
            bufferedWriter.close();

        }catch(Exception e) {
            System.out.println("error while writing to file. " + e.getMessage());
        }
    }
}
