package com.smart.farmer.step;

import androidx.test.uiautomator.UiObjectNotFoundException;

/**
 * Created by fuwenwen on 2018/11/15.
 */

public class PressKey extends BaseStep {

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    int keyCode;


    @Override
    protected void runSelf() {

        if (keyCode <= 0) {

            throw new Error("keycode输入无效");
        }
        screenshot(TYPE_LOG_SUCCESS);
        getUiDevice().getInstance().pressKeyCode(keyCode);

    }
}
