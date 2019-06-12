package com.smart.farmer.execute;

import com.smart.farmer.step.log.ILogListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TestCaseLogListener implements ILogListener {

    private List<LogImageEntity> imageList = Collections.synchronizedList(new ArrayList());
    private List<LogTextEntity> textList = Collections.synchronizedList(new ArrayList());

    @Override
    public void logMessage(String type, String message) {
        LogTextEntity textEntity = new LogTextEntity();
        textEntity.setType(type);
        textEntity.setTime(System.currentTimeMillis());
        textEntity.setContent(message);
        textList.add(textEntity);
    }

    @Override
    public void logImage(String type, File imageFile) {
        LogImageEntity imageEntity = new LogImageEntity();
        imageEntity.setType(type);
        imageEntity.setTime(System.currentTimeMillis());
        imageEntity.setImage(imageFile);
        imageList.add(imageEntity);
    }


    /**
     * 抽取并删除目前存在的全部的日志
     *
     * @return
     */
    public List pullLogs() {
        List<LogTextEntity> rtl = new ArrayList();
        synchronized (textList) {
            Iterator<LogTextEntity> it = textList.iterator();
            while (it.hasNext()) {
                LogTextEntity item = it.next();
                it.remove();
                rtl.add(item);
            }
        }
        return rtl;
    }

    /**
     * 抽取并删除目前存在的全部的截图信息
     *
     * @return
     */
    public List pullImages() {
        List<LogImageEntity> rtl = new ArrayList();
        synchronized (imageList) {
            Iterator<LogImageEntity> it = imageList.iterator();
            while (it.hasNext()) {
                LogImageEntity item = it.next();
                it.remove();
                rtl.add(item);
            }
        }
        return rtl;
    }
}
