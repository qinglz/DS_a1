package client;

import data_package.MyResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class ClientWindow extends JFrame{
    private ClientSocketChannel channel;
    public ClientWindow(ClientSocketChannel channel){
        super("ClientWindow");
        this.channel = channel;
        JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.add(getMeaningButton());
        this.add(jPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(20,20,400,100);
        this.setVisible(true);
    }

    private JButton getMeaningButton(){
        JButton getMeaningButton = new JButton("Get Meanings");
        JDialog searchingDialog = new JDialog(this);
        JButton submit = new JButton("Submit");
        JTextField word = new JTextField(8);

        searchingDialog.setBounds(20,20,400,100);
        searchingDialog.setLayout(new FlowLayout(FlowLayout.LEFT,10,20));

        searchingDialog.add(new JLabel("Input A Word: "));
        searchingDialog.add(word);
        searchingDialog.add(submit);

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
                    for (String s : response.getMeanings()){
                        answerDialog.add(new JLabel(s));
                    }
                }else{
                    answerDialog.add(new JLabel("Invalid Input"));
                }
                answerDialog.setVisible(true);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return getMeaningButton;
    }
}
