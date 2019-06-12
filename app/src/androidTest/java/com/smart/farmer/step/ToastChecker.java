package com.smart.farmer.step;

import android.os.SystemClock;

import com.smart.farmer.step.global.GlobalEventListener;
import com.smart.farmer.step.global.IGlobalEventChecker;

/**
 * Created by fuwenwen on 2019/1/8.
 */

public class ToastChecker implements IGlobalEventChecker {

    private String message = "";
    private boolean flag = false;

    @Override
    public void check(String toastMessage) {
        if (toastMessage.contains(message)) {
            flag = true;
        }
    }

    public void startCheck(String message) {
        this.message = message;
        GlobalEventListener.getInstance().registerToastChecker(this);
    }

    public boolean waitChecked() {
        return this.waitChecked(1000);
    }

    public boolean waitChecked(int waitTime) {

        long startTime = SystemClock.uptimeMillis();
        long elapsedTime = 0;
        while (!flag && elapsedTime <= waitTime) {
            elapsedTime = SystemClock.uptimeMillis() - startTime;
            SystemClock.sleep(500);
        }
        GlobalEventListener.getInstance().removeToastChecker(this);
        return flag;
    }
}
