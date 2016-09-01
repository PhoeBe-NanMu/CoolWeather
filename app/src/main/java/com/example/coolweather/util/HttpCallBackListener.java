package com.example.coolweather.util;

/**
 * Created by LeiYang on 2016/9/1 0001.
 */

public interface HttpCallBackListener {
    void onFinish(String string);
    void onError(Exception e);
}
