package com.smart.farmer.step;


import com.smart.farmer.step.log.LogUtils;

import androidx.test.uiautomator.Until;

/**
 * Created by fwwmac on 2018/2/26.
 */

public class WaitUntilGone extends BaseElementHandler {
    @Override
    protected void runSelf() {
        if ((getUiDevice().wait(Until.gone(getElementSelector()), 5000))) {
            screenshot(TYPE_LOG_SUCCESS);
        } else {
            LogUtils.getInstance().error("WaitUntilGone Faild");
        }
    }


}
