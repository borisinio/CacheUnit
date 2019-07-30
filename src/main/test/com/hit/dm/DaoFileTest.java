//package main.test.com.hit.dm;
//
//import main.java.com.hit.dao.DaoFileImpl;
//import main.java.com.hit.dm.DataModel;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class DaoFileTest  {
//
//    long[] keys = {1, 2, 3, 4, 5};
//    String[] values = {"A", "B", "C", "D", "E"};
//
//    String expected = null;
//    String actual;
//
//    @Test
//    public void DaoFileTest() throws IOException,NullPointerException {
//        DaoFileImpl daoFile = new DaoFileImpl("path", 5);
//        DataModel dataModel;
//        for (int i = 0; i < 5; i++) {
//            dataModel = new DataModel(keys[i], values[i]);
//
//            daoFile.save(dataModel);
//            expected = values[i];
//            actual = daoFile.find((long) i+1).getContent().toString();
//            assertEquals(expected, actual);
//        }
//
//        dataModel = new DataModel((long) 2,"B");
//        daoFile.delete(dataModel);
//        expected = "100";
//
//
//        actual = daoFile.find((long)2).getContent().toString();
//
//
//        assertEquals(expected, actual);
//
//        daoFile.save(new DataModel((long) 2, "j"));
//        expected = "j";
//        actual = daoFile.find((long) 2).getContent().toString();
//        assertEquals(expected,actual);
//    }
//
//}
