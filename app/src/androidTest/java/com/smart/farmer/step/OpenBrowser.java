package com.smart.farmer.step;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;

import android.util.Log;

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
        getBrowserApp(mContext);
    }

    public ActivityInfo getBrowserApp(Context context) {

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
        ResolveInfo info = context.getPackageManager().resolveActivity(intent, PackageManager.GET_INTENT_FILTERS);
        String infoPackage = info.activityInfo.packageName;
        String infoactivityName = info.activityInfo.name;

        //命令行启动
        try {
            String test = getUiDevice().executeShellCommand("am start -a android.intent.action.VIEW -d " + getUrl() + " -n " + infoPackage + "/" + infoactivityName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return info.activityInfo;


//        // 找出手机当前安装的所有浏览器程序
//        List resolveInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS);
////        context.getPackageManager().getActivityInfo(intent,PackageManager.GET_INTENT_FILTERS);
//        if (resolveInfoList.size() > 0) {
//            //ActivityInfo activityInfo = (ActivityInfo)resolveInfoList.get(0).activityInfo;
//            Log.i("fuwenwen1010", "数量" + resolveInfoList.size());
//            ResolveInfo info = (ResolveInfo) resolveInfoList.get(0);
//            String packageName = info.activityInfo.packageName;
//            String className = info.activityInfo.name;
//            Log.i("fuwenwen1010", "数量" + resolveInfoList.size() + "包名：" + packageName + "类名：" + className + "info:" + info.activityInfo);
//
//            //命令行启动
//            try {
//                String test = getUiDevice().executeShellCommand("am start -a android.intent.action.VIEW -d https://i-test.zhongan.com/1rk1i5 -n " + packageName + "/" + className);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return info.activityInfo;
//
//        } else {
//            return null;
//        }

    }
}
