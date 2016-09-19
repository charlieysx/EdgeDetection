package com.elims.edgedetection.detection;

import android.graphics.Bitmap;

/**
 * 边缘检测方法
 * Created by smile on 2016/9/19.
 */
public abstract class Detection {

    /**
     * 进行边缘检测
     * @param originalBitmap 原始图片
     * @return
     */
    public abstract Bitmap detection(Bitmap originalBitmap);
}
