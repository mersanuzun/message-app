package com.mersanuzun.examples.messanger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

/**
 * Created by mersanuzun on 2/22/16.
 */
public class MessangerClient extends JFrame{
    private Socket socket;
    private JTextArea messagesTextArea;
    private JTextField messageTxt;
    private PrintWriter writer;

    MessangerClient(){
        setBounds(new Rectangle(400, 400));
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        messagesTextArea = new JTextArea(5,20);
        JScrollPane scrollPane = new JScrollPane(messagesTextArea);
        topPanel.add(messagesTextArea, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        messageTxt = new JTextField(20);
        bottomPanel.add(messageTxt, BorderLayout.CENTER);
        JButton sendBtn = new JButton("Send");
        sendBtn.addActionListener(new SendButtonListener());
        sendBtn.setBounds(new Rectangle(75, 50));
        bottomPanel.add(sendBtn, BorderLayout.EAST);
        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setUpNetworking();
    }

    public void setUpNetworking(){
        try {
            socket = new Socket("127.0.0.1", 9090);
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class SendButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            writer.println(messageTxt.getText());
            writer.flush();
            System.out.println(messageTxt.getText());
        }
    }
    private void go() {
        MessangerClient frame = new MessangerClient();
        frame.setVisible(true);

    }

    public static void main(String args[]){
        new MessangerClient().go();
    }

}
