package com.smart.farmer.execute;

import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.text.TextUtils;

import java.io.File;

import static android.support.test.InstrumentationRegistry.getArguments;

/**
 * Created by fuwenwen on 2019/2/26.
 */

public class Config {

    private static final String FILE_BASE_PATH = InstrumentationRegistry.getTargetContext().getExternalCacheDir().getPath() + File.separator;
    private static final String PATH_RESULT = FILE_BASE_PATH + "dlc";
    private static final String PATH_CASE = FILE_BASE_PATH + "dlc" + File.separator + "case.json";
    private static final int DEFAULT_GRADE = 0;

    private static final String KEY_RESULT_PATH = "resultPath";
    private static final String KEY_CASE = "case";
    private static final String KEY_GRADE = "grade";



    //结果路径
    private String resultPath;
    //案例路径
    private String casePath;

    //截图的权重
    private int grade;


    private static Config sInstance;

    public static Config getInstance() {
        if (sInstance == null) {
            sInstance = new Config();
        }
        return sInstance;
    }

    public Config() {

        Bundle arguments = getArguments();

        //传递结果路径
        String resultPath = arguments.getString(KEY_RESULT_PATH);
        if (!TextUtils.isEmpty(resultPath)) {
            setResultPath(FILE_BASE_PATH + resultPath + File.separator);
        } else {
            setResultPath(PATH_RESULT);
        }

        //传递案例路径
        String casePath = arguments.getString(KEY_CASE);
        if (!TextUtils.isEmpty(casePath)) {
            setCasePath(casePath);
        } else {
            setCasePath(PATH_CASE);
        }

        //传递截图权重参数
        String grade = arguments.getString(KEY_GRADE);
        if (TextUtils.isEmpty(grade)) {
            setGrade(Integer.parseInt(grade));
        } else {
            setGrade(DEFAULT_GRADE);
        }

    }


    public String getResultPath() {
        return resultPath;
    }

    private void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public String getCasePath() {
        return casePath;
    }

    private void setCasePath(String casePath) {
        this.casePath = casePath;
    }

    public int getGrade() {
        return grade;
    }

    private void setGrade(int grade) {
        this.grade = grade;
    }


}
