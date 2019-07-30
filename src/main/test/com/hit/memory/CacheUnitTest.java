//package main.test.com.hit.memory;
//
//import com.hit.algorithm.LRUAlgoCacheImpl;
//import main.java.com.hit.dao.DaoFileImpl;
//import main.java.com.hit.dao.IDao;
//import main.java.com.hit.dm.DataModel;
//import main.java.com.hit.memory.CacheUnit;
//import org.junit.Test;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class CacheUnitTest {
//
//    @Test
//    public void CacheUnitTest() throws IOException {
//
//        CacheUnit cacheUnit = new CacheUnit(new LRUAlgoCacheImpl<>(5), new DaoFileImpl("path"));
//
//        long[] keys = {1, 2, 3, 4, 5};
//        String[] values = {"A", "B", "C", "D", "E"};
//
//        String expected = null;
//        String actual = null;
//
//        DataModel[] dataModel = new DataModel[5];
//
//        for (int i = 0; i < 5; i++) {
//            dataModel[i] = new DataModel(keys[i], values[i]);
//        }
//
//        cacheUnit.putDataModels(dataModel);
//
//        DataModel[] dataModels2 = new DataModel[5];
//        dataModels2[0] = new DataModel((long) 6,"H");
//        dataModels2[1] = new DataModel((long) 7,"I");
//        try {
//            cacheUnit.putDataModels(dataModels2);
//        }catch (NullPointerException e){
//            e.printStackTrace();
//        }
//
//        expected = "I H C D E";
//        Long[] ids = {(long)6,(long)7,(long)3,(long)4,(long)5};
//
//        for (int i = 0; i < 5; i++) {
//            actual = cacheUnit.getDataModels(ids).toString();
//
//        }
//        assertEquals(expected, actual);
//
//    }
//}
