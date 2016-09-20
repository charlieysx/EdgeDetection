package com.elims.edgedetection.detection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;

import com.elims.edgedetection.IMain;
import com.elims.edgedetection.ScriptC_hello;
import com.elims.edgedetection.utils.BitmapUtil;

/**
 * Sobel边缘检测算法
 * Created by smile on 2016/9/19.
 */
public class SobelByRs extends Detection {

    private Bitmap originalBitmap;
    private Bitmap temp;
    private Bitmap resultBitmap;

    private Context mContext;

    private RenderScript rs;
    private Allocation aInit;
    private Allocation aGray;
    private Allocation aOutInit;
    private Allocation aOutGray;
    private ScriptC_detectionBitmap mScript;

    public SobelByRs(IMain iMain, Context ctx) {
        super(iMain);
        this.mContext = ctx;
        this.originalBitmap = null;
        this.temp = null;
        this.resultBitmap = null;
        rs = RenderScript.create(mContext);
        mScript = new ScriptC_detectionBitmap(rs);
    }

    @Override
    public void detection(Bitmap originalBitmap) {

        this.originalBitmap = originalBitmap;
        this.resultBitmap = originalBitmap;

        aInit = Allocation.createFromBitmap(rs, this.originalBitmap);
        aOutInit = Allocation.createTyped(rs, aInit.getType());
        mScript.set_initBitmap(aInit);


        this.temp = BitmapUtil.toGrayscale(this.originalBitmap);
        aGray = Allocation.createFromBitmap(rs, this.temp);
        aOutGray = Allocation.createTyped(rs, aGray.getType());
        mScript.set_grayBitmap(aGray);

        int w = temp.getWidth();
        int h = temp.getHeight();
        mScript.set_width(w);
        mScript.set_height(h);


    }

    /**
     *
     * 根据设定的阙值获取处理后的图片
     *
     * @param a
     * @param b
     * @param c
     *
     */
    public void getBitmap(double a, double b, double c){

        if(a == 0 && b == 0 && c == 0){
            iMain.setBitmap(temp);
            return ;
        }

        mScript.forEach_calculateSobel(aInit, aOutInit);
        aOutInit.copyTo(resultBitmap);

        iMain.setBitmap(resultBitmap);
    }
}
