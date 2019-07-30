package main.java.com.hit.dao;

import main.java.com.hit.dm.DataModel;

import java.io.*;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {

    private int capacity;
//    private BufferedWriter bufferedWriter;
    private String filePath;
    private ObjectOutputStream objectOutput;
    private ObjectInputStream objectInput;
    private Hashtable<Long,DataModel<T>> map;

    public DaoFileImpl( String filePath, int capacity) {
        this.filePath = filePath;
        this.capacity = capacity;
        this.map = new Hashtable<>();

    }

    public DaoFileImpl(String filepath){
        this.filePath = filepath;
        this.map = new Hashtable<>();
    }

    @Override
    public void delete(DataModel<T> entity) {

        inputConnection();
        map.remove(entity.getId());
        outputConnection();
        closeStreams();

    }

    @Override
     public DataModel<T> find(Long id) {

        inputConnection();
        DataModel dataModel = this.map.get(id);
        outputConnection();
        closeStreams();
        return dataModel;
    }

    @Override
    public void save(DataModel<T> entity) {
        inputConnection();
        map.put(entity.getId(), entity);
        outputConnection();
        closeStreams();
    }


    private void inputConnection(){
        try {
            objectInput = new ObjectInputStream(new FileInputStream(filePath));
            map = (Hashtable) objectInput.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void outputConnection(){
        try {
            objectOutput = new ObjectOutputStream(new FileOutputStream(filePath));
            objectOutput.writeObject(map);
            objectOutput.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeStreams()
    {
        try
        {
            objectInput.close ();
            objectOutput.close ();
        } catch (IOException e)
        {
            e.printStackTrace ();
        }
    }
}

