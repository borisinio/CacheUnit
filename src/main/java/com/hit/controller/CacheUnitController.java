package main.java.com.hit.controller;

import main.java.com.hit.model.Model;
import main.java.com.hit.util.ObserMessage;
import main.java.com.hit.view.View;


import java.util.Observable;

public class CacheUnitController implements Controller{

    private Model model;
    private View view;
    private static String id;

    public CacheUnitController(Model model, View view)
    {
        this.model = model;
        this.view = view;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        id = null;
        ObserMessage update = (ObserMessage) arg;

        if(update.getSentIdentifier ().equals ("view"))
        {
            model.updateModelData(update);
        }

        if(update.getSentIdentifier ().equals ("model-load"))
        {
            view.updateUIData(new ObserMessage("load",update.getMessege()));
        }

        if(update.getSentIdentifier ().equals ("model-stats"))
        {
            view.updateUIData(new ObserMessage("stats",update.getMessege()));
        }

        if(update.getSentIdentifier().equals("net-crash"))
        {
            view.updateUIData(new ObserMessage("load","false"));
        }

    }

}
