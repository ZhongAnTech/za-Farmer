package com.smart.farmer.step;


import androidx.test.uiautomator.UiObjectNotFoundException;

/**
 * Created by fwwmac on 2018/2/26.
 */

public class ClickByPoint extends BaseStep {


    private int touchX;
    public int getTouchX() {
        return touchX;
    }

    public void setTouchX(int touchX) {
        this.touchX = touchX;
    }

    private int touchY;
    public int getTouchY() {
        return touchY;
    }

    public void setTouchY(int touchY) {
        this.touchY = touchY;
    }



    @Override
    protected void runSelf() throws UiObjectNotFoundException {
        screenshot(TYPE_LOG_SUCCESS,this.touchX, this.touchY);
        getUiDevice().click(getTouchX(), getTouchY());
    }


}
