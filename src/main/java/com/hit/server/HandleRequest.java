package main.java.com.hit.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.java.com.hit.dm.DataModel;
import main.java.com.hit.services.CacheUnitController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Map;

public class HandleRequest<T> implements Runnable{

    private Socket socket;
    private CacheUnitController<T> controller;
    private String getAction;

    Response<DataModel<T>[]> response;
    String action,inputString,outputString;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    public HandleRequest(Socket socket, CacheUnitController controller) throws IOException {
        this.socket = socket;
        this.controller = controller;
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

    }

    @Override
    public void run() {
        Gson gson = new GsonBuilder().create();

        try {
          int choice = (Integer) objectInputStream.readObject();
          //get user json and preform the action and return data to client
        switch (choice){
            case 1:

                inputString = (String) objectInputStream.readObject();
                Type req = new TypeToken<Request<DataModel<T>[]>>(){}.getType();
                Request<DataModel<T>[]> request = gson.fromJson(inputString, req);

                Map header = request.getHeader();

                action = header.get("action").toString().toUpperCase().trim();

                switch (action) {
                    case "UPDATE":
                        if (controller.update(request.getBody())) {
                            objectOutputStream.writeObject(controller.update(request.getBody()));
                        } else {
                            System.out.println("something was wrong");
                        }
                        break;
                    case "GET":
                        DataModel<T>[] dataModels = controller.get(request.getBody());
                        response = new Response(request.getHeader(), dataModels);
                        outputString = gson.toJson(response);
                        objectOutputStream.writeObject(outputString);

                        break;
                    case "DELETE":
                        if (controller.delete(request.getBody())) {
                            objectOutputStream.writeObject("removed");
                        } else {
                            objectOutputStream.writeObject("failed");
                        }
                        break;
                }
            case 2:
                 getAction =  controller.getCacheHit()+ " " + controller.getDataModelFromCache() + " " + controller.getSwap() + " " + controller.getCapacity() + " " + controller.getUsedCapacity();
                 objectOutputStream.writeObject(getAction);
        }

            socket.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}










//    @Override
//    public void run() {
//        Gson gson = new GsonBuilder().create();
//
//
//
//        try {
//
//            inputString = (String) objectInputStream.readObject();
//
//            Type req = new TypeToken<Request<DataModel<T>[]>>(){}.getType();
//            Request<DataModel<T>[]> request = gson.fromJson(inputString, req);
//
//            Map header = request.getHeader();
//
//            action = header.get("action").toString().toUpperCase().trim();
//
//            switch (action) {
//                case "UPDATE":
//                    if (controller.update(request.getBody())) {
//                        objectOutputStream.writeObject(controller.update(request.getBody()));
//                    } else {
//                        System.out.println("something was wrong");
//                    }
//                    break;
//                case "GET":
//                    DataModel<T>[] dataModels = controller.get(request.getBody());
//                    response = new Response(request.getHeader(), dataModels);
//                    outputString = gson.toJson(response);
//                    objectOutputStream.writeObject(outputString);
//
//                    break;
//                case "DELETE":
//                    if (controller.delete(request.getBody())) {
//                        objectOutputStream.writeObject("removed");
//                    } else {
//                        objectOutputStream.writeObject("rekj");
//                    }
//                    break;
//                case "":controller.getCacheHit();
//            }
////            }
//            socket.close();
//        } catch (IOException | NullPointerException e) {
//            e.printStackTrace();
//        }
//        catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
