package com.jacksonyang.wolfkiller.BaseTools;

/**
 * Created by 35390 on 2017/8/9.
 */

public interface LoadingListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
}
