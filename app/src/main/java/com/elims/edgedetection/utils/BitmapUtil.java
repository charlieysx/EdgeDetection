package com.elims.edgedetection.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Bitmap处理的工具
 * Created by smile on 2016/9/19.
 */
public class BitmapUtil {

    private final static String TAG = "BitmapUtil";

    /**
     * 获取bitmap图片，动态调整图片大小，防止OOM
     *
     * @param context
     * @param imgId
     * @param maxWidth 图片的最大宽度
     * @return
     */
    public static Bitmap getThumbnailBitmap(Context context, int imgId, int maxWidth) {

        Bitmap resultBitmap;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), imgId, opts);
        opts.inSampleSize = computeSampleSize(opts, -1, maxWidth * maxWidth);
//        Log.i(TAG, "opts.inSampleSize = " + opts.inSampleSize);
        opts.inJustDecodeBounds = false;

        try {
            resultBitmap = BitmapFactory.decodeResource(context.getResources(), imgId, opts);
        } catch (Exception e) {
            resultBitmap = null;
            e.printStackTrace();
        }

        return resultBitmap;
    }

    /**
     * 动态计算出图片的inSampleSize
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    private static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double width = options.outWidth;
        double height = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(width * height / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? (int) Math.sqrt(maxNumOfPixels) : (int) Math.min(Math.floor(width / minSideLength), Math.floor
                (height / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

}
