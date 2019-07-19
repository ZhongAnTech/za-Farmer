package com.smart.farmer;


import android.app.Instrumentation;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.smart.farmer.execute.TestCase;
import com.smart.farmer.execute.TestCaseFactory;
import com.smart.farmer.step.IStep;
import com.smart.farmer.step.global.GlobalEventListener;
import com.smart.farmer.step.log.ILogListener;
import com.smart.farmer.step.log.LogUtils;

import junit.framework.Assert;

import androidx.test.uiautomator.UiDevice;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Set;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    public UiDevice mUIDevice = null;
    public Context mContext = null;
    public Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

    @Before
    public void setUp() throws RemoteException {
        mUIDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());  //获得device对象
        mContext = InstrumentationRegistry.getContext();
        KeyguardManager km = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("farmer");
        //解锁
        kl.disableKeyguard();
        if (!mUIDevice.isScreenOn()) {
            mUIDevice.wakeUp();
        }

    }

    public ExampleInstrumentedTest() {

        super();
    }

    /**
     * 执行案例，入口
     */
    @Test
    public void execute() throws IOException, JSONException {


        Bundle args = InstrumentationRegistry.getArguments();

        final String caseFileKey = "executeFile";
        final String resultPathKey = "executeResult";
        final String autoPermitKey = "autoPermit";
        if (!args.containsKey(caseFileKey)) {
            args.putString(caseFileKey, InstrumentationRegistry.getTargetContext().getExternalCacheDir().getPath() + "/case.json");
        }
        if (!args.containsKey(resultPathKey)) {
            args.putString(resultPathKey, InstrumentationRegistry.getTargetContext().getExternalCacheDir().getPath());
        }
        if (!args.containsKey(autoPermitKey)) {
            args.putString(autoPermitKey, "true");

        }

        instrumentation.sendStatus(1, args);

        String executeFile = args.getString(caseFileKey);
        String executeResult = args.getString(resultPathKey);
        String autoPermit = args.getString(autoPermitKey);


        TestCase testCase = TestCaseFactory.getTestCase(new File(executeFile));


        //全局处理权限弹窗(开关控制)
        if (autoPermit.equals("true")) {

            GlobalEventListener.getInstance().usePermissionsWindowHandler(true);
        }

        testCase.runCase(executeResult);

    }


    @Test
    public void step() {


        Bundle args = InstrumentationRegistry.getArguments();
        instrumentation.sendStatus(1, args);

        LogUtils.getInstance().setLogListener(new ILogListener() {

            @Override
            public void logMessage(String type, String message) {
                Bundle result = new Bundle();
                result.putString("log-" + type, message);
                instrumentation.sendStatus(1, result);
            }

            @Override
            public void logImage(String type, File imageFile) {
                Bundle result = new Bundle();
                result.putString("screenshot-" + type, imageFile.getPath());
                instrumentation.sendStatus(1, result);
            }
        });

        IStep step = convertStep(args);

        Assert.assertTrue("执行失败", step.runStep());
    }

    private IStep convertStep(Bundle args) {

        Object object = null;
        String stepName = args.getString("step-action");
        try {
            //首字母大写
            stepName = stepName.substring(0, 1).toUpperCase() + stepName.substring(1);

            Class<?> stepClass = Class.forName("com.smart.farmer.step." + stepName);
            object = stepClass.newInstance();
        } catch (Exception e) {
            Assert.fail("找不到step : " + stepName);
        }


        Method[] methods = object.getClass().getMethods();

        for (Method method : methods) {
            String methodName = method.getName();
            //判断是否set方法
            if (methodName.indexOf("set") == 0) {
                String keyName = "step-" + methodName.substring(3);

                //获取入参，name不区分大小写
                String argsValue = getValue(args, keyName);
                //入参存在相应的参数
                if (argsValue != null) {
                    //获取set方法的入参，并转换参数
                    Type valueType = method.getGenericParameterTypes()[0];
                    Object value;

                    if (valueType == int.class) {
                        value = Integer.parseInt(argsValue);
                    } else if (valueType == boolean.class) {
                        value = Boolean.valueOf(argsValue);
                    } else {
                        value = argsValue;
                    }

                    Log.d("farmer", methodName + " " + value.getClass() + " " + value);
                    try {
                        method.invoke(object, value);
                    } catch (Exception e) {
                        Log.d("farmer", "属性注入失败：" + keyName);
                    }
                }
            }


            System.out.println("method name:" + method.getName());
        }


        return (IStep) object;
    }

    private String getValue(Bundle args, String name) {
        Set<String> keySet = args.keySet();
        for (String key : keySet) {
            if (key.equalsIgnoreCase(name)) {
                return args.getString(key);
            }
        }
        return null;
    }

}
