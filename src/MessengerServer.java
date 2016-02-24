package com.mersanuzun.examples.messanger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mersanuzun on 2/22/16.
 */
public class MessengerServer extends Thread{
    private static final int PORT = 9090;
    private static final String IP = "localhost";
    private ServerSocket serverSocket;
    private ArrayList<PrintWriter> clientsOutputStreams = new ArrayList<>();

    private void go() {

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running.");
            while(true){
                Socket socket = serverSocket.accept();
                System.out.println(socket.toString());
                clientsOutputStreams.add(new PrintWriter(socket.getOutputStream()));
                new Thread(new ClientHandler(socket)).start();
                System.out.println("Got a connection.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }

    private class ClientHandler implements Runnable {
        private Socket sock;
        BufferedReader reader;
        private String username;

        ClientHandler(Socket sock) throws IOException {
            this.sock = sock;
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);
        }

        @Override
        public void run() {
            try {
                String message;
                while((message = reader.readLine()) != null){
                    System.out.println(message);
                    if (message.contains("firstVisit")){
                        username = message.substring(10);
                        tellEveryone("Joined " + message.substring(10));
                    }else{
                        tellEveryone(username + " : " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void tellEveryone(String message) {
            for(PrintWriter writer : clientsOutputStreams){
                writer.println(message);
                writer.flush();
            }
        }
    }

    public static void main(String args[]){
        new MessengerServer().go();

    }
}
