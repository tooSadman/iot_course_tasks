package com.ua.lviv.iot.writer;

import com.ua.lviv.iot.spares.Spares;

import java.io.*;
import java.util.List;

public class SparesWriter {
    public void writeToFile(List<Spares> list) {
        try( FileWriter fileWriter = new FileWriter("list.csv");
             PrintWriter printWriter = new PrintWriter(fileWriter)) {


            for (Spares aList : list) {
                printWriter.print(aList.getHeaders());
                printWriter.print(aList.toCSV());
                printWriter.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
