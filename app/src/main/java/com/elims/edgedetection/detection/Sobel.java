package com.elims.edgedetection.detection;

import android.graphics.Bitmap;
import android.graphics.Color;

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

    public Sobel() {
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
     * @return
     */
    public Bitmap getBitmap(double a, double b, double c){

        if(a == 0 && b == 0 && c == 0){
            return temp;
        }

        int w = temp.getWidth();
        int h = temp.getHeight();
        int[] cmap = new int[w * h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (tmap[j * w + i] > max * a) {
                    cmap[j * w + i] = mmap[j * w + i];
                } else if (tmap[j * w + i] > max * b) {
                    cmap[j * w + i] = mmap[j * w + i] - Color.GRAY;
                } else if (tmap[j * w + i] > max * c) {
                    cmap[j * w + i] = mmap[j * w + i] - Color.GRAY * 3 / 2;
                } else {
                    cmap[j * w + i] = Color.WHITE;
                }
            }
        }

        return Bitmap.createBitmap(cmap, temp.getWidth(), temp.getHeight(),
                Bitmap.Config.ARGB_8888);
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
