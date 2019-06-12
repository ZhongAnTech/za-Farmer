package com.smart.farmer.execute;

/**
 * author by fuwenwen on 2018.
 */

import com.smart.farmer.BuildConfig;
import com.smart.farmer.step.log.LogUtils;
import com.smart.farmer.step.IStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TestCase {

    private JSONArray jsonSteps;

    private JSONObject testJson;


    public TestCase(JSONObject testJson) {
        this.testJson = testJson;
    }

    public void runCase(String resultPath) throws JSONException {

        //设定结果路径
        LogUtils.getInstance().setResultPath(resultPath);

        this.jsonSteps = testJson.getJSONArray("steps");

        String versionName = BuildConfig.VERSION_NAME;

        testJson.put("farmerVersion", versionName);

        TestCaseLogListener testCaseLogListener = new TestCaseLogListener();
        LogUtils.getInstance().setLogListener(testCaseLogListener);

        for (int i = 0; i < jsonSteps.length(); i++) {

            JSONObject jStep = null;
            try {
                jStep = jsonSteps.getJSONObject(i);
            } catch (JSONException e) {
                //TODO 添加log输出
                e.printStackTrace();
                continue;
            }

            int errorHandle = 0;
            try {
                errorHandle = jStep.getInt("errorHandle");
            } catch (Exception e) {
            }


            //创建步骤
            IStep mStep = TestCaseFactory.convertStep(jStep);
            if (mStep == null) {
                //不明的步骤处理,不做处理
                continue;
            }

            StepResult stepResult = new StepResult();

            stepResult.setStartTime(System.currentTimeMillis());
            boolean isSuccess = mStep.runStep();
            stepResult.setEndTime(System.currentTimeMillis());

            if (isSuccess) {
                stepResult.setResult(StepResult.resultCode.Pass);
            } else if (errorHandle == 2) {
                stepResult.setResult(StepResult.resultCode.Warning);
            } else {
                stepResult.setResult(StepResult.resultCode.Fail);
            }

            List logs = testCaseLogListener.pullLogs();
            stepResult.setLogs(logs);
            List images = testCaseLogListener.pullImages();
            stepResult.setImages(images);

            //加入报告节点
            try {
                jStep.put("result", stepResult.createResult());
                createFile(resultPath);
            } catch (JSONException | IOException e) {
                //如果生成json 错误的话直接不用跑了
                e.printStackTrace();
                return;
            }

            if (stepResult.getResult() == StepResult.resultCode.Fail) {
                return;
            }
        }
    }

    private void createFile(String path) throws IOException {
        File file = new File(path, "result.json");
        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream out = new FileOutputStream(file);
        out.write(testJson.toString().getBytes());
        out.flush();
        out.close();
    }

}
