package me.limeice.common.function.algorithm.util;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 通过链表方式实现Stack
 * <p>
 * <a href="https://en.wikipedia.org/wiki/Stack_(abstract_data_type)">Stack详细介绍</a>
 * <a href="https://en.wikipedia.org/wiki/Linked_list">关于链表</a>
 *
 * @param <E> 泛型约束
 */
@SuppressWarnings("WeakerAccess")
public class LinkedStack<E> implements IStack<E>, Serializable {

    transient int size = 0;

    transient Node<E> head = null;

    transient Node<E> last = null;

    @NonNull
    @Override
    public Iterator<E> iterator() {
        checkNonNull();
        return new StackIterator(head);
    }

    /**
     * 包含元素实际个数
     *
     * @return 数量
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * 栈是否为空
     *
     * @return if {@code true} empty.
     */
    @Override
    public boolean isEmpty() {
        return size <= 0 || head == null;
    }

    /**
     * 栈底
     *
     * @return 返回栈底部
     */
    @Override
    @NonNull
    public E first() {
        checkNonNull();
        return head.item;
    }

    /**
     * 栈顶
     *
     * @return 返回栈顶部
     */
    @NonNull
    @Override
    public E last() {
        checkNonNull();
        return last.item;
    }

    /**
     * 压栈
     *
     * @param e 元素
     */
    @Override
    public LinkedStack<E> push(E e) {
        if (head == null) {
            head = new Node<>(null, e, null);
            last = head;
        } else {
            Node<E> newLast = new Node<>(last, e, null);
            last.next = newLast;
            last = newLast;
        }
        size++;
        return this;
    }

    /**
     * 出栈
     *
     * @return 出栈元素
     */
    @Override
    public E pop() {
        checkNonNull();
        if (last == head) {
            Node<E> node = head;
            last = null;
            head = null;
            size = 0;
            return node.item;
        }
        Node<E> node = last;
        last = last.prev;
        last.next = null;
        size--;
        return node.item;
    }

    /**
     * 清空栈
     */
    @Override
    public void clear() {
        last = null;
        head = null;
        size = 0;
    }

    /**
     * 按照队列顺序遍历（先进先遍历）
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     * @since 1.8
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        for (E e : this) action.accept(e);
    }

    /**
     * 深度克隆
     *
     * @return 副本
     */
    @Override
    public LinkedStack<E> deepClone() {
        LinkedStack<E> stack = new LinkedStack<>();
        for (E e : this)
            stack.push(e);
        return stack;
    }

    /**
     * 删除指定数据
     *
     * @param o 如果数据存在多个相同，优先删除栈底方向数据
     * @return if {@code true} remove successful
     */
    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = head; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = head; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 删除指定数据
     *
     * @param index 索引（从栈底开始）
     * @return if {@code true} remove successful
     */
    @Override
    public E removeAt(final int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        Node<E> p = head;
        for (int i = 0; i < index; i++)
            p = p.next;
        return unlink(p.next);
    }

    /**
     * UnLink Node （删除节点）
     *
     * @param node 节点
     * @return 删除值
     */
    E unlink(Node<E> node) {
        final E element = node.item;
        final Node<E> prev = node.prev;
        final Node<E> next = node.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.item = null;
        size--;
        return element;
    }


    /**
     * 节点
     */
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * 迭代器
     */
    private class StackIterator implements Iterator<E> {

        transient Node<E> node;     // 迭代当前节点

        transient Node<E> next;     // 迭代下一个节点

        int lastModified = size;    // 最后一次修改大小

        StackIterator(Node<E> node) {
            this.node = new Node<>(null, null, node);
        }

        /**
         * 是否存在下一个元素
         *
         * @return {@code true} is exist ,but {@code false} not exist
         */
        @Override
        public boolean hasNext() {
            if (node != null)
                return node.next != null;
            return next != null;
        }

        /**
         * 请在调用hasNext确定数据存在的时候使用
         *
         * @return 获取下一个元素
         */
        @Override
        public E next() {
            if (lastModified != size)
                throw new ConcurrentModificationException();
            if (node != null) {
                node = node.next;
                return node.item;
            } else {
                node = next;
                next = null;
                return node.item;
            }
        }

        /**
         * 删除当前next节点
         */
        @Override
        public void remove() {
            if (lastModified != size)
                throw new ConcurrentModificationException();
            next = node.next;
            unlink(node);
            node = null;
            lastModified--;
        }
    }

    private void checkNonNull() {
        if (isEmpty())
            throw new NullPointerException("The stack is empty!");
    }

    /**
     * Saves the state of this {@code LinkedStack} instance to a stream
     * (that is, serializes it).
     *
     * @serialData The size of the list (the number of elements it
     * contains) is emitted (int), followed by all of its
     * elements (each an Object) in the proper order.
     */
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        // Write out any hidden serialization magic
        s.defaultWriteObject();

        // Write out size
        s.writeInt(size);

        // Write out all elements in the proper order.
        for (Node<E> x = head; x != null; x = x.next)
            s.writeObject(x.item);
    }

    /**
     * Reconstitutes this {@code LinkedStack} instance from a stream
     * (that is, deserializes it).
     */
    @SuppressWarnings("unchecked")
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        // Read in any hidden serialization magic
        s.defaultReadObject();

        // Read in size
        int size = s.readInt();

        // Read in all elements in the proper order.
        for (int i = 0; i < size; i++)
            push((E) s.readObject());
    }
}