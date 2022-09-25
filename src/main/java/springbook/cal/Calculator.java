package springbook.cal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public int calcSum(String path) throws IOException {
        return fileReaderTemplate(path, 0, (line, res) -> Integer.parseInt(line) + res);
    }

    public int calcMul(String path) throws IOException {
        return fileReaderTemplate(path,1, (line, res) -> Integer.parseInt(line) * res);
    }

    public String concatenate(String path) throws IOException {
        return fileReaderTemplate(path,"", (line, res) -> res + line);
    }


    private <T> T fileReaderTemplate(String path, T initValue, LineReadCallback<T> callback) throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(path));
            T result = initValue;
            String line = null;
            while ((line = br.readLine()) != null) {
                result = callback.doSomethingWithLine(line, result);
            }
            return result;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
