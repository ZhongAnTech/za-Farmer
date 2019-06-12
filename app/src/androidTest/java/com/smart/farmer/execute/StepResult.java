package com.smart.farmer.execute;

import com.smart.farmer.step.log.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sunshine on 2018/8/8.
 */

public class StepResult {


    private resultCode result;

    public resultCode getResult() {
        return result;
    }

    public void setResult(resultCode result) {
        this.result = result;
    }

    private List<LogImageEntity> images = new ArrayList<LogImageEntity>();

    public List<LogImageEntity> getImages() {
        return images;
    }

    public void setImages(List images) {
        this.images = images;
    }

    private Long startTime;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    private Long endTime;

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    private List<LogTextEntity> logs = new ArrayList<>();

    public void setLogs(List logs) {
        this.logs = logs;
    }

    public List<LogTextEntity> getLogs() {
        return logs;
    }


    public JSONObject createResult() throws JSONException {

        StringBuffer logSB = new StringBuffer();
        for (LogTextEntity entity : this.getLogs()) {
            logSB.append(this.timeStampToString(entity.getTime()));
            logSB.append(" ");
            logSB.append(entity.getType());
            logSB.append(":");
            logSB.append(entity.getContent());
            logSB.append("\r\n");
        }

        String image = null;
        List<String> imageList = new ArrayList<>();
        for (LogImageEntity entity : this.images) {
            String imageName = entity.getImage().getName();
            if (image == null) {
                image = imageName;
            } else if (entity.getType() == LogUtils.TYPE_SUCCESS || entity.getType() == LogUtils.TYPE_ERROR) {
                image = imageName;
            }
            imageList.add(imageName);
        }

        JSONObject reStepJson = new JSONObject();
        reStepJson.put("result", this.getResult().getStatusCode());
        reStepJson.put("image", image);
        reStepJson.put("startTime", this.getStartTime());
        reStepJson.put("endTime", this.getEndTime());
        //打印步骤的输出log
        reStepJson.put("log", logSB.toString());
        reStepJson.put("images", new JSONArray(imageList));
        return reStepJson;
    }

    private String timeStampToString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }

    //枚举结果
    public enum resultCode {
        NoRun(0, "未执行"),
        Pass(1, "执行成功"),
        Fail(2, "执行失败"),
        Warning(3, "判断是否继续执行");


        /**
         * 返回状态码
         */
        private int statusCode;
        /**
         * 返回状态信息
         */
        private String statusMsg;


        resultCode(int statusCode, String statusMsg) {
            this.statusCode = statusCode;
            this.statusMsg = statusMsg;

        }


        /**
         * @return the statusCode
         */
        public int getStatusCode() {
            return statusCode;
        }

        public String getStatusMsg() {
            return statusMsg;
        }


    }
}




