package com.smart.farmer.step;


import androidx.test.uiautomator.UiObjectNotFoundException;

/**
 * Created by fwwmac on 2018/2/7.
 */

//断言存在（相当于检查点）

public class AssertExist extends BaseElementHandler {

    @Override
    protected void runSelf() throws UiObjectNotFoundException {
        getElement();
        screenshot(TYPE_LOG_SUCCESS);
    }


}
