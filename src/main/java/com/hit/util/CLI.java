package main.java.com.hit.util;

import main.java.com.hit.server.Server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class CLI implements Runnable{

    private Scanner scanner;
    private PrintWriter printWriter;
    private String userInput;
    private PropertyChangeSupport pcs;
    private String algo;
    private int capacity;
    private Boolean serverIsRunning;

    public CLI(InputStream in, OutputStream out){
        this.scanner = new Scanner(in);
        printWriter = new PrintWriter(out,true);
        pcs = new PropertyChangeSupport(this);
        algo = "LRU";
        capacity = 30;
        serverIsRunning = false;
    }


    @Override
    public void run() {

        while (true) {
            System.out.println("pls enter your command:");
            String oldVal = userInput;
            userInput = scanner.nextLine().trim().toUpperCase();
            String[] s = userInput.split(" ");
            //fire property to server, start/shutdown/change cacheunit
            switch (s[0]) {
                case "START":
                    if (!serverIsRunning)   {
                        pcs.firePropertyChange(userInput, null, "START");
                        Thread server = new Thread(new Server(34567));
                        server.start();
                        serverIsRunning = true;
                    } else {
                        System.out.println("server is already running");
                    }
                    break;
                case "SHOW_STATS":
                    pcs.firePropertyChange(userInput, oldVal, userInput);
                	break;
                case "CACHE_UNIT_CONFIG":
                    if (s[1].equals("LRU") | s[1].equals("RANDOM") | s[1].equals("SECONDCHANCE")) {
                        pcs.firePropertyChange(userInput, oldVal, userInput);
                    } else {
                        System.out.println("Algo doesn't exist");
                    }
                    break;
                case "SHUTDOWN":
                    if (serverIsRunning) {
                        pcs.firePropertyChange(userInput, null, "SHUTDOWN");
                        serverIsRunning = false;
                    } else {
                        System.out.println("server is not running");
                    }
                    break;
            }
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl){
        this.pcs.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl){
        this.pcs.removePropertyChangeListener(pcl);
    }

    public void write(String string){

    }


}
