package com.elims.edgedetection;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.elims.edgedetection.detection.Detection;
import com.elims.edgedetection.detection.Sobel;
import com.elims.edgedetection.utils.BitmapUtil;
import com.elims.edgedetection.utils.CommonUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMain {

    private ImageView iv_result;
    private Button bt_last;
    private Button bt_next;
    private Button bt_change;
    private EditText et_threshold;
    private EditText et_gray_threshold;
    private EditText et_pale_grey_threshold;
    private Detection detection;
    private Bitmap bitmap;
    private int imgMaxWidth;

    private int[] imgIds = {R.mipmap.test, R.mipmap.test2, R.mipmap.test3};
    private int thisImg;
    private boolean dealing;
    private boolean isd;

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
        bt_last = (Button) findViewById(R.id.bt_last);
        bt_next = (Button) findViewById(R.id.bt_next);
        bt_change = (Button) findViewById(R.id.bt_change);
        et_threshold = (EditText) findViewById(R.id.et_threshold);
        et_gray_threshold = (EditText) findViewById(R.id.et_gray_threshold);
        et_pale_grey_threshold = (EditText) findViewById(R.id.et_pale_grey_threshold);

        bt_last.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        bt_change.setOnClickListener(this);

        detection = new Sobel(this);

        imgMaxWidth = CommonUtil.screenWidth;
        thisImg = 0;

        dealing = false;
        isd = false;
        deal(0, 0, 0);
    }

    private void showBitmap(Bitmap bitmap){

        if(bitmap != null) {
            iv_result.setImageBitmap(bitmap);
            Log.i("MainActivity", "bitmap load end");
        } else {
            Log.i("MainActivity", "bitmap null");
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_last:
                if(dealing) {
                    Toast.makeText(this, "图象处理中...", Toast.LENGTH_SHORT).show();
                } else {
                    thisImg--;
                    if (thisImg < 0) {
                        thisImg = 0;
                        Toast.makeText(this, "已经是第一张了", Toast.LENGTH_SHORT).show();
                    } else {
                        isd = false;
                        deal(0, 0, 0);
                        et_threshold.setText("0");
                        et_gray_threshold.setText("0");
                        et_pale_grey_threshold.setText("0");
                    }
                }
                break;
            case R.id.bt_next:
                if(dealing) {
                    Toast.makeText(this, "图象处理中...", Toast.LENGTH_SHORT).show();
                } else {
                    thisImg++;
                    if (thisImg > 2) {
                        thisImg = 2;
                        Toast.makeText(this, "已经是最后一张了", Toast.LENGTH_SHORT).show();
                    } else {
                        isd = false;
                        deal(0, 0, 0);
                        et_threshold.setText("0");
                        et_gray_threshold.setText("0");
                        et_pale_grey_threshold.setText("0");
                    }
                }
                break;
            case R.id.bt_change:
                if(dealing) {
                    Toast.makeText(this, "图象处理中...", Toast.LENGTH_SHORT).show();
                } else {
                    dealing = true;
                    String sa = et_threshold.getText().toString();
                    sa = sa.equals("") ? "0" : sa;
                    String sb = et_gray_threshold.getText().toString();
                    sb = sb.equals("") ? "0" : sb;
                    String sc = et_pale_grey_threshold.getText().toString();
                    sc = sc.equals("") ? "0" : sc;

                    double a = Double.parseDouble(sa);
                    double b = Double.parseDouble(sb);
                    double c = Double.parseDouble(sc);
//                    Toast.makeText(this, a + "\n" + b + "\n" + c, Toast.LENGTH_SHORT).show();
                    isd = true;
                    deal(a, b, c);
                }
                break;
            default:
                break;
        }
    }

    private void deal(final double a, final double b, final double c){
        new Thread(){
            @Override
            public void run() {
                bitmap = BitmapUtil.getThumbnailBitmap(MainActivity.this, imgIds[thisImg], imgMaxWidth);
                if(isd) {
                    detection.detection(bitmap);
                    ((Sobel) detection).getBitmap(a, b, c);
//                    Log.d("main", a + "--" + b + "--" + c);
                } else {
                    setBitmap(bitmap);
                }
            }
        }.start();
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        Message msg = new Message();
        msg.obj = bitmap;
        mHandler.sendMessage(msg);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            showBitmap(bitmap);
            dealing = false;
        }
    };

}
