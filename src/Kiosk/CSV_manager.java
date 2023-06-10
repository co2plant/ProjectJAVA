package Kiosk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CSV_manager {
     	final String csvSplitBy = ",";
     	final String csvFile = "CSV/record.csv";
     	final int MAX_TOKEN = 3;
     	public void CSV_Write(String gamename,String Score)
     	{
     		try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile,true))) {
                // CSV 파일에 쓸 데이터를 준비합니다.
                String[] data1 = {gamename,Score};
                
                // CSV 파일에 데이터를 씁니다.
                bw.write(String.join(csvSplitBy, data1));
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
     		CSV_Sort();
     	}
	
	public List CSV_Reader(String name)
	{
        String line;
        List<String[]> data = new ArrayList<>();
        CSV_Sort();
        int token = MAX_TOKEN;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
            	if(token <= 0)
            		break;
                String[] values = line.split(csvSplitBy);
                if(values[0].equals(name))
                {
                	data.add(values);
                	token--;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
	}
	
	public void CSV_Sort()
	{
        String line;
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(csvSplitBy);
                data.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(data, new MultiColumnComparator());


        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
            for (String[] row : data) {
                String lineToWrite = String.join(csvSplitBy, row);
                bw.write(lineToWrite);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	static class MultiColumnComparator implements Comparator<String[]> {
        @Override
        public int compare(String[] row1, String[] row2) {
            int compareResult = row2[0].compareTo(row1[0]);
            if (compareResult != 0) {
                return compareResult;
            }
            if(row1[0].equals("Puzzle"))
            	compareResult = row1[1].compareTo(row2[1]);
            else
            	compareResult = Integer.parseInt(row2[1]) - Integer.parseInt(row1[1]);
            return compareResult;
        }
    }
	
}

