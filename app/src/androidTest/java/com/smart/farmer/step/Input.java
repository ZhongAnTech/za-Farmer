package com.smart.farmer.step;


import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;

/**
 * Created by fwwmac on 2018/2/5.
 */

public class Input extends BaseElementHandler {

    private String reText;
    public String getReText() {
        return reText;
    }

    public void setReText(String reText) {
        this.reText = reText;
    }


    @Override
    protected void runSelf() throws UiObjectNotFoundException {
        UiObject2 uiObject = getElement();
        screenshot(TYPE_LOG_SUCCESS,uiObject);
        uiObject.setText(getReText());

    }


}
