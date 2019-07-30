package main.java.com.hit.driver;

import main.java.com.hit.controller.CacheUnitController;
import main.java.com.hit.controller.Controller;
import main.java.com.hit.model.CacheUnitModel;
import main.java.com.hit.model.Model;
import main.java.com.hit.view.CacheUnitView;
import main.java.com.hit.view.View;

public class Driver {

    public static void main(String[] args) {

        Model model = new CacheUnitModel();
        View view = new CacheUnitView();
        Controller controller = new CacheUnitController(model, view);
        ((CacheUnitModel) model).addObserver(controller);
        ((CacheUnitView) view).addObserver(controller);
        view.start();
    }
}

