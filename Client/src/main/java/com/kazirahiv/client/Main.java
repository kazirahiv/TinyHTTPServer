package com.kazirahiv.client;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class Main {
    static String hostname = "localhost";
    static int port = 5000;
    static Socket socket;
    static OutputStream output;
    static PrintWriter writer;
    public static void main(String[] args) {

        try  {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String text = "";
            while (!text.equals("exit"))
            {
                System.out.println("Enter a text: ");
                text= br.readLine();
                StringTokenizer tokenizer = new StringTokenizer(text);
                String command = tokenizer.nextToken();
                switch (command.toLowerCase()) {
                    case "connect":
                        connect();
                        break;
                    case "get":
                        get(writer,text);
                        break;
                    case "put":
                        put(writer,text);
                        break;
                    case "delete":
                        delete(writer,text);
                        break;
                    case "disconnect":
                        socket.close();
                        disconnect(writer,text);
                        break;
                    default:
                        System.out.println("Illegal Command");
                }
            }
            System.out.println("Bye :)");

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }


    private static void put(PrintWriter writer, String text) throws IOException {
        if(socket.isConnected())
        {
            if(text.split(" ").length == 3)
            {
                String file = text.split(" ")[1];
                String Target = text.split(" ")[2];
                Payload payloadObj = new Payload();
                payloadObj.Type = Type.PUT;
                payloadObj.Target = Target;
                payloadObj.Message = "request";
                payloadObj.Content = readFile(file);

                if(payloadObj.Content.isEmpty())
                {
                    System.out.println("The resource is not found. Abort ..");
                }else
                    {
                        Gson gson = new Gson();
                        String payloadJSON = gson.toJson(payloadObj);
                        writer.println(payloadJSON);
                        System.out.println("File sent to server..");
                    }
            }else
                {
                    System.out.println("Invalid Argument.");
                }

        }else
        {
            System.out.println("Not connected with the server.");
        }
    }


    //Read File
    public static String readFile(String path) throws IOException {
        if(!path.isEmpty())
        {
            String currentDirectory = System.getProperty("user.dir");
            String directory = currentDirectory.concat("/").concat(path).replace("/", File.separator);
            System.out.println(directory);

            if(directory.contains(".html"))
            {
                File file = new File(directory);
                if(file.exists())
                {
                    byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
                    return new String(encoded, StandardCharsets.UTF_8); //String in UTF8 Encoding
                }
            }
            return "";
        }
        return "";
    }


    private static void disconnect(PrintWriter writer,String text) throws IOException {
        if(socket.isConnected())
        {
            socket.close();
            System.out.println("Connection Closed.");
        }else
        {
            System.out.println("Not connected with the server.");
        }

    }

    private static void delete(PrintWriter writer,String text) {
        if(socket.isConnected())
        {
            if(text.split(" ").length == 2)
            {
                String Target = text.split(" ")[1];
                Payload payloadObj = new Payload();
                payloadObj.Type = Type.DELETE;
                payloadObj.Target = Target;
                payloadObj.Message = "request";
                Gson gson = new Gson();
                String payloadJSON = gson.toJson(payloadObj);
                writer.println(payloadJSON);
            }
            else
            {
                System.out.println("Invalid Argument.");
            }
        }else
        {
            System.out.println("Not connected with the server.");
        }

    }

    private static void get(PrintWriter writer,String text) {
        if(socket.isConnected())
        {
            if(text.split(" ").length == 2)
            {
                String Target = text.split(" ")[1];
                Payload payloadObj = new Payload();
                payloadObj.Type = Type.GET;
                payloadObj.Target = Target;
                payloadObj.Message = "request";
                Gson gson = new Gson();
                String payloadJSON = gson.toJson(payloadObj);
                writer.println(payloadJSON);
            }
            else
            {
                System.out.println("Invalid Argument.");
            }
        }else
            {
                System.out.println("Not connected with the server.");
            }

    }

    public static void connect() throws IOException {
        socket = new Socket(hostname, port);
        OutputStream output = socket.getOutputStream();
        writer = new PrintWriter(output, true);
        new Incoming(socket).start();
    }
}
