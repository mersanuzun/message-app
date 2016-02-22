package com.mersanuzun.examples.messanger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mersanuzun on 2/22/16.
 */
public class MessangerFrame extends JFrame{
    private JTextArea messagesTextArea;
    private JTextField messageTxt;

    MessangerFrame(){
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
    }

    private class SendButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            messagesTextArea.append(messageTxt.getText());
        }
    }
}
