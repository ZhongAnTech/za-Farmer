package com.smart.farmer.execute;

import java.io.File;
import java.io.Serializable;

public class LogImageEntity implements Serializable {

    private String type;
    private long time;
    private File image;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
