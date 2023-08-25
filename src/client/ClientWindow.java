package client;

import data_package.MyResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

public class ClientWindow extends JFrame{
    private ClientSocketChannel channel;
    public ClientWindow(ClientSocketChannel channel){
        super("ClientWindow");
        this.channel = channel;
        JPanel jPanel = new JPanel(new FlowLayout());
        JButton exit = new JButton("Exit");
        exit.addActionListener(actionEvent -> {
            channel.close();
            this.dispose();
            System.exit(0);
        });
        jPanel.add(getMeaningButton());
        jPanel.add(getDeleteWordButton());
        jPanel.add(getAddWordButton());
        jPanel.add(getUpdateWordButton());
        jPanel.add(exit);
        this.add(jPanel);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                channel.close();
                System.exit(0);
            }
        });
        this.setBounds(20,20,275,140);
        this.setLocationRelativeTo(null);
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
                        answerDialog.add(new JLabel(s+";\n"));
                    }
                }else{
                    answerDialog.add(new JLabel("Invalid Input"));
                }
                answerDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                answerDialog.setLocationRelativeTo(this);
                answerDialog.setVisible(true);
            } catch (IOException | ClassNotFoundException e) {
                JDialog err = getErrDialog(searchingDialog);
                err.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
                }else if(response.getStatus()==3){
                    answerDialog.add(new JLabel("Dictionary File Update Failed"));
                }else{
                    answerDialog.add(new JLabel("Invalid Input"));
                }
                answerDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                answerDialog.setLocationRelativeTo(this);
                answerDialog.setVisible(true);
            } catch (IOException | ClassNotFoundException e) {
                JDialog err = getErrDialog(searchingDialog);
                err.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                err.setVisible(true);
                e.printStackTrace();
            }
        });
        return deleteWordButton;
    }
    private JButton getAddWordButton(){
        JButton addWordButton = new JButton("Add A Word");
        JDialog searchDialog = getTwoInputDialog();
        JButton submit = (JButton) searchDialog.getContentPane().getComponent(4);
        JTextField word = (JTextField)searchDialog.getContentPane().getComponent(1);
        JTextArea meanings = (JTextArea) ((JScrollPane)searchDialog.getContentPane().getComponent(3)).getViewport().getView();
        searchDialog.setTitle("Add words");

        addWordButton.addActionListener(actionEvent -> {
            searchDialog.setVisible(true);
        });
        submit.addActionListener(actionEvent -> {
            try {
                String extract = word.getText().replaceAll("\n|;","");
                MyResponse response = channel.addWord(extract,
                        List.of(meanings.getText().replaceAll("\n","").split(";")));
                JDialog answerDialog = new JDialog(searchDialog);
                answerDialog.setBounds(20,20,400,100);
                answerDialog.setLayout(new FlowLayout(FlowLayout.LEFT,10,20));
                if(response.getStatus()==4){
                    answerDialog.add(new JLabel("Word Has Exist"));
                }else if(response.getStatus()==0){
                    answerDialog.add(new JLabel("Add Word: "+extract+" Successfully"));
                }else if(response.getStatus()==2){
                    answerDialog.add(new JLabel("Dictionary File Update Failed"));
                }else{
                    answerDialog.add(new JLabel("Invalid Input"));
                }
                answerDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                answerDialog.setLocationRelativeTo(this);
                answerDialog.setVisible(true);
            } catch (IOException | ClassNotFoundException e) {
                JDialog err = getErrDialog(searchDialog);
                err.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                err.setVisible(true);
                e.printStackTrace();
            }
        });

        return addWordButton;
    }
    private JButton getUpdateWordButton(){
        JButton updateWordButton = new JButton("Update A Word");
        JDialog searchDialog = getTwoInputDialog();
        JButton submit = (JButton) searchDialog.getContentPane().getComponent(4);
        JTextField word = (JTextField)searchDialog.getContentPane().getComponent(1);
        JTextArea meanings = (JTextArea) ((JScrollPane)searchDialog.getContentPane().getComponent(3)).getViewport().getView();
        searchDialog.setTitle("Update words");

        updateWordButton.addActionListener(actionEvent -> {
            searchDialog.setVisible(true);
        });

        submit.addActionListener(actionEvent -> {
            try {
                String extract = word.getText().replaceAll("\n|;","");
                MyResponse response = channel.updateWord(extract,
                        List.of(meanings.getText().replaceAll("\n","").split(";")));
                JDialog answerDialog = new JDialog(searchDialog);
                answerDialog.setBounds(20,20,400,100);
                answerDialog.setLayout(new FlowLayout(FlowLayout.LEFT,10,20));
                if(response.getStatus()==5){
                    answerDialog.add(new JLabel("Word Doesn't Exist"));
                }else if(response.getStatus()==0){
                    answerDialog.add(new JLabel("Update Word: "+extract+" Successfully"));
                }else if(response.getStatus()==2){
                    answerDialog.add(new JLabel("Dictionary File Update Failed"));
                }else{
                    answerDialog.add(new JLabel("Invalid Input"));
                }
                answerDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                answerDialog.setLocationRelativeTo(this);
                answerDialog.setVisible(true);
            } catch (IOException | ClassNotFoundException e) {
                JDialog err = getErrDialog(searchDialog);
                err.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                err.setVisible(true);
                e.printStackTrace();
            }
        });

        return updateWordButton;
    }



    private JDialog getErrDialog(JDialog owner){
        JDialog errDialog = new JDialog(owner);
        errDialog.setBounds(20,20,400,100);
        errDialog.setLayout(new FlowLayout(FlowLayout.LEFT,10,20));
        errDialog.setLocationRelativeTo(this);
        errDialog.add(new JLabel("Fail To Connect Server"));
        return errDialog;
    }
    private JDialog getOneInputDialog(){
        JDialog inputDialog = new JDialog(this);
        JButton submit = new JButton("Submit");
        JTextField word = new JTextField(8);
        JButton cancel = new JButton("cancel");

        cancel.addActionListener(actionEvent -> {
            inputDialog.setVisible(false);
        });

        inputDialog.setBounds(20,20,400,100);
        inputDialog.setLayout(new FlowLayout(FlowLayout.LEFT,10,20));
        inputDialog.setLocationRelativeTo(this);

        inputDialog.add(new JLabel("Input the Word: "));
        inputDialog.add(word);
        inputDialog.add(submit);
        inputDialog.add(cancel);
        return inputDialog;
    }
    private JDialog getTwoInputDialog(){
        JDialog inputDialog = new JDialog(this);
        JButton submit = new JButton("Submit");
        JTextField word = new JTextField(8);
        JTextArea meanings = new JTextArea(10,20);
        JScrollPane scrollPane = new JScrollPane(meanings);
        Dimension size = meanings.getPreferredSize();
        JButton cancel = new JButton("Cancel");

        cancel.addActionListener(actionEvent -> {
            inputDialog.setVisible(false);
        });

        meanings.setLineWrap(true);
        scrollPane.setBounds(20,20,size.width,size.height);
        inputDialog.setBounds(20,20,430,300);
        inputDialog.setLayout(new FlowLayout(FlowLayout.LEFT,10,20));
        inputDialog.setLocationRelativeTo(this);

        inputDialog.add(new JLabel("Input the Word: "));
        inputDialog.add(word);
        inputDialog.add(new JLabel("Input the Meanings (Separate each meaning by ';'): "));
        inputDialog.add(scrollPane);
        inputDialog.add(submit);
        inputDialog.add(cancel);

        return inputDialog;
    }

}
