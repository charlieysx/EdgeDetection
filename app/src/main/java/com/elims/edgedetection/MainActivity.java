package com.elims.edgedetection;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.elims.edgedetection.detection.Detection;
import com.elims.edgedetection.detection.Sobel;
import com.elims.edgedetection.utils.BitmapUtil;
import com.elims.edgedetection.utils.CommonUtil;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_result;
    private Detection detection;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        CommonUtil.screenWidth = dm.widthPixels;
        CommonUtil.screenHeight = dm.heightPixels;

        iv_result = (ImageView) findViewById(R.id.iv_result);

        detection = new Sobel();

        int imgMaxWidth = CommonUtil.screenWidth;
        bitmap = BitmapUtil.getThumbnailBitmap(this, R.mipmap.test2, imgMaxWidth);

        Bitmap bm = detection.detection(bitmap);

        if(bm != null) {
            iv_result.setImageBitmap(bm);
            Log.i("MainActivity", "bitmap load end");
        } else {
            Log.i("MainActivity", "bitmap null");
        }
    }
}
