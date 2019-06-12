package com.smart.farmer.execute;


import com.smart.farmer.step.IStep;
import com.smart.farmer.step.log.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * Created by sunshine on 2018/8/8.
 */

public class TestCaseFactory {

    private static final String STEP_PACKAGE_NAME = "com.smart.farmer.step.";


    public static TestCase getTestCase(File mFile) throws IOException, JSONException {

        //此目录为手机目录
        BufferedReader br = new BufferedReader(new FileReader(mFile));

        String line;
        StringBuilder builder = new StringBuilder();

        while ((line = br.readLine()) != null) {
            builder.append(line);
        }
        br.close();
        return getTestCase(builder.toString());

    }

    public static TestCase getTestCase(String caseJson) throws JSONException {

        JSONObject testjson = new JSONObject(caseJson);

        return new TestCase(testjson);


    }

    public static IStep convertStep(JSONObject role) {

        Object object;
        try {
            Class<?> stepClass = Class.forName(STEP_PACKAGE_NAME + role.optString("action"));
            object = stepClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        for (Iterator iter = role.keys(); iter.hasNext(); ) {
            String key = (String) iter.next();
            if (key.equals("action"))
                continue;

            Object value = null;
            try {
                value = role.get(key);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

            reflectObject(object, key, value);
        }

        return (IStep) object;
    }


    private static void reflectObject(Object obj, String key, Object value) {

        // 避免关键字首字母没有大写
        key = key.substring(0, 1).toUpperCase() + key.substring(1);

        Class objClass = obj.getClass();
        Class valueClass = value instanceof Integer ? int.class : value.getClass();

        try {
            Method serMethod = objClass.getMethod("set" + key, valueClass);
            serMethod.invoke(obj, value);
        } catch (NoSuchMethodException | IllegalAccessException |InvocationTargetException e) {
            System.out.println("属性注入失败："+key);
        }

    }

}
