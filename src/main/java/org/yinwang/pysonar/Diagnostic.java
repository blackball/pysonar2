package org.yinwang.pysonar;

import org.jetbrains.annotations.NotNull;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Diagnostic {
    public enum Category {
        INFO, WARNING, ERROR
    }


    public String file;
    public Category category;
    public int start;
    public int end;
    public String msg;


    public Diagnostic(String file, Category category, int start, int end, String msg) {
        this.category = category;
        this.file = file;
        this.start = start;
        this.end = end;
        this.msg = msg;
    }

    private int[] errorLocator(String file, int start, int end) {
        int row = 0, col_start = 0, col_end = 0, sum = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                sum += line.length();
                if (sum >= start) {
                    int gap = sum - start;
                    col_start = line.length() - gap;
                    col_end   = col_start + (end - start);
                    break;
                }
                ++row;
            }
        } catch (IOException e) {
            System.out.println("Can not open file " + file + " !");
        }
        
        
        int[] location = new int[3];
        location[0] = row + 1;
        location[1] = col_start;
        location[2] = col_end;
        return location;
    }
    
    @NotNull
    @Override
    public String toString() {
        int[] location = errorLocator(file, start, end);
        return "[Diagnostic: " + file + ": at [" +  location[0] + "," + location[1] + "], [" + category + "] : " + msg + "]";
    }
}
