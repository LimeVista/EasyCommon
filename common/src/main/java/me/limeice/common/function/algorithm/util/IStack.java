package me.limeice.common.function.algorithm.util;

/**
 * 栈接口
 * <p>
 * <a href="https://en.wikipedia.org/wiki/Stack_(abstract_data_type)">Stack详细介绍</a>
 *
 * @param <E> TYPE
 */
public interface IStack<E> extends Iterable<E> {

    /**
     * 包含元素实际个数
     *
     * @return 数量
     */
    int size();

    /**
     * 栈是否为空
     *
     * @return if {@code true} empty.
     */
    boolean isEmpty();

    /**
     * 栈底
     *
     * @return 返回栈底部
     */
    E first();

    /**
     * 栈顶
     *
     * @return 返回栈顶部
     */
    E last();

    /**
     * 压栈
     *
     * @param e 元素
     */
    IStack<E> push(E e);

    /**
     * 出栈
     *
     * @return 出栈元素
     */
    E pop();

    /**
     * 清空栈
     */
    void clear();

    /**
     * 深度克隆
     *
     * @return 副本
     */
    IStack<E> deepClone();

    /**
     * 删除指定数据
     *
     * @param o 如果数据存在多个相同，优先删除栈底方向数据
     * @return if {@code true} remove successful
     */
    boolean remove(Object o);

    /**
     * 删除指定数据
     *
     * @param index 索引（从栈底开始）
     * @return if {@code true} remove successful
     */
    E removeAt(final int index);
}
