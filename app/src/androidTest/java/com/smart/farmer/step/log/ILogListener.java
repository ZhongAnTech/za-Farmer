package com.smart.farmer.step.log;

import java.io.File;

public interface ILogListener {
    void logMessage(String type, String message);

    void logImage(String type, File imageFile);
}
