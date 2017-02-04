package lv.com.csvfileaccess.model.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import lv.com.csvfileaccess.model.pojo.RowData;

public class CSVFile {
    InputStream inputStream;

    public CSVFile(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List read() {
        List resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                resultList.add(row);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
        return resultList;
    }

    public List<RowData> getDataList() {
        List<RowData> rowDatas = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                RowData rowData = new RowData();
                rowData.setId(Integer.parseInt(csvLine.split(",")[0]));
                rowData.setProd(csvLine.split(",")[1].replace("\"", ""));
                rowData.setOwn(csvLine.split(",")[2]);
                rowDatas.add(rowData);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
        return rowDatas;
    }
}
