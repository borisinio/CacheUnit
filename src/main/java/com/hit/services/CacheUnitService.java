package main.java.com.hit.services;

import main.java.com.hit.algorithm.IAlgoCache;
import main.java.com.hit.algorithm.LRUAlgoCacheImpl;
import main.java.com.hit.dao.DaoFileImpl;
import main.java.com.hit.dm.DataModel;
import main.java.com.hit.memory.CacheUnit;

import java.io.IOException;

public class CacheUnitService<T> {
    private CacheUnit cacheUnit;

    public CacheUnitService(){
        this.cacheUnit = new CacheUnit((new LRUAlgoCacheImpl(5)),new DaoFileImpl<>("DataSource.txt"));
    }

    public CacheUnitService(IAlgoCache algoCache){
        this.cacheUnit = new CacheUnit(algoCache,new DaoFileImpl<>("DataSource.txt"));
    }

    public int getSwap(){
        return this.cacheUnit.getSwap();
    }

    public int getUsedCapacity(){
        return this.cacheUnit.getUsedCapacity();
    }

    public int getCapacity(){
        return this.cacheUnit.getCapacity();
    }
    public boolean delete(DataModel<T>[] dataModels){
        Long[] ids = new Long[dataModels.length];
        for (int i=0;i<dataModels.length;i++){
            ids[i] = dataModels[i].getId();
        }
        cacheUnit.removeDataModels(ids);
        DataModel<T>[] dataModels1 = cacheUnit.getDataModels(ids);
        if(dataModels1 != null)
            return true;
        return false;
    }

    public DataModel<T>[] get(DataModel<T>[] dataModels){
        Long[] ids = new Long[dataModels.length];
        for (int i=0;i<dataModels.length;i++){
            ids[i] = dataModels[i].getId();
        }
          return cacheUnit.getDataModels(ids);
    }

    public boolean update(DataModel<T>[] dataModels) throws IOException {
        cacheUnit.putDataModels(dataModels);
        if(dataModels.length == this.get(dataModels).length)
            return true;
        return false;
    }
    
    public int getDataModelFromCache() { return cacheUnit.getDataModelFromCache(); }
    public int getCacheHit(){ return cacheUnit.getCacheHit(); }
}

