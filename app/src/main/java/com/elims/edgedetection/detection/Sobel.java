package com.elims.edgedetection.detection;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.elims.edgedetection.IMain;
import com.elims.edgedetection.utils.BitmapUtil;

/**
 * Sobel边缘检测算法
 * Created by smile on 2016/9/19.
 */
public class Sobel extends Detection {

    private Bitmap originalBitmap;
    private Bitmap temp;
    private double max;
    private int[] mmap;
    private double[] tmap;

    public Sobel(IMain iMain) {
        super(iMain);
        this.originalBitmap = null;
    }

    @Override
    public void detection(Bitmap originalBitmap) {

        this.originalBitmap = originalBitmap;

        this.temp = BitmapUtil.toGrayscale(this.originalBitmap);
        int w = temp.getWidth();
        int h = temp.getHeight();

        mmap = new int[w * h];
        tmap = new double[w * h];

        temp.getPixels(mmap, 0, temp.getWidth(), 0, 0, temp.getWidth(),
                temp.getHeight());

        max = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                double gx = GX(i, j, temp);
                double gy = GY(i, j, temp);
                tmap[j * w + i] = Math.sqrt(gx * gx + gy * gy);
                if (max < tmap[j * w + i]) {
                    max = tmap[j * w + i];
                }
            }
        }

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

        int w = temp.getWidth();
        int h = temp.getHeight();
        int[] cmap = new int[w * h];


        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (tmap[j * w + i] > max * a) {
                    cmap[j * w + i] = mmap[j * w + i];
                } else if (tmap[j * w + i] > max * b) {
                    cmap[j * w + i] = getColor(mmap[j * w + i], 50);
                } else if (tmap[j * w + i] > max * c) {
                    cmap[j * w + i] = getColor(mmap[j * w + i], 80);
                } else {
                    cmap[j * w + i] = Color.WHITE;
                }
            }
        }

        Bitmap bm =  Bitmap.createBitmap(cmap, temp.getWidth(), temp.getHeight(),
                Bitmap.Config.ARGB_8888);

        iMain.setBitmap(bm);
    }

    private int getColor(int color, int value) {

        int cr, cg, cb;

        cr = (color & 0x00ff0000) >> 16;
        cg = (color & 0x0000ff00) >> 8;
        cb = color & 0x000000ff;

        cr += value;
        cg += value;
        cb += value;

        if(cr > 255){
            cr = 255;
        }
        if(cg > 255){
            cg = 255;
        }
        if(cb > 255){
            cb = 255;
        }

        return Color.argb(255, cr, cg, cb);
    }

    /**
     * 获取横向的
     *
     * @param x      第x行
     * @param y      第y列
     * @param bitmap
     * @return
     */
    private static double GX(int x, int y, Bitmap bitmap) {
        double res = (-1) * getPixel(x - 1, y - 1, bitmap)
                + 1 * getPixel(x + 1, y - 1, bitmap)
                + (-Math.sqrt(2)) * getPixel(x - 1, y, bitmap)
                + Math.sqrt(2) * getPixel(x + 1, y, bitmap)
                + (-1) * getPixel(x - 1, y + 1, bitmap)
                + 1 * getPixel(x + 1, y + 1, bitmap);

        return res;
    }

    /**
     * 获取纵向的
     *
     * @param x      第x行
     * @param y      第y列
     * @param bitmap
     * @return
     */
    private static double GY(int x, int y, Bitmap bitmap) {
        double res = 1 * getPixel(x - 1, y - 1, bitmap)
                + Math.sqrt(2) * getPixel(x, y - 1, bitmap)
                + 1 * getPixel(x + 1, y - 1, bitmap)
                + (-1) * getPixel(x - 1, y + 1, bitmap)
                + (-Math.sqrt(2)) * getPixel(x, y + 1, bitmap)
                + (-1) * getPixel(x + 1, y + 1, bitmap);

        return res;
    }

    /**
     * 获取第x行第y列的色度
     *
     * @param x      第x行
     * @param y      第y列
     * @param bitmap
     * @return
     */
    private static double getPixel(int x, int y, Bitmap bitmap) {
        if (x < 0 || x >= bitmap.getWidth() || y < 0 || y >= bitmap.getHeight()) {
            return 0;
        }
        return bitmap.getPixel(x, y);
    }

}
