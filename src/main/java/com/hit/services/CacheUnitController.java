package main.java.com.hit.services;

import main.java.com.hit.algorithm.IAlgoCache;
import main.java.com.hit.dm.DataModel;

import java.io.IOException;

public class CacheUnitController<T> {

    private CacheUnitService cacheUnitService;

    public CacheUnitController(IAlgoCache algoCache){
        this.cacheUnitService = new CacheUnitService(algoCache);
    }

    public boolean delete(DataModel<T>[] dataModels){
        return cacheUnitService.delete(dataModels);
    }
    public DataModel<T>[] get(DataModel<T>[] dataModels){
        return cacheUnitService.get(dataModels);
    }
    // gets the num of requst from cacheunitservice
    public int getDataModelFromCache() { return cacheUnitService.getDataModelFromCache(); }
  // get the num of the page that we get and they found in cache
    public int getCacheHit(){ return cacheUnitService.getCacheHit(); }
    public int getSwap(){ return cacheUnitService.getSwap();
    }

    public int getUsedCapacity(){ return cacheUnitService.getUsedCapacity(); }

    public int getCapacity(){ return cacheUnitService.getCapacity(); }

    public boolean update (DataModel<T>[] dataModels) throws IOException { return cacheUnitService.update(dataModels); }

}
