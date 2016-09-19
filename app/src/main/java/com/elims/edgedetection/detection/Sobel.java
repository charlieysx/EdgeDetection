package com.elims.edgedetection.detection;

import android.graphics.Bitmap;

/**
 *
 * Sobel边缘检测算法
 * Created by smile on 2016/9/19.
 */
public class Sobel extends Detection {

    private Bitmap originalBitmap;

    public Sobel(){
        this.originalBitmap = null;
    }

    @Override
    public Bitmap detection(Bitmap originalBitmap) {

        this.originalBitmap = originalBitmap;


        return originalBitmap;
    }
}
