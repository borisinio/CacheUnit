package main.java.com.hit.server;

import main.java.com.hit.algorithm.LRUAlgoCacheImpl;
import main.java.com.hit.algorithm.RandomAlgoCacheImpl;
import main.java.com.hit.algorithm.SecondChance;
import main.java.com.hit.services.CacheUnitController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable, PropertyChangeListener {

    private int port;
    private ServerSocket server;
    private String userInput = "";
    private CacheUnitController cacheUnitController;
    private String[] userChoice;
    private Boolean serverIsRunning;
    private String algo;
    private String capacity;

    public Server(int port) {
        this.port = port;
        cacheUnitController = new CacheUnitController(new LRUAlgoCacheImpl(30));
        serverIsRunning = true;
    }

    public void propertyChange(PropertyChangeEvent evt) {

        synchronized (this) {
            //get property from cli
            userInput = evt.getNewValue().toString();

            userChoice = userInput.split(" ");

            switch (userChoice[0]) {
                case "START":
                    System.out.println("Starting server...");
                    break;
                case "CACHE_UNIT_CONFIG":
                    algo = userChoice[1];
                    capacity = userChoice[2];
                    switch (algo) {
                        case "LRU":
                            this.cacheUnitController = new CacheUnitController(new LRUAlgoCacheImpl(Integer.parseInt(capacity)));
                            break;
                        case "RANDOM":
                            this.cacheUnitController = new CacheUnitController(new RandomAlgoCacheImpl(Integer.parseInt(capacity)));
                            break;
                        case "SECONDCHANCE":
                            this.cacheUnitController = new CacheUnitController(new SecondChance(Integer.parseInt(capacity)));
                            break;
                    }
                    break;
                case "SHUTDOWN":
                    serverIsRunning = false;
                    System.out.println("Shutdown server...");
                    break;
            }
        }
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            while (serverIsRunning) {
                new Thread(new HandleRequest(server.accept(), cacheUnitController)).start();
            }
            server.close();
            System.out.println("shutdown");
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}




