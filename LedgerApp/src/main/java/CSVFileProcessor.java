import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVFileProcessor {
    private String filePath;
    private List<String> headers;

    // Constructors for initialize CSVFileProcessor instance
    public CSVFileProcessor (String filePath, List<String> headers) {
        this.filePath = filePath;
        this.headers = headers;
    }

    // Allow read file depending on including header or not
    public List<String> readFile(boolean isReadingHeader) {
        // Initialzie output (which is the content of the file)
        List<String> output = new ArrayList<>();
        try {
            // Initialize variables
            boolean isFirstLine = true;
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String lineContent = null;

            while((lineContent = bufferedReader.readLine()) != null) {
                // If it is not reading header and we are at the first line, skip it
                if (!isReadingHeader && isFirstLine) {
                    // Set isFirstLine to false because we have moved to the next line
                    isFirstLine = false;
                } else if (lineContent.trim() != "") {
                    // Only add the line with content, skip line without content
                    output.add(lineContent);
                }
            }

            // Close the buffer reader
            bufferedReader.close();
        } catch (IOException e){
            System.out.println("error while reading file. " + e.getMessage());
        }
        return output;
    }

    // Create file, if file is already exist, do nothing
    public void createFile() {
        // if file is already exist, do nothing
        File file = new File(filePath);
        if (file.exists()) {
            return;
        }

        // If file not exist, try create file
        try {
            FileWriter filewriter = new FileWriter(this.filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
            String header = "";

            // Create header line for CSV file.
            for(int i = 0; i < headers.size(); i++) {
                String currentHeader = headers.get(i);
                header = header + currentHeader;

                // If it is the last header field, no need to add '|', otherwise, just add '|' between them
                if (i != headers.size() - 1)  {
                    header = header + " | ";
                }
            }

            // Attempt to write header to file
            bufferedWriter.write(header);

            // Close the buffer writer
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("error while creating file. " + e.getMessage());
        }
    }

    // Write to file, and add more content, if file exist, if not, create file, and write content to file
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