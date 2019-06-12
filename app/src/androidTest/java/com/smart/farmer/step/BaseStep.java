package com.smart.farmer.step;


import android.graphics.Point;
import android.support.test.InstrumentationRegistry;
import android.text.TextUtils;
import android.util.Log;

import com.smart.farmer.step.log.ICanvasHandler;
import com.smart.farmer.step.log.LogUtils;
import com.smart.farmer.step.log.LineCanvasHandler;
import com.smart.farmer.step.log.PointCanvasHandler;
import com.smart.farmer.step.log.RectCanvasHandler;

import java.util.regex.Pattern;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiWatcher;

/**
 * Created by fwwmac on 2018/2/2.
 */

public abstract class BaseStep implements IStep {

    public static final String TYPE_LOG_INFO = "INFO";
    public static final String TYPE_LOG_SUCCESS = "SUCCESS";
    public static final String TYPE_LOG_ERROR = "ERROR";

    BaseStep() {
    }

    private int waitTime = 1000;
    private String toastMessage;
    private boolean autoPermit;

    public int getWaitTime() {
        return this.waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }


    public String getToastMessage() {
        return this.toastMessage;
    }

    public void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
    }


    public static final UiDevice getUiDevice() {
        return UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }


    public boolean isAutoPermit() {
        return autoPermit;
    }

    public void setAutoPermit(boolean autoPermit) {
        this.autoPermit = autoPermit;
    }

    public boolean runStep() {

        boolean isSuccess = true;

        if (autoPermit) {
            //使用Watcher处理权限弹窗
            //为了确保不会错误触发，用过text、clazz、package过滤。是否有更好的实现方式？
            getUiDevice().registerWatcher("autoPermit", new UiWatcher() {
                @Override
                public boolean checkForCondition() {
                    String buttonName = "android.widget.Button";
                    String important = "允许|同意|始终允许|确认|总是允许";
                    String packages = "com\\.lbe\\.security\\.miui|com\\.huawei\\.systemmanager|com\\.smartisanos\\.systemui|android|^.*coloros.*$|^.*android.*$|com\\.miui\\.home|^.*xiaomi.*$|android";

                    UiObject2 obj = UiDevice.getInstance().findObject(By.pkg(Pattern.compile(packages)).text(Pattern.compile(important)).clazz(buttonName));

                    if (obj != null) {
                        RectCanvasHandler rectCanvasHandler = new RectCanvasHandler(obj.getVisibleBounds());
                        LogUtils.getInstance().infoScreenshot(rectCanvasHandler);
                        try {
                            obj.click();
                        } catch (Exception e) {
                            LogUtils.getInstance().error(e);
                        }
                        return true;
                    }
                    return false;
                }
            });
        }


        //toast检测
        ToastChecker toastChecker = null;
        if (this.getToastMessage() != null) {
            toastChecker = new ToastChecker();
            toastChecker.startCheck(this.getToastMessage());
        }

        try {
            this.runSelf();
            if (toastChecker != null) {
                if (!toastChecker.waitChecked()) {
                    isSuccess = false;
                    LogUtils.getInstance().error("捕获预期Toast失败");
                }
            }
        } catch (Exception e) {
            LogUtils.getInstance().error(e);
            LogUtils.getInstance().errorScreenshot(null);
            isSuccess = false;
        }


        try {
            Thread.sleep(this.getWaitTime());
        } catch (InterruptedException e) {
            LogUtils.getInstance().error(e);
        }

        getUiDevice().removeWatcher("autoPermit");
        return isSuccess;
    }


    protected abstract void runSelf() throws Exception;

    public void screenshot(String type) {
        screenShotLog(type, null);
    }


    public void screenshot(String type, UiObject2 obj) {
        ICanvasHandler handler = new RectCanvasHandler(obj.getVisibleBounds());
        screenShotLog(type, handler);
    }

    public void screenshot(String type, int sourceX, int sourceY, int targetX, int targetY) {
        Point startPoint = new Point(sourceX, sourceY);
        Point endPoint = new Point(targetX, targetY);
        ICanvasHandler handler = new LineCanvasHandler(startPoint, endPoint);
        screenShotLog(type, handler);
    }

    public void screenshot(String type, int sourceX, int sourceY) {
        Point point = new Point(sourceX, sourceY);
        ICanvasHandler handler = new PointCanvasHandler(point);
        screenShotLog(type, handler);
    }

    private void screenShotLog(String type, ICanvasHandler handler) {
        if (TextUtils.isEmpty(type))
            type = TYPE_LOG_INFO;

        switch (type) {
            case TYPE_LOG_INFO:
                LogUtils.getInstance().infoScreenshot(handler);
                break;
            case TYPE_LOG_SUCCESS:
                LogUtils.getInstance().successScreenshot(handler);
                break;
            case TYPE_LOG_ERROR:
                LogUtils.getInstance().errorScreenshot(handler);
                break;
        }
    }

}
