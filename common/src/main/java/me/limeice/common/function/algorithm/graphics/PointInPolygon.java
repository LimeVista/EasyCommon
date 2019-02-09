package me.limeice.common.function.algorithm.graphics;

import android.graphics.PointF;
import android.support.annotation.NonNull;

/**
 * 根据PointInPolygon算法,算法核心
 * 检查一点是否在多边形之内，可以作一射线从该点往任意方向投射。
 * 若让射线与多边形边的相交次数为奇，则点位于多边形之内。
 * 为了简化计算，一般会往水平或垂直方向投射。
 *
 * @see <a href="http://geomalgorithms.com/a03-_inclusion.html">PointInPolygon介绍</a>
 * @see <a href="http://alienryderflex.com/polyspline/">实现（C/C++）</a>
 */
public final class PointInPolygon {

    /**
     * 判定该点是否在一个封闭的区间内
     *
     * @param ps 多边形顶点
     * @param x  坐标x轴
     * @param y  坐标y轴
     * @return 是否在区间内
     */
    public static boolean pnpoly(@NonNull final Position[] ps, float x, float y) {
        boolean c = false;
        final int len = ps.length;
        for (int i = 0, j = len - 1; i < len; j = i++) {
            if ((((ps[i].y <= y) && (y < ps[j].y)) || ((ps[j].y <= y) && (y < ps[i].y)))
                    && (x < (ps[j].x - ps[i].x) * (y - ps[i].y) / (ps[j].y - ps[i].y) + ps[i].x))
                c = !c;
        }
        return c;
    }

    /**
     * 判定该点是否在一个封闭的区间内
     *
     * @param psX 多边形横坐标顶点集合
     * @param psY 多边形纵坐标顶点集合
     * @param x   坐标x轴
     * @param y   坐标y轴
     * @return 是否在区间内
     */
    public static boolean pnpoly(@NonNull final float[] psX, @NonNull final float[] psY, float x, float y) {
        boolean c = false;
        if (psX.length != psY.length)
            throw new IllegalArgumentException("psX length must eq psY length");
        final int len = psX.length;
        for (int i = 0, j = len - 1; i < len; j = i++) {
            if ((((psY[i] <= y) && (y < psY[j])) || ((psY[j] <= y) && (y < psY[i])))
                    && (x < (psX[j] - psX[i]) * (y - psY[i]) / (psY[j] - psY[i]) + psX[i]))
                c = !c;
        }
        return c;
    }

    /**
     * 判定该点是否在一个封闭的区间内
     *
     * @param ps 多边形顶点
     * @param x  坐标x轴
     * @param y  坐标y轴
     * @return 是否在区间内
     */
    public static boolean pnpoly(@NonNull final PointF[] ps, float x, float y) {
        boolean c = false;
        final int len = ps.length;
        for (int i = 0, j = len - 1; i < len; j = i++) {
            if ((((ps[i].y <= y) && (y < ps[j].y)) || ((ps[j].y <= y) && (y < ps[i].y)))
                    && (x < (ps[j].x - ps[i].x) * (y - ps[i].y) / (ps[j].y - ps[i].y) + ps[i].x))
                c = !c;
        }
        return c;
    }
}