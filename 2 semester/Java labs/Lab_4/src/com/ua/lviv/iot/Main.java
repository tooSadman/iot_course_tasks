package com.ua.lviv.iot;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // write your code here
        StringProcessor stringProcessor = new StringProcessor();
        String string = "";
        try {
            string = stringProcessor.readInputText();
            string = stringProcessor.processText(string);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Output text:" + "\n"+ string);

    }
}
