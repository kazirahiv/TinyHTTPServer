package com.kazirahiv.httpserver;


import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String args[]) throws Exception {
        try(ServerSocket listener = new ServerSocket(5000, 10, InetAddress.getByName("127.0.0.1"))) {
            System.out.println("HTTPServer started on port 5000");
            ExecutorService pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new HTTPServer(listener.accept()));
            }
        }
    }
}
