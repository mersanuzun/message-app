package com.mersanuzun.examples.messanger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

/**
 * Created by mersanuzun on 2/22/16.
 */
public class MessangerClient extends JFrame{
    private Socket socket;
    private JTextArea messagesTextArea;
    private JTextField messageTxt;
    private PrintWriter writer;
    private BufferedReader reader;

    MessangerClient(){
        setVisible(true);
        setBounds(new Rectangle(400, 400));
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        messagesTextArea = new JTextArea(5,20);
        JScrollPane scrollPane = new JScrollPane(messagesTextArea);
        topPanel.add(messagesTextArea, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        messageTxt = new JTextField(20);
        messageTxt.requestFocus();
        bottomPanel.add(messageTxt, BorderLayout.CENTER);
        JButton sendBtn = new JButton("Send");
        sendBtn.addActionListener(new SendButtonListener());
        sendBtn.setBounds(new Rectangle(75, 50));
        bottomPanel.add(sendBtn, BorderLayout.EAST);
        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setUpNetworking();
        new Thread(new InCommingReader()).start();
        setVisible(true);
    }

    public void setUpNetworking(){
        try {
            socket = new Socket("127.0.0.1", 9090);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class InCommingReader implements Runnable{

        @Override
        public void run() {
            String message;
            try {
                System.out.println(reader);
                while((message = reader.readLine()) != null){
                    System.out.println("ınComming while");
                    messagesTextArea.append(message);
                    System.out.println(message);
                    
                }
                System.out.println("exit");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class SendButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            writer.println(messageTxt.getText());
            writer.flush();
            messageTxt.setText("");
            messageTxt.requestFocus();
        }
    }

    public static void main(String args[]){
        new MessangerClient();
    }

}
