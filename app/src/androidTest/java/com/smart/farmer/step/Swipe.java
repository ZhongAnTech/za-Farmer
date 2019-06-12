package com.smart.farmer.step;


import androidx.test.uiautomator.UiObjectNotFoundException;

/**
 * Created by fwwmac on 2018/2/6.
 */

public class Swipe extends BaseStep {


    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int steps;
    private String direction;// 滑动方向


    @Override
    protected void runSelf() throws UiObjectNotFoundException {

        int startX = this.startX;
        int startY = this.startY;
        int endX = this.endX;
        int endY = this.endY;

        if (this.getDirection() != null && !this.getDirection().isEmpty()) {
            //方向
            int xLength = getUiDevice().getDisplayWidth();// 显示宽度
            int yLength = getUiDevice().getDisplayHeight();// 显示高度

            //移动距离
            int xSwipeRange = xLength * 3 / 4;
            int ySwipeRange = yLength / 2;

            String pp = this.getDirection();
            if (this.getDirection().equals("up")) {
                //上
                startX = xLength / 2;
                startY = yLength - (yLength - ySwipeRange) / 2;
                endX = xLength / 2;
                endY = (yLength - ySwipeRange) / 2;
            } else if (this.getDirection().equals("down")) {
                //下
                startX = xLength / 2;
                startY = (yLength - ySwipeRange) / 2;
                endX = xLength / 2;
                endY = yLength - (yLength - ySwipeRange) / 2;
            } else if (this.getDirection().equals("left")) {
                //左
                startX = xLength - (xLength - xSwipeRange) / 2;
                startY = yLength / 2;
                endX = (xLength - xSwipeRange) / 2;
                endY = yLength / 2;
            } else if (this.getDirection().equals("right")) {
                //右
                startX = (xLength - xSwipeRange) / 2;
                startY = yLength / 2;
                endX = xLength - (xLength - xSwipeRange) / 2;
                endY = yLength / 2;
            }
        }

        screenshot(TYPE_LOG_SUCCESS, startX, startY, endX, endY);
        getUiDevice().swipe(startX, startY, endX, endY, this.getSteps());

    }


    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int getSteps() {
        return steps > 55 ? steps : 55;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
