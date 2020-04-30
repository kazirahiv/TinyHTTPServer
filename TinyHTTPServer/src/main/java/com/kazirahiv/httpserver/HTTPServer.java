package com.kazirahiv.httpserver;


import com.google.gson.Gson;
import com.kazirahiv.httpserver.Data.HTTPStatus;
import com.kazirahiv.httpserver.Data.HeaderTypes;
import com.kazirahiv.httpserver.Data.Payload;
import com.kazirahiv.httpserver.Data.Type;
import com.kazirahiv.httpserver.Util.FileHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HTTPServer implements Runnable {

    private Socket socket = null;
    private BufferedReader inClient = null;
    private DataOutputStream outClient = null;

    public HTTPServer(Socket cl) throws Exception {
        socket = cl;
    }

    public void run() {
        try {
            System.out.println("The Client "+ socket.getInetAddress()+":"+socket.getPort()+" is connected");
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner in = new Scanner(socket.getInputStream());

            while (in.hasNextLine())
            {
                String payload = in.nextLine();
                Gson gson = new Gson();
                Payload payloadObj = gson.fromJson(payload, Payload.class);
                System.out.println(payloadObj.Type);
                if(payloadObj.Type == Type.GET)
                {
                    System.out.println("GET : "+ payloadObj.Target);
                    HandleGetRequest(payloadObj.Target, out);
                }
                else if(payloadObj.Type == Type.PUT)
                {
                    System.out.println("PUT : "+ payloadObj.Target);
                    HandlePutRequest(payloadObj.Target, payloadObj.Content, out);
                }
                else if(payloadObj.Type == Type.DELETE)
                {
                    System.out.println("DELETE : "+ payloadObj.Target);
                    HandleDeleteRequest(payloadObj.Target, out);
                }
            }
            System.out.println("Connection Is Closed With - "+ socket.getInetAddress() + ":"+ socket.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void HandlePutRequest(String file, String value, PrintWriter out) throws Exception {
        boolean status = FileHandler.writeFile(value, file);
        Payload payload = new Payload();
        payload.Message = "response";
        if(status)
        {
            payload.Code = "201";
            payload.Content = "The file has been copied.";
        }else
        {
            payload.Code = "500";
            payload.Content = "Error copying the file.";
        }
        out.println(new Gson().toJson(payload));
    }




    private void HandleDeleteRequest(String queryString, PrintWriter out) throws Exception {
        boolean status = FileHandler.deleteFile(queryString);
        Payload payload = new Payload();
        payload.Message = "response";
        if(status)
        {
            payload.Code = "200";
            payload.Content = "The requested resource is successfully deleted.";
        }else
            {
                payload.Code = "500";
                payload.Content = "The requested resource is not deleted.";
            }
        out.println(new Gson().toJson(payload));
    }


    public void HandleGetRequest(String httpQueryString, PrintWriter out) throws Exception
    {
        String data  = FileHandler.readFile(httpQueryString);
        Payload payload = new Payload();
        if(!data.isEmpty())
        {

            payload.Message = "response";
            payload.Code = "200";
            payload.Content = data;
        }else
        {
            payload.Message = "response";
            payload.Code = "400";
            payload.Content = NotFoundTemplate();
        }
        out.println(new Gson().toJson(payload));
    }


    public String NotFoundTemplate() throws IOException {
        String template  = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Not Found</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>The request URL is not present on this server.</h1>\n" +
                "</body>\n" +
                "</html>";
        return template;
    }

}