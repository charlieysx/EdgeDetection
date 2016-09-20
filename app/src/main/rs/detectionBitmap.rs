#pragma version(1)
#pragma rs java_package_name(com.elims.edgedetection.detection)
#pragma rs_fp_relaxed

//原图
rs_allocation initBitmap;
//灰度化后的图
rs_allocation grayBitmap;
//图片的宽度
uint32_t width;
//图片的高度
uint32_t height;



static uchar4 gx(uint32_t x, uint32_t y) {

    return rsGetElementAt_uchar4(initBitmap, x - 1, y);
}

static uchar4 gy(uint32_t x, uint32_t y) {

    return rsGetElementAt_uchar4(initBitmap, x - 1, y);
}

uchar4 __attribute__((kernel)) calculateSobel(uchar4 in, uint32_t x, uint32_t y){
    uchar4 out = in;

    uchar4 left = in;
    uchar4 top = in;
    uchar4 right = in;
    uchar4 bottom = in;

    if(x - 1 > -1){
        left = rsGetElementAt_uchar4(initBitmap, x - 1, y);
    }
    if(y - 1 > -1){
        top = rsGetElementAt_uchar4(initBitmap, x , y - 1);
    }
    if(x + 1 < width){
        right = rsGetElementAt_uchar4(initBitmap, x + 1, y);
    }
    if(y + 1 < height){
        bottom = rsGetElementAt_uchar4(initBitmap, x, y + 1);
    }

    out.r = (left.r + top.r + right.r + bottom.r) / 4;
    out.g = (left.g + top.g + right.g + bottom.g) / 4;
    out.b = (left.b + top.b + right.b + bottom.b) / 4;

    return out;
}

