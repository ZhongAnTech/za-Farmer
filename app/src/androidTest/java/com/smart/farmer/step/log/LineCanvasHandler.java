package com.smart.farmer.step.log;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.smart.farmer.step.log.ICanvasHandler;

public class LineCanvasHandler implements ICanvasHandler {

    private Point startPoint, endPoint;

    public LineCanvasHandler(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    @Override
    public void handle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);

        //中间画布操作
        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint);
        Path triangle = new Path();
        Point triangleP[] = getTrianPoint(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        triangle.moveTo(triangleP[0].x, triangleP[0].y);
        triangle.lineTo(triangleP[1].x, triangleP[1].y);
        triangle.lineTo(triangleP[2].x, triangleP[2].y);
        triangle.close();
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(triangle, paint);
        paint.setStyle(Paint.Style.STROKE);
    }


    private Point[] getTrianPoint(int sourceX, int sourceY, int targetX, int targetY) {
        Point rePoint[] = new Point[3];
        double H = 50;
        double L = 30;
        int x3 = 0;
        int y3 = 0;
        int x4 = 0;
        int y4 = 0;
        double awrad = Math.atan(L / H);
        double arraow_len = Math.sqrt(L * L + H * H);
        double[] arrXY_1 = rotateVec(targetX - sourceX, targetY - sourceY, awrad, true, arraow_len);
        double[] arrXY_2 = rotateVec(targetX - sourceX, targetY - sourceY, -awrad, true, arraow_len);
        double x_3 = targetX - arrXY_1[0];
        double y_3 = targetY - arrXY_1[1];
        double x_4 = targetX - arrXY_2[0];
        double y_4 = targetY - arrXY_2[1];
        Double X3 = new Double(x_3);
        x3 = X3.intValue();
        Double Y3 = new Double(y_3);
        y3 = Y3.intValue();
        Double X4 = new Double(x_4);
        x4 = X4.intValue();
        Double Y4 = new Double(y_4);
        y4 = Y4.intValue();

        Point p1 = new Point();
        Point p2 = new Point();
        Point p3 = new Point();
        p1.set(targetX, targetY);
        p2.set(x3, y3);
        p3.set(x4, y4);
        rePoint[0] = p1;
        rePoint[1] = p2;
        rePoint[2] = p3;
        return rePoint;

    }

    private double[] rotateVec(int px, int py, double ang, boolean isChLen, double newLen) {
        double mathstr[] = new double[2];
        double vx = px * Math.cos(ang) - py * Math.sin(ang);
        double vy = px * Math.sin(ang) + py * Math.cos(ang);
        if (isChLen) {
            double d = Math.sqrt(vx * vx + vy * vy);
            vx = vx / d * newLen;
            vy = vy / d * newLen;
            mathstr[0] = vx;
            mathstr[1] = vy;
        }
        return mathstr;
    }
}
