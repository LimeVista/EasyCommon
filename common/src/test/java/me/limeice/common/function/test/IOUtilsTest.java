package me.limeice.common.function.test;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import me.limeice.common.function.IOUtils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class IOUtilsTest {

    private File mFileHome = new File(
            new File(new File(".").getAbsolutePath()).getParentFile().getParentFile(),
            "art"
    );

    private File mTmpDir = new File(System.getProperty("java.io.tmpdir"));

    private static final String TEST_MSG = "me.limeice.common.test";

    @Test
    public void read() throws IOException {
        File file = new File(mFileHome, "PointInPolygonTest.png");
        byte[] bs1 = IOUtils.read(file);
        assertNotNull(bs1);
        byte[] bs2 = IOUtils.read(file.getAbsolutePath());
        assertNotNull(bs2);
        FileInputStream input = new FileInputStream(file);
        byte[] bs3 = IOUtils.read(input);
        assertNotNull(bs3);
        assertArrayEquals(bs1, bs2);
        assertArrayEquals(bs2, bs3);
    }


    @Test
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void write() throws IOException {
        File file = new File(mFileHome, "PointInPolygonTest.png");
        byte[] bs1 = IOUtils.read(file);
        File writeFile = new File(mTmpDir, "PointInPolygonTest.png");
        writeFile.delete();
        IOUtils.write(writeFile, bs1);

        // Check
        byte[] bs2 = IOUtils.read(file);
        assertNotNull(bs2);

        boolean delResult = writeFile.delete();
        assertTrue(delResult);
        IOUtils.write(writeFile, TEST_MSG);
        String readMsg = new String(IOUtils.read(writeFile));
        assertEquals(readMsg, TEST_MSG);

        delResult = writeFile.delete();
        assertTrue(delResult);
        IOUtils.write(writeFile.getAbsolutePath(), TEST_MSG);
        readMsg = new String(IOUtils.read(writeFile));
        assertEquals(readMsg, TEST_MSG);

        delResult = writeFile.delete();
        assertTrue(delResult);
        IOUtils.write(writeFile.getAbsolutePath(), bs2);

        writeFile.delete();
    }


    @Test
    public void writeAppend() throws IOException {
        File writeFile = new File(mTmpDir, "PointInPolygonTest.png");
        IOUtils.write(writeFile, TEST_MSG);
        IOUtils.writeAppend(writeFile, TEST_MSG.getBytes());
        assertFalse(TEST_MSG.equals(new String(IOUtils.read(writeFile))));

        //noinspection ResultOfMethodCallIgnored
        writeFile.delete();
    }

    @Test
    public void checkFileIfNotExistCreate() throws IOException {
        File file = new File(mFileHome, "PointInPolygonTest.png");
        IOUtils.checkFileIfNotExistCreate(file);
    }

    @Test
    public void zip() {
    }

    @Test
    public void unzip() {
    }

    @Test
    public void copyFile() {
    }

    @Test
    public void moveFile() {
    }

    @Test
    public void copy() {
    }
}