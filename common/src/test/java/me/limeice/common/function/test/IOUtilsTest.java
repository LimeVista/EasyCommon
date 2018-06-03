package me.limeice.common.function.test;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import me.limeice.common.function.CloseUtils;
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
    public void write() throws IOException {
        File file = new File(mFileHome, "PointInPolygonTest.png");
        byte[] bs1 = IOUtils.read(file);
        File writeFile = new File(mTmpDir, "PointInPolygonTest.png");
        delete(writeFile);
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

        delete(writeFile);
    }


    @Test
    public void writeAppend() throws IOException {
        File writeFile = new File(mTmpDir, "PointInPolygonTest.png");
        IOUtils.write(writeFile, TEST_MSG);
        IOUtils.writeAppend(writeFile, TEST_MSG.getBytes());
        assertFalse(TEST_MSG.equals(new String(IOUtils.read(writeFile))));

        delete(writeFile);
    }

    @Test
    public void checkFileIfNotExistCreate() throws IOException {
        File file = new File(mFileHome, "PointInPolygonTest.png");
        IOUtils.checkFileIfNotExistCreate(file);
    }

    @Test
    public void zip() throws IOException {
        File file = new File(mFileHome, "PointInPolygonTest.png");
        File writeFile = new File(mTmpDir, "PointInPolygonTest.png");
        IOUtils.zip(new FileInputStream(file), new FileOutputStream(writeFile));
        assertTrue(writeFile.length() > 0);
        delete(writeFile);
    }

    @Test
    public void unzip() throws IOException {
        File file = new File(mFileHome, "PointInPolygonTest.png");
        File writeFile = new File(mTmpDir, "PointInPolygonTest.png");
        IOUtils.zip(new FileInputStream(file), new FileOutputStream(writeFile));

        File writeFile2 = new File(mTmpDir, "PointInPolygonTest.png.bak");
        IOUtils.unzip(new FileInputStream(writeFile), new FileOutputStream(writeFile2));
        assertTrue(file.length() == writeFile2.length());
        delete(writeFile, writeFile2);
    }

    @Test
    public void copyFile() {
        File file = new File(mFileHome, "PointInPolygonTest.png");
        File writeFile = new File(mTmpDir, "PointInPolygonTest.png");
        assertTrue(IOUtils.copyFile(file, writeFile));
        delete(writeFile);
    }

    @Test
    public void moveFile() {
        File file = new File(mFileHome, "PointInPolygonTest.png");
        File writeFile = new File(mTmpDir, "PointInPolygonTest.png");
        File writeFile2 = new File(mTmpDir, "PointInPolygonTest.png.bak");
        assertTrue(IOUtils.copyFile(file, writeFile));
        assertTrue(IOUtils.moveFile(writeFile, writeFile2));
        delete(writeFile, writeFile2);
    }

    @Test
    public void copy() throws IOException {
        File file = new File(mFileHome, "PointInPolygonTest.png");
        File writeFile = new File(mTmpDir, "PointInPolygonTest.png");
        File writeFile2 = new File(mTmpDir, "PointInPolygonTest.png.bak");
        long len1 = IOUtils.copy(file, writeFile);
        FileInputStream in = new FileInputStream(file);
        long len2 = IOUtils.copy(in, writeFile2);
        assertEquals(len1, len2);
        delete(writeFile2);
        FileOutputStream out = new FileOutputStream(writeFile2);
        CloseUtils.closeIOQuietly(in);
        in = new FileInputStream(file);
        len1 = IOUtils.copy(in, out);
        assertEquals(len2, len1);
        delete(writeFile, writeFile2);
    }

    private void delete(File... files) {
        if (files == null)
            return;
        for (File file : files)
            //noinspection ResultOfMethodCallIgnored
            file.delete();
    }

    @Test
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void deleteDir0() throws IOException {
        File f = new File(mTmpDir, "0me.lime.common.test.dir_0");
        if (f.mkdirs()) {
            for (int i = 0; i < 8; i++) {
                File fChild = new File(f, "child" + i);
                File fChildDir = new File(f, "dir" + i);
                fChild.createNewFile();
                fChildDir.mkdirs();
            }
        }
        IOUtils.deleteDirectoryQuietly(f);
    }

    @Test
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void deleteDir1() throws IOException {
        File f = new File(mTmpDir, "0me.lime.common.test.dir_1");
        if (f.mkdirs()) {
            for (int i = 0; i < 8; i++) {
                File fChild = new File(f, "child" + i);
                File fChildDir = new File(f, "dir" + i);
                fChild.createNewFile();
                fChildDir.mkdirs();
            }
        }
        IOUtils.deleteDirectory(f);
    }
}