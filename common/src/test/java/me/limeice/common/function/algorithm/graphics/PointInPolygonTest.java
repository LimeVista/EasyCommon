package me.limeice.common.function.algorithm.graphics;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class PointInPolygonTest {

    /**
     * 测试图详见 art/PointInPolygonTest.png
     */
    @Test
    public void pnpoly() throws Exception {
        List<Position> pList = new ArrayList<Position>() {{
            add(new Position(147.5f, 209));
            add(new Position(238.5f, 219));
            add(new Position(172.5f, 277));
            add(new Position(51.5f, 244));
            add(new Position(49.5f, 176));
            add(new Position(160.9f, 95.5f));
            add(new Position(224.6f, 96.3f));
            add(new Position(156.5f, 129f));
            add(new Position(270.5f, 154f));
            add(new Position(126.5f, 161f));
            add(new Position(235.5f, 198f));
        }};
        Position[] ps = pList.toArray(new Position[0]);
        assertFalse(PointInPolygon.pnpoly(ps, 82, 118));
        assertFalse(PointInPolygon.pnpoly(ps, 225, 120));
        assertFalse(PointInPolygon.pnpoly(ps, 221.5f, 237.5f));
        assertFalse(PointInPolygon.pnpoly(ps, 191.5f, 208.f));
        assertTrue(PointInPolygon.pnpoly(ps, 169, 106));
        assertTrue(PointInPolygon.pnpoly(ps, 158, 189));
        assertTrue(PointInPolygon.pnpoly(ps, 78.5f, 232.5f));
        assertTrue(PointInPolygon.pnpoly(ps, 211.5f, 227.5f));
        assertTrue(PointInPolygon.pnpoly(ps, 241, 151));

        float[] pX = new float[ps.length];
        float[] pY = new float[ps.length];

        for (int i = 0; i < ps.length; i++) {
            pX[i] = ps[i].x;
            pY[i] = ps[i].y;
        }
        assertFalse(PointInPolygon.pnpoly(pX, pY, 82, 118));
        assertFalse(PointInPolygon.pnpoly(pX, pY, 225, 120));
        assertFalse(PointInPolygon.pnpoly(pX, pY, 221.5f, 237.5f));
        assertFalse(PointInPolygon.pnpoly(pX, pY, 191.5f, 208.f));
        assertTrue(PointInPolygon.pnpoly(pX, pY, 169, 106));
        assertTrue(PointInPolygon.pnpoly(pX, pY, 158, 189));
        assertTrue(PointInPolygon.pnpoly(pX, pY, 78.5f, 232.5f));
        assertTrue(PointInPolygon.pnpoly(pX, pY, 211.5f, 227.5f));
        assertTrue(PointInPolygon.pnpoly(pX, pY, 241, 151));
    }
}