package me.limeice.common.function.algorithm.util;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 通过链表方式实现Stack
 * <p>
 * <a href="https://en.wikipedia.org/wiki/Stack_(abstract_data_type)">Stack详细介绍</>
 * <a href="https://en.wikipedia.org/wiki/Linked_list">关于链表</>
 *
 * @param <E> 泛型约束
 */
@SuppressWarnings("WeakerAccess")
public class LinkedStack<E> implements Iterable<E>, Serializable {

    transient int size = 0;

    transient Node<E> head = null;

    transient Node<E> last = null;

    @NonNull
    @Override
    public Iterator<E> iterator() {
        checkNonNull();
        return new StackIterator(head);
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size <= 0 || head == null;
    }

    /**
     * 栈底
     *
     * @return 返回栈底部
     */
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
    public E last() {
        checkNonNull();
        return last.item;
    }

    /**
     * 压栈
     *
     * @param e 元素
     */
    public void push(E e) {
        if (head == null) {
            head = new Node<>(null, e, null);
            last = head;
        } else {
            Node<E> newLast = new Node<>(last, e, null);
            last.next = newLast;
            last = newLast;
        }
        size++;
    }

    /**
     * 出栈
     *
     * @return 出栈元素
     */
    public E poll() {
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
     * 按照队列顺序遍历（先进先遍历）
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * @implSpec <p>The default implementation behaves as if:
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
    public LinkedStack<E> deepClone() {
        LinkedStack<E> stack = new LinkedStack<>();
        for (E e : this)
            stack.push(e);
        return stack;
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

        transient Node<E> node;     // 迭代节点

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
            return node.next != null;
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
            node = node.next;
            return node.item;
        }

        /**
         * 删除当前next节点
         */
        @Override
        public void remove() {
            if (node == head && head == last) {
                head = null;
                last = null;
                node.next = null;
                size = 0;
                lastModified = 0;
                return;
            }
            if (node == head) {
                head = node.next;
                head.prev = null;
            } else if (node == last) {
                last = last.prev;
                last.next = null;
                node = last;
            } else {
                Node<E> next = node.next;
                Node<E> prev = node.prev;
                prev.next = next;
                next.prev = prev;
            }
            size--;
            lastModified--;
        }
    }

    private void checkNonNull() {
        if (isEmpty())
            throw new NullPointerException("The stack is empty!");
    }
}