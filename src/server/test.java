package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) throws IOException {
        Dictionary dictionary = new Dictionary("./dict.csv");
        List<String> added = new ArrayList<>();
        added.add("hi");
        added.add("你好");
        added.add("雷猴啊");

        dictionary.addNewWord("hello",added);
//        dictionary.deleteWord("hello");
        if(dictionary.getMeaning("hello")!=null){
        System.out.println(dictionary.getMeaning("hello"));
        }
    }
}
