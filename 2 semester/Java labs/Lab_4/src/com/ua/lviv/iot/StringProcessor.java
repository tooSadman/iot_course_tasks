package com.ua.lviv.iot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class StringProcessor {

    public String readInputText() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter String:");
        System.out.println("");
        return br.readLine();

    }

    public String processText(String inputText) throws IOException {

        String fullText = inputText.toLowerCase();


        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter three words to delete:");
        System.out.println("");
        String wordsToDelete =  br1.readLine();



        //adding split text array
        String [] textArray = fullText.split("\\s|(?=\\p{Punct})|(?<=\\p{Punct})");
        String [] deleteWords = new String[3];
        deleteWords =  wordsToDelete.split("\\s+");
        System.out.println(deleteWords);
        ArrayList<String> textArrayList = new ArrayList<>(Arrays.asList(textArray));

        //


        for (int ind1 = textArray.length-1; ind1 != 0; ind1--){
            if (textArray[ind1].equals("?")) {
                for (int int2 = ind1-1; !textArray[int2].equals("") && !textArray[int2].equals("?") && !textArray[int2].equals("!") && int2!=2; int2--) {
                    if (textArray[int2].equals(deleteWords[2])) {
                        int int3 = int2-1;
                        while (textArray[int3].equals(" ") && textArray[int3].equals(",") && textArray[int3].equals(":")&& textArray[int3].equals(";")&& int3!=1) {
                            int3--;
                        }
                        if (textArray[int3].equals(deleteWords[1])) {
                            int int4 = int3-1;
                            while (textArray[int4].equals(" ") & textArray[int4].equals(",") && textArray[int4].equals(":") && textArray[int4].equals(";") && int4!=0) {
                                int4--;
                            }
                            if (textArray[int4].equals(deleteWords[0])) {
                                textArrayList.remove(int2);
                                textArrayList.remove(int3);
                                textArrayList.remove(int4);
                            }

                        }
                    }
                }
            }

        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int index=0; index< textArrayList.size(); index++){
            stringBuilder.append(textArrayList.get(index)).append(" ");
        }
        return stringBuilder.toString();
    }

    public void showResult(String resultText /* or pass your class instance as parameter */) {
        // TODO: add implementation here
    }

}