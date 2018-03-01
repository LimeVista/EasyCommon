package me.limeice.common.function;

import android.support.annotation.Nullable;

@SuppressWarnings("WeakerAccess")
public class QStack<E> {

    private volatile Object[] elementData;

    private volatile int size;

    private volatile int capacity;

    public QStack() {
        this(8);
    }

    public QStack(int initCapacity) {
        size = 0;
        capacity = initCapacity;
        elementData = new Object[capacity];
    }

    public QStack push(E e) {
        if (capacity == size) {
            capacity <<= 1;
            Object[] _data = new Object[capacity];
            System.arraycopy(elementData, 0, _data, 0, size);
            elementData = _data;
            System.gc();
        }
        elementData[size++] = e;
        return this;
    }

    @Nullable
    public E pop() {
        if (size <= 0)
            return null;
        //noinspection unchecked
        E e = (E) elementData[--size];
        elementData[size] = null;
        return e;
    }

    public void clear() {
        size = 0;
        elementData = new Object[capacity];
    }

    public int size() {
        return size;
    }

    /**
     * 获取数组容量
     *
     * @return 数组容量
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * 删除
     *
     * @param index 索引下标
     * @return 被删除数据
     */
    public E remove(int index) {
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
    public boolean remove(E o) {
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
    public E first() {
        if (size < 1)
            throw new IndexOutOfBoundsException("Array is Empty.");
        //noinspection unchecked
        return (E) elementData[0];
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
     * 遍历
     *
     * @param consumer 回调处理
     */
    public void forEach(Consumer<E> consumer) {
        for (int i = size - 1; i >= 0; i++) {
            //noinspection unchecked
            consumer.accept((E) elementData[i]);
        }
    }
}