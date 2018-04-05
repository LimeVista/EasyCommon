package me.limeice.common.datahelper;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DataHelperTest {
    private String dir = System.getProperty("java.io.tmpdir");

    @Test
    public void writer() throws Exception {
        DataHelper helper = new DataHelper(new File(dir, "LimeData.test"));
        Writer w = helper.writer();
        w.put((short) 1, 10086);
        String[] s = new String[]{"ABC", "Lime"};
        w.put((short) 2, s);
        w.put((short) 3, "2018|안녕하세요|hello|你好|こんにちは|cześć|Olá");
        w.put((short) 4, getTestIntList());
        assertTrue(w.commit());
    }

    @Test
    public void reader() {
        DataHelper helper = new DataHelper(new File(dir, "LimeData.test"), DataHelper.MODE_READ_ID);
        Reader r = helper.reader();
        int i = r.readInt((short) 1);
        String[] s = r.readStringArray1((short) 2);
        String v3 = r.readString((short) 3);
        System.out.println("v1:" + i + ", v2:" + s[0] + ", v3:" + v3);
    }

    private List<Integer> getTestIntList() {
        List<Integer> list = new ArrayList<>();
        list.add(12565);
        list.add(8986);
        list.add(566);
        return list;
    }
}
