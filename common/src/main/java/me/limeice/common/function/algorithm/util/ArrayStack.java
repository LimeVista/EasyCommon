package me.limeice.common.function.algorithm.util;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.function.Consumer;


@SuppressWarnings({"NonAtomicOperationOnVolatileField", "WeakerAccess"})
public class ArrayStack<E> implements IStack<E>, Serializable {

    /**
     * The Array default capacity
     */
    private static final int INIT_CAPACITY = 8;

    /**
     * Array Stack size
     */
    private volatile int size;

    /**
     * Array Stack element data array
     */
    private transient Object[] elementData;

    public ArrayStack() {
        this(INIT_CAPACITY);
    }

    public ArrayStack(int initCapacity) {
        size = 0;
        elementData = new Object[initCapacity > 0 ? initCapacity : INIT_CAPACITY];
    }

    /**
     * 压栈
     *
     * @param e 元素
     */
    public ArrayStack<E> push(E e) {
        if (elementData.length == size) {
            Object[] _data = new Object[elementData.length << 1];
            System.arraycopy(elementData, 0, _data, 0, size);
            elementData = _data;
            System.gc();
        }
        elementData[size++] = e;
        return this;
    }

    /**
     * 出栈
     *
     * @return 出栈元素
     */
    @Override
    public E pop() {
        if (size <= 0)
            return null;
        //noinspection unchecked
        E e = (E) elementData[--size];
        elementData[size] = null;
        return e;
    }

    /**
     * 清空栈
     */
    @Override
    public void clear() {
        size = 0;
        elementData = new Object[INIT_CAPACITY];
    }

    /**
     * 深度克隆
     *
     * @return 副本
     */
    @Override
    public IStack<E> deepClone() {
        ArrayStack<E> as = new ArrayStack<>(elementData.length);
        System.arraycopy(elementData, 0, as.elementData, 0, as.elementData.length);
        as.size = size;
        return as;
    }

    /**
     * 包含元素实际个数
     *
     * @return 数量
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 栈是否为空
     *
     * @return if {@code true} empty.
     */
    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    /**
     * 获取数组容量
     *
     * @return 数组容量
     */
    public int getCapacity() {
        return elementData.length;
    }

    /**
     * 删除
     *
     * @param index 索引下标
     * @return 被删除数据
     */
    @Override
    public E removeAt(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException("Array is IndexOutOfBoundsException,index = "
                    + index + ", Size = " + size);

        //noinspection unchecked
        E oldValue = (E) elementData[index];

        fastRemove(index);
        return oldValue;
    }

    /**
     * 删除
     *
     * @param o 需要Remove的元素
     * @return 是否删除成功
     */
    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    /**
     * 返回最后一个元素（栈顶）
     *
     * @return 元素
     */
    @Override
    public E last() {
        if (size < 1)
            throw new IndexOutOfBoundsException("Array is IndexOutOfBoundsException,index = "
                    + (size - 1) + ", Size = " + size);
        //noinspection unchecked
        return (E) elementData[size - 1];
    }

    /**
     * 返回第一个元素（栈底）
     *
     * @return 元素
     */
    @Override
    public E first() {
        if (size < 1)
            throw new IndexOutOfBoundsException("Array is Empty.");
        //noinspection unchecked
        return (E) elementData[0];
    }

    /**
     * 遍历
     *
     * @param consumer 回调处理
     */
    public void foreach(me.limeice.common.function.Consumer<E> consumer) {
        for (int i = 0; i < size; i++) {
            //noinspection unchecked
            consumer.accept((E) elementData[i]);
        }
    }

    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Unless otherwise specified by the implementing class,
     * actions are performed in the order of iteration (if an iteration order
     * is specified).  Exceptions thrown by the action are relayed to the
     * caller.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     *
     * <p>The default implementation behaves as if:
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     * @since 1.8
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void forEach(Consumer<? super E> action) {
        for (int i = 0; i < size; i++) {
            //noinspection unchecked
            action.accept((E) elementData[i]);
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @NonNull
    @Override
    public Iterator<E> iterator() {
        return new StackIterator();
    }


    /**
     * 快速删除
     *
     * @param index 索引下标
     */
    private synchronized void fastRemove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = null; // clear to let GC do its work
    }

    /**
     * Save the state of the <tt>ArrayList</tt> instance to a stream (that
     * is, serialize it).
     *
     * @serialData The length of the array backing the <tt>ArrayList</tt>
     * instance is emitted (int), followed by all of its elements
     * (each an <tt>Object</tt>) in the proper order.
     */
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        // Write out element count, and any hidden stuff
        int expectedModCount = size;
        s.defaultWriteObject();

        // Write out all elements in the proper order.
        for (int i = 0; i < size; i++)
            s.writeObject(elementData[i]);

        if (size != expectedModCount)
            throw new ConcurrentModificationException();
    }

    /**
     * Reconstitute the <tt>ArrayList</tt> instance from a stream (that is,
     * deserialize it).
     */
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        // Read in size, and any hidden stuff
        s.defaultReadObject();

        // Read in capacity
        s.readInt(); // ignored

        if (size > 0) {
            if (elementData == null || elementData.length < size)
                elementData = new Object[size];
            // be like clone(), allocate array based upon size not capacity
            Object[] a = elementData;
            // Read in all elements in the proper order.
            for (int i = 0; i < size; i++)
                a[i] = s.readObject();
        } else {
            if (elementData == null || elementData.length == 0)
                elementData = new Object[INIT_CAPACITY];
        }
    }

    private class StackIterator implements Iterator<E> {

        volatile int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }


        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            return (E) elementData[index++];
        }


        @Override
        public void remove() {
            fastRemove(index--);
        }
    }
}