package me.limeice.common.function;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import me.limeice.common.function.exception.SerializableException;


/**
 * 深度复制
 *
 * @author Lime(LimeVista)
 *         Created at 2016.08.18
 * @version 1.0
 */
public class DeepClone {

    /**
     * 深度克隆（被克隆类必须继承Serializable）
     *
     * @param obj 被克隆类
     * @param <T> 泛型约束
     * @return 克隆类
     * @throws IOException            IOException
     * @throws ClassNotFoundException ClassNotFoundException
     * @throws SerializableException  SerializableException
     */
    public static <T> T deepClone(T obj) throws IOException, ClassNotFoundException, SerializableException {
        if (!(obj instanceof Serializable)) throw new SerializableException();
        // 序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);

        // 反序列化
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        //noinspection unchecked
        return (T) ois.readObject();
    }
}
