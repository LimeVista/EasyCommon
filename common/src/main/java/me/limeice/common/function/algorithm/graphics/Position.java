package me.limeice.common.function.algorithm.graphics;

/**
 * 用于位置的表述或者平面向量的表述
 */
@SuppressWarnings("WeakerAccess")
public class Position {

    /**
     * x轴
     */
    public float x;

    /**
     * y轴
     */
    public float y;

    public Position() {
        super();
    }

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}