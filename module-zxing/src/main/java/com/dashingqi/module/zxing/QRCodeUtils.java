package com.dashingqi.module.zxing;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * @author : zhangqi
 * @time : 2020/7/21
 * desc :
 */
public class QRCodeUtils {

    public static Bitmap createQRCode(String url, int w, int h) {

        Bitmap bitmap = null;
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, w, h, hints);
            int[] pixels = new int[w * h];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * w + x] = 0xff000000;
                    } else {
                        pixels[y * w + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
