package com.smart.farmer.step;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build.VERSION;
import android.support.test.InstrumentationRegistry;

import com.smart.farmer.step.log.LogUtils;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;

/**
 * Created by fwwmac on 2018/2/28.
 */

public class OpenApplication extends BaseElementHandler {


    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }


    private boolean clear;

    public boolean getClear() {
        return clear;
    }

    public void setClear(boolean clear) {
        this.clear = clear;
    }

    @Override
    protected void runSelf() throws Exception {

        //Part One 输入app版本log

        PackageInfo info = InstrumentationRegistry.getContext().getPackageManager().getPackageInfo(this.getPackageName(), 0);
        String appversion = info.versionName;
        LogUtils.getInstance().info("appversion" + appversion);


        int sdk = VERSION.SDK_INT;

        if (this.clear) {
            //清掉缓存

            if (sdk < 21) {
                //清缓存待定
                LogUtils.getInstance().info("sdk版本小于api21，清缓后续处理");
            } else {

                String clearoutput = getUiDevice().executeShellCommand("pm clear " + this.getPackageName());
                LogUtils.getInstance().info("pm clear " + clearoutput);
            }
        }


        Context mContext = InstrumentationRegistry.getContext();
        Intent myIntent = mContext.getPackageManager().getLaunchIntentForPackage(this.getPackageName());
        String AppStartActivity = myIntent.getComponent().getClassName();

        if (sdk < 21) {
            //安卓版本5.0以下的启动方式
            mContext.startActivity(myIntent);

        } else {
            //安卓版本5.0以上的启动方式
            String startNoClearoutput = getUiDevice().executeShellCommand("am start -n " + this.getPackageName() + "/" + AppStartActivity);
            LogUtils.getInstance().info(startNoClearoutput);
        }

        BySelector bySelector = By.base();
        bySelector.pkg(this.getPackageName());
        getElement(bySelector, 10000);


        screenshot(TYPE_LOG_SUCCESS);

    }
}
