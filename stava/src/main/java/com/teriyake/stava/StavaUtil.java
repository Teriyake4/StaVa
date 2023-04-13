package com.teriyake.stava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class StavaUtil {
    public static String readFile(File filePath) {
        String output = "";
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder("");
            String line;
             // Holds true until there is nothing to read
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }
            output = sb.toString();
            reader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            return output;
        }
        // checks if file is empty
        if(output.equals("") || output.equals("\n")) {
            return output;
        }
        return output;
    }

    public static void writeFile(File filePath, String toWrite, boolean append) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            writer.write(toWrite);
            writer.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
