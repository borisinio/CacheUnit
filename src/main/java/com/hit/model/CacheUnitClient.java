package main.java.com.hit.model;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class CacheUnitClient {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public CacheUnitClient() { }

    public String send(String request) {
        try {
            socket = new Socket("localhost", 34567);

        } catch (IOException e) {
            if (e instanceof ConnectException) {
                return ("net-crash");
            }
            e.printStackTrace();
        }

        String o = "";

        try {

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(1);
            outputStream.writeObject(request);

            inputStream = new ObjectInputStream(socket.getInputStream());
            o = inputStream.readObject().toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return o;
    }

    public String get(){

        try {
            socket = new Socket("localhost", 34567);
        } catch (IOException e) {
            if (e instanceof ConnectException) { return ("net-crash"); }
            e.printStackTrace();
        }

        String o ="";

        try {

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(2);

            inputStream = new ObjectInputStream(socket.getInputStream());
            o = inputStream.readObject().toString();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

}
