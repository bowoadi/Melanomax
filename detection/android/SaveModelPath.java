package org.tensorflow.demo;

import android.util.Base64;

import org.tensorflow.demo.util.SharedPrefUtil;

public class SaveModelPath {
    private static SaveModelPath ourInstance;

    private SaveModelPath() {
    }

    public static SaveModelPath getInstance() {
        if (ourInstance == null) ourInstance = new SaveModelPath();
        return ourInstance;
    }

    public String getModelPath() {
        return SharedPrefUtil.getString("model");
    }

    public void saveModelPath(String path) {
       SharedPrefUtil.saveString("model", path);
    }

    public String getLabelPath() {
        return SharedPrefUtil.getString("label");
    }

    public void saveLabelPath(String path) {
        SharedPrefUtil.saveString("label", path);
    }
}
