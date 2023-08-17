package server;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary {
    private Map<String,List<String>> wordData = new HashMap<>();
    private String path;
    private boolean available = false;
    public Dictionary(String path){
        String newline = "";
        String[] newlineS;
        try {
            this.path = path;
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while((newline = reader.readLine())!=null){
                newlineS = newline.split(",");
                if(this.wordData.containsKey(newlineS[0])){
                    List<String> wordMeanings = this.wordData.get(newlineS[0]);
                    wordMeanings.add(newlineS[1]);
                }else {
                    List<String> wordMeanings = new ArrayList<>();
                    wordMeanings.add(newlineS[1]);
                    this.wordData.put(newlineS[0],wordMeanings);
                }
            }
            reader.close();
            this.available = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<String> getMeaning(String word){
        if (this.wordData.containsKey(word)){
            return this.wordData.get(word);
        }
        return null;
    }
    public boolean addNewWord(String word, List<String> meanings){
        if (this.wordData.containsKey(word)){
            return false;
        }
        this.wordData.put(word,meanings);
        refreshFile();
        return true;
    }
    public boolean deleteWord(String word){
        if (!this.wordData.containsKey(word)){
            return false;
        }
        this.wordData.remove(word);
        refreshFile();
        return true;
    }
    public boolean updateWord(String word, List<String> meanings){
        if (!this.wordData.containsKey(word)){
            return false;
        }
        this.wordData.replace(word, meanings);
        refreshFile();
        return true;
    }
    private void refreshFile(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.path));
            for(Map.Entry<String,List<String>> entry:this.wordData.entrySet()){
                if (entry.getValue().size()==1){
                    writer.write(entry.getKey()+","+entry.getValue().get(0)+",");
                    writer.newLine();
                }else {
                    for (String meaning: entry.getValue()){
                        writer.write(entry.getKey()+","+meaning+",");
                        writer.newLine();
                    }
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isAvailable() {
        return available;
    }
}
