package main.java.com.hit.memory;

import java.io.IOException;

import main.java.com.hit.algorithm.IAlgoCache;
import main.java.com.hit.dao.IDao;
import main.java.com.hit.dm.DataModel;


public class CacheUnit<T> {

    private IAlgoCache<Long, DataModel<T>> cache;
    private IDao dao ;
    //static because we want to save the stats along the program is alive
    private static int cacheHit = 0;
    private static int getRequest =0;
    
    public CacheUnit(IAlgoCache<Long, DataModel<T>> algo, IDao<Long, DataModel<T>> dao) {
        this.cache = algo;
        this.dao = dao;
    }

    public CacheUnit() { }

    public int getSwap(){
        return cache.getSwap();
    }

    public int getUsedCapacity(){
        return cache.getUsedCapacity();
    }

    public int getCapacity(){
        return cache.getCapacity();
    }

    public DataModel<T>[] getDataModels(Long[] ids){
        DataModel[] models = new DataModel[ids.length];
        DataModel dataModel;
        for(int i=0; i<ids.length; i++) {
            // gets the num of request(we need to count both cache and dao)
            getRequest++;
            if(cache.getElement(ids[i]) != null) {
                dataModel = new DataModel(ids[i],cache.getElement(ids[i]));
                models[i] = dataModel;
                cacheHit++;
            }
            else if (dao.find(ids[i]) != null) {
                dataModel = new DataModel(ids[i],dao.find(ids[i]));
                models[i] = dataModel;
            }
        }
        return models;
    }

    //put dataModels in cacheUnit and dao

    public DataModel<T>[] putDataModels(DataModel<T>[] datamodels) throws IOException {
        try {
            DataModel<T>[] oldDataModels = new DataModel[datamodels.length];
            int i = 0;
            for (DataModel data : datamodels){
                if (dao.find(data.getId()) == null){
                    dao.save(data);
                }
            }
            for (DataModel data : datamodels) {
                DataModel dataModel = cache.putElement(data.getId(), data);
                if (dataModel != null && dataModel != data) {
                    oldDataModels[i++] = dataModel;
                }
            }
            return oldDataModels;

        }catch (NullPointerException e){
            e.printStackTrace();
        }
        
        return null;
    }

    public void removeDataModels(Long[] ids) {
        DataModel dataModel;
        for (Long id : ids) {
            dataModel = (DataModel) dao.find(id);
            if(dataModel !=null){
                dao.delete(dataModel);
            }
            cache.removeElement(id);
        }
    }

    public int getDataModelFromCache() {
        return getRequest;
    }

    public int getCacheHit(){
        return cacheHit;
    }
}
