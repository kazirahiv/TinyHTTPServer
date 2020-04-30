package com.kazirahiv.client;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Incoming extends Thread {
    Socket socket;

    public Incoming(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            while (in.hasNextLine()) {
                String payload = in.nextLine();
                Gson gson = new Gson();
                Payload payloadObj = gson.fromJson(payload, Payload.class);
                System.out.println(payloadObj.Content);
                System.out.println("Enter a text: ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
