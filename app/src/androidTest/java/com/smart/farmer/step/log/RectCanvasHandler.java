package com.smart.farmer.step.log;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class RectCanvasHandler implements ICanvasHandler {

    private Rect rect;

    public RectCanvasHandler(Rect rect) {
        this.rect = rect;
    }

    @Override
    public void handle(Canvas canvas) {
        //定制画笔
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);

        canvas.drawRect(rect, paint);
    }
}
