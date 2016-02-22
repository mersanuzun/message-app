package com.mersanuzun.examples.messanger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by mersanuzun on 2/22/16.
 */
public class MessengerServer extends Thread{
    private static final int PORT = 9090;
    private static final String IP = "localhost";
    private ServerSocket serverSocket;
    private ArrayList<PrintWriter> writers = new ArrayList<>();
    private void go() {

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running.");
            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("accepted");

                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }

    private class ClientHandler implements Runnable {
        private Socket sock;
        BufferedReader reader;

        ClientHandler(Socket sock) throws IOException {
            this.sock = sock;
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);
        }

        @Override
        public void run() {
            try {
                System.out.println(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]){
        new MessengerServer().go();

    }
}
