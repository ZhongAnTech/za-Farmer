package com.smart.farmer.step.log;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.test.uiautomator.UiDevice;

public class LogUtils {

    private final String Tag = "Step";
    private static LogUtils instance;

    public static final String TYPE_SUCCESS = "SUCCESS";
    public static final String TYPE_INFO = "INFO";
    public static final String TYPE_WARN = "WARN";
    public static final String TYPE_ERROR = "ERROR";

    private String resultPath = InstrumentationRegistry.getTargetContext().getExternalCacheDir().getPath();

    public void setResultPath(String value) {
        resultPath = value;
    }


    private ILogListener logListener = new ILogListener() {
        @Override
        public void logMessage(String type, String message) {

        }

        @Override
        public void logImage(String type, File imageFile) {

        }
    };

    public static LogUtils getInstance() {
        if (instance == null) {
            instance = new LogUtils();
        }
        return instance;
    }

    public void info(String infoMsg) {
        Log.i(Tag, infoMsg);
        logListener.logMessage(TYPE_INFO, infoMsg);
    }

    public void error(String erroMsg) {
        this.error(erroMsg, null);
    }

    public void error(Throwable e) {
        this.error(e.getMessage(), e);
    }

    public void error(String errorMsg, Throwable e) {
        Log.e(Tag, errorMsg, e);
        logListener.logMessage(TYPE_ERROR, errorMsg);
    }

    public void warn(String warnMsg) {
        Log.w(Tag, warnMsg);
        logListener.logMessage(TYPE_WARN, warnMsg);
    }

    public void infoScreenshot(ICanvasHandler ICanvasHandler) {
        File imageFile = this.commonScreenshot(ICanvasHandler);
        logListener.logImage(TYPE_INFO, imageFile);
    }

    public void successScreenshot(ICanvasHandler ICanvasHandler) {
        File imageFile = this.commonScreenshot(ICanvasHandler);
        logListener.logImage(TYPE_SUCCESS, imageFile);
    }

    public void errorScreenshot(ICanvasHandler ICanvasHandler) {
        File imageFile = this.commonScreenshot(ICanvasHandler);
        logListener.logImage(TYPE_ERROR, imageFile);
    }


    public void setLogListener(ILogListener logListener) {
        this.logListener = logListener;
    }

    private File commonScreenshot(ICanvasHandler iCanvasHandler) {

        String imageName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        File storeFile = new File(this.resultPath, imageName);
        Bitmap tmpbitmap = UiDevice.getInstance().takeScreenshot();
        if (tmpbitmap == null) {
            this.error(" 截图失败", null);
            return null;
        }
        Bitmap bitmap = tmpbitmap.copy(tmpbitmap.getConfig(), true);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap, 0, 0, null);

        if (iCanvasHandler != null) {
            iCanvasHandler.handle(canvas);
        }

        canvas.setBitmap(null);
        tmpbitmap.recycle();
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(storeFile));
            if (bos != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bos);
                bos.flush();
            }
        } catch (IOException ioe) {
            this.error(ioe.getMessage(), ioe);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException ioe) {
                    this.error(ioe.getMessage(), ioe);
                }
            }
        }
        bitmap.recycle();
        return storeFile;

    }


}
