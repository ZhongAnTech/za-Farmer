package com.smart.farmer.step.global;

import android.app.Instrumentation;
import android.app.Notification;
import android.app.UiAutomation;
import android.os.Parcelable;
import android.support.test.InstrumentationRegistry;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;

import com.smart.farmer.step.log.LogUtils;
import com.smart.farmer.step.log.RectCanvasHandler;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;

/**
 * Created by fuwenwen on 2019/1/4.
 */


public class GlobalEventListener {

    private static GlobalEventListener sInstance;

    private HashSet<IGlobalEventChecker> toastCheckerSet;

    final private String packages = "com\\.lbe\\.security\\.miui|com\\.huawei\\.systemmanager|com\\.smartisanos\\.systemui|android|^.*coloros.*$|^.*android.*$|com\\.miui\\.home|^.*xiaomi.*$|android|^.*meitu.*$";
    final private String allowButton = "允许|同意|始终允许|确认|总是允许|确定";


    private boolean permissionsWindowHandler = false;
    final private UiDevice uiDevice = UiDevice.getInstance();

    public GlobalEventListener() {
        toastCheckerSet = new HashSet<>();
        this.initListener();
    }

    public static GlobalEventListener getInstance() {
        if (sInstance == null) {
            sInstance = new GlobalEventListener();
        }
        return sInstance;
    }


    private void initListener() {

        Instrumentation mInstrumentation = InstrumentationRegistry.getInstrumentation();
        mInstrumentation.getUiAutomation().setOnAccessibilityEventListener(
                new UiAutomation.OnAccessibilityEventListener() {
                    @Override
                    public void onAccessibilityEvent(AccessibilityEvent event) {
                        try {
                            final int eventType = event.getEventType();
                            //处理权限框
                            if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && permissionsWindowHandler) {


                                //package 符合
                                final String packageName = event.getPackageName().toString();
                                if (!Pattern.matches(packages, packageName)) {
                                    return;
                                }


                                String btnText = null;
                                final List<CharSequence> texts = event.getText();
                                for (CharSequence text : texts) {
                                    if (Pattern.matches(allowButton, text)) {
                                        btnText = text.toString();
                                        break;
                                    }
                                }

                                //btnText 符合
                                if (btnText == null) {
                                    return;
                                }

                                //文本日志
                                LogUtils.getInstance().info("permissions window: package " + packageName
                                        + ",text " + texts);


                                BySelector permissionsSelector = By.pkg(packageName).text(btnText);
                                UiObject2 obj = uiDevice.findObjectOnce(permissionsSelector);

                                //截图日志
                                LogUtils.getInstance().infoScreenshot(new RectCanvasHandler(obj.getVisibleBounds()));

                                obj.click();


                            } else if (eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
                                //判断是否是通知事件
                                Parcelable parcelable = event.getParcelableData();
                                //如果不是下拉通知栏消息，则为其它通知信息，包括Toast
                                if (!(parcelable instanceof Notification)) {
                                    List<CharSequence> messageList = event.getText();
                                    for (CharSequence toast_Message : messageList) {
                                        if (!TextUtils.isEmpty(toast_Message)) {
                                            for (IGlobalEventChecker toastChecker : toastCheckerSet) {
                                                toastChecker.check(toast_Message.toString());
                                            }
                                            return;
                                        }
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            LogUtils.getInstance().error(ex);
                        }
                    }
                }
        );
    }


    public void registerToastChecker(IGlobalEventChecker toastChecker) {
        toastCheckerSet.add(toastChecker);
    }


    public void removeToastChecker(IGlobalEventChecker toastChecker) {
        if (!toastCheckerSet.isEmpty()) {
            toastCheckerSet.remove(toastChecker);
        }
    }

    public void usePermissionsWindowHandler(boolean used) {
        permissionsWindowHandler = used;
    }

}




