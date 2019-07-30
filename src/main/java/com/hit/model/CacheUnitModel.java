package main.java.com.hit.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import main.java.com.hit.dm.DataModel;
import main.java.com.hit.server.Request;
import main.java.com.hit.util.ObserMessage;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Observable;
import java.util.Scanner;

public class CacheUnitModel extends Observable implements Model {

    CacheUnitClient cacheUnitClient;

    public CacheUnitModel() {
        this.cacheUnitClient = new CacheUnitClient();
    }

    @Override
    public <T> void updateModelData(T t) {
        ObserMessage requst = (ObserMessage) t;

        if (requst.getMessege().equals("load")) {
            Scanner scanner = null;
            String req = null;

            try {
                Gson gson = new GsonBuilder().create();
                scanner = new Scanner(new FileInputStream(requst.getExtra()));
                req = scanner.useDelimiter("\\A").next();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (scanner != null) {
                    scanner.close();
                }
            }

            if (req != null) {
                String send = cacheUnitClient.send(req);
                setChanged();

                if (send.equals("net-crash")) {
                    notifyObservers(new ObserMessage("net-crash", "Failed"));
                }
                notifyObservers(new ObserMessage("model-load", send));
            }
        }

        if (requst.getMessege().equals("updateUI")) {

            String send = cacheUnitClient.get();
            setChanged();
            notifyObservers(new ObserMessage("model-stats", send));

        }
    }
}
