package postharvest.ucdavis.edu.producefacts;

import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pryu on 5/26/2017.
 */

public class CSVFile {
    InputStream inputStream;
    public int columnCount;
    public String[] headers;
    public Map<String, Integer> headerIndex;
    public List<String[]> keyedRows;

    public CSVFile(InputStream inputStream) {
        this.inputStream = inputStream;
        keyedRows = read();
    }

    /*
        Read and parse the CSV file.
     */
    public List<String[]> read() {
        List<String[]> resultList;
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
            resultList = reader.readAll();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error in getting CSV file: " + e);
        } catch (IOException e) {
            throw new RuntimeException("Error in reading CSV file: " + e);
        }

        headers = (String[]) resultList.get(0);
        resultList.remove(0);

        columnCount = headers.length;
        headerIndex = new HashMap<String, Integer>();
        for (int i = 0; i < columnCount; i++) {
            headerIndex.put(headers[i], i);
        }

        return resultList;
    }
}
