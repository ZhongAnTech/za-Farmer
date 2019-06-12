package com.smart.farmer.step.log;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.smart.farmer.step.log.ICanvasHandler;

public class PointCanvasHandler implements ICanvasHandler {

    private Point point;

    public PointCanvasHandler(Point point) {
        this.point = point;
    }

    @Override
    public void handle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);

        //中间画布操作
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        canvas.drawCircle(point.x, point.y, 10, paint);
        canvas.drawLine(point.x - 40, point.y, point.x + 40, point.y, paint);
        canvas.drawLine(point.x, point.y - 40, point.x, point.y + 40, paint);
        paint.setStrokeWidth(8);
    }
}
