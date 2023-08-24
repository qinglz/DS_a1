package client;

import data_package.MyResponse;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
public class ClientWindow extends JFrame{
    private ClientSocketChannel channel;
    public ClientWindow(ClientSocketChannel channel){
        super("ClientWindow");
        this.channel = channel;
        JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.add(getMeaningButton());
        jPanel.add(getDeleteWordButton());
        jPanel.add(getAddWordButton());
        jPanel.add(getUpdateWordButton());
        this.add(jPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(20,20,275,100);
        this.setVisible(true);
    }

    private JButton getMeaningButton(){
        JButton getMeaningButton = new JButton("Get Meanings");
        JDialog searchingDialog = getOneInputDialog();
        searchingDialog.setTitle("Get meanings");
        JButton submit = (JButton) searchingDialog.getContentPane().getComponent(2);
        JTextField word = (JTextField)searchingDialog.getContentPane().getComponent(1);
        getMeaningButton.addActionListener(actionEvent -> {
            searchingDialog.setVisible(true);
        });
        submit.addActionListener(actionEvent -> {
            try {
                MyResponse response = channel.getMeanings(word.getText());
                JDialog answerDialog = new JDialog(searchingDialog);
                answerDialog.setBounds(20,20,400,100);
                answerDialog.setLayout(new FlowLayout(FlowLayout.LEFT,10,20));
                if(response.getStatus()==5){
                    answerDialog.add(new JLabel("Word Doesn't Exist"));
                }else if(response.getStatus()==0){
                    answerDialog.add(new JLabel("Meanings Of The Word: "));
                    for (String s : response.getMeanings()){
                        answerDialog.add(new JLabel(s+"; "));
                    }
                }else{
                    answerDialog.add(new JLabel("Invalid Input"));
                }
                answerDialog.setVisible(true);
            } catch (IOException | ClassNotFoundException e) {
                JDialog err = getErrDialog(searchingDialog);
                err.setVisible(true);
                e.printStackTrace();
            }
        });
        return getMeaningButton;
    }
    private JButton getDeleteWordButton(){
        JButton deleteWordButton = new JButton("Delete A Word");
        JDialog searchingDialog = getOneInputDialog();
        searchingDialog.setTitle("Delete words");
        JButton submit = (JButton) searchingDialog.getContentPane().getComponent(2);
        JTextField word = (JTextField)searchingDialog.getContentPane().getComponent(1);
        deleteWordButton.addActionListener(actionEvent -> {
            searchingDialog.setVisible(true);
        });
        submit.addActionListener(actionEvent -> {
            try {
                MyResponse response = channel.deleteWord(word.getText());
                JDialog answerDialog = new JDialog(searchingDialog);
                answerDialog.setBounds(20,20,400,100);
                answerDialog.setLayout(new FlowLayout(FlowLayout.LEFT,10,20));
                if(response.getStatus()==5){
                    answerDialog.add(new JLabel("Word Doesn't Exist"));
                }else if(response.getStatus()==0){
                    answerDialog.add(new JLabel("Delete Word: "+word.getText()+" Successfully"));
                }else{
                    answerDialog.add(new JLabel("Invalid Input"));
                }
                answerDialog.setVisible(true);
            } catch (IOException | ClassNotFoundException e) {
                JDialog err = getErrDialog(searchingDialog);
                err.setVisible(true);
                e.printStackTrace();
            }
        });
        return deleteWordButton;
    }
    private JButton getAddWordButton(){
        JButton addWordButton = new JButton("Add A Word");
        JDialog searchDialog = getTwoInputDialog();
        searchDialog.setTitle("Add words");
        addWordButton.addActionListener(actionEvent -> {
            searchDialog.setVisible(true);
        });
        return addWordButton;
    }
    private JButton getUpdateWordButton(){
        JButton addWordButton = new JButton("Update A Word");
        JDialog searchDialog = getTwoInputDialog();
        searchDialog.setTitle("Update words");
        addWordButton.addActionListener(actionEvent -> {
            searchDialog.setVisible(true);
        });
        return addWordButton;
    }





    private JDialog getErrDialog(JDialog owner){
        JDialog errDialog = new JDialog(owner);
        errDialog.setBounds(20,20,400,100);
        errDialog.setLayout(new FlowLayout(FlowLayout.LEFT,10,20));
        errDialog.add(new JLabel("Fail To Connect Server"));
        return errDialog;
    }
    private JDialog getOneInputDialog(){
        JDialog inputDialog = new JDialog(this);
        JButton submit = new JButton("Submit");
        JTextField word = new JTextField(8);

        inputDialog.setBounds(20,20,400,100);
        inputDialog.setLayout(new FlowLayout(FlowLayout.LEFT,10,20));

        inputDialog.add(new JLabel("Input the Word: "));
        inputDialog.add(word);
        inputDialog.add(submit);
        return inputDialog;
    }
    private JDialog getTwoInputDialog(){
        JDialog inputDialog = new JDialog(this);
        JButton submit = new JButton("Submit");
        JTextField word = new JTextField(8);
        JTextArea meanings = new JTextArea(10,20);
        JScrollPane scrollPane = new JScrollPane(meanings);
        Dimension size = meanings.getPreferredSize();

        meanings.setLineWrap(true);
        scrollPane.setBounds(20,20,size.width,size.height);
        inputDialog.setBounds(20,20,400,300);
        inputDialog.setLayout(new FlowLayout(FlowLayout.LEFT,10,20));
        inputDialog.add(new JLabel("Input the Word: "));
        inputDialog.add(word);
        inputDialog.add(new JLabel("Input the Meanings (Separate each meaning by ';'): "));
        inputDialog.add(scrollPane);
        inputDialog.add(submit);

        return inputDialog;
    }

}
