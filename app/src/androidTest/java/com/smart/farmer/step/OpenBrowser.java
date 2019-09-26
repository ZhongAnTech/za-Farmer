package com.smart.farmer.step;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;

import com.smart.farmer.step.log.LogUtils;

import java.io.IOException;

public class OpenBrowser extends BaseStep {

    protected String url = "about:blank";//启动的浏览器地址

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected void runSelf() throws Exception {

        //启动移动端HTML5浏览器
        Context mContext = InstrumentationRegistry.getInstrumentation().getContext();
        //筛选条件

        //默认浏览器，BROWSABLE 种类
        String default_browser = "android.intent.category.DEFAULT";
        String browsable = "android.intent.category.BROWSABLE";
        String view = "android.intent.action.VIEW";
        Intent intent = new Intent(view);

        //添加类别
        intent.addCategory(default_browser);
        intent.addCategory(browsable);

        //添加Data及Type
        Uri uri = Uri.parse("http://");
        intent.setDataAndType(uri, null);
        ResolveInfo info = mContext.getPackageManager().resolveActivity(intent, PackageManager.GET_INTENT_FILTERS);
        String infoPackage = info.activityInfo.packageName;
        String infoactivityName = info.activityInfo.name;

        //命令行启动
        try {
            String test= getUiDevice().executeShellCommand("am start -a android.intent.action.VIEW -d " + getUrl() + " -n " + infoPackage + "/" + infoactivityName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

