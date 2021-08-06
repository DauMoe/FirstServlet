package utils;

import java.io.BufferedReader;
import java.io.IOException;

public class StreamReader {
    private String value;

    public StreamReader(String value) {
        this.value = value;
    }

    public StreamReader() {
    }

    public static String parse2String (BufferedReader buf) {
        StringBuilder buffer = new StringBuilder();
        String line;
        try {
            while ((line = buf.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            throw new Error(e);
        }
        return new String(buffer);
    }
}
