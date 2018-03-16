package me.limeice.common.function;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Deep clone(深度复制)
 *
 * @author Lime(LimeVista)
 *         Created at 2016.08.18
 *         Update 2018.03.17
 * @version 1.0
 */
public class DeepClone {

    /**
     * deep clone
     *
     * @param obj a Object extends Serializable
     * @param <T> Object Type
     * @return copy of the object
     * @throws IOException            IOException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public static <T extends Serializable> T deepClone(T obj) throws IOException, ClassNotFoundException {
        // serialize
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);

        // deserialize
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        //noinspection unchecked
        return (T) ois.readObject();
    }

    /**
     * serialize a object
     *
     * @param file save file path
     * @param obj  object
     * @param <T>  extends Serializable
     */
    public static <T extends Serializable> void serialize(@Nullable File file, @Nullable T obj) {
        if (file == null || obj == null)
            return;
        ObjectOutputStream out = null;
        FileOutputStream outFile = null;
        try {
            IOUtils.checkFileIfNotExistCreate(file);
            outFile = new FileOutputStream(file);
            out = new ObjectOutputStream(outFile);
            out.writeObject(obj);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            CloseUtils.closeIOQuietly(out, outFile);
        }
    }

    /**
     * deserialize a Object
     *
     * @param file file
     * @param <T>  Object Class
     * @return Object
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <T extends Serializable> T deserialize(@NonNull File file) {
        if (!file.exists()) return null;
        ObjectInputStream inputObj = null;
        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
            inputObj = new ObjectInputStream(input);
            return (T) inputObj.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new RuntimeException(e);
        } finally {
            CloseUtils.closeIOQuietly(input, inputObj);
        }
    }
}
