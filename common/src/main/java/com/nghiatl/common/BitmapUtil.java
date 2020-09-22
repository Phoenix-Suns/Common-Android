package com.nghiatl.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Nghia-PC on 9/8/2015.
 */
public class BitmapUtil {

    /** Đổi cỡ Bitmap
     * Chưa test*/
    public static Bitmap resize(Bitmap bitmapSource, int maxSize){

        int sourceWidth = bitmapSource.getWidth();
        int sourceHeight = bitmapSource.getHeight();
        int targetWidth = maxSize;
        int targetHeight = maxSize;

        //if Source Width > Height => Resize Height theo Width;
        if (sourceWidth > sourceHeight){
            targetHeight = (int)((float)targetWidth * sourceHeight / sourceWidth );
        } else {
            targetWidth = (int)((float)targetHeight * sourceWidth / sourceHeight);
        }

        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmapSource, targetWidth, targetHeight, false);

        return newBitmap;
    }

    /** Lưu ảnh Bitmap
     * Chưa test
     * @param bitmap
     * @param savePath
     * @param format ex: Bitmap.CompressFormat.JPEG
     * @param quality from 0 to 100
     * @throws IOException
     * return true: thành công
     */
    public static boolean save(Bitmap bitmap, String savePath, Bitmap.CompressFormat format, int quality) {
        try {
            // save
            File outFile = new File(savePath);
            FileOutputStream fos = new FileOutputStream(outFile);
            bitmap.compress(format, quality, fos);
            fos.flush();
            fos.close();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Đổi kích thước và Lưu
     * @param bitmap
     * @param maxSize
     * @param savePath
     * @return thành công hay Không
     */
    public static boolean resizeAndSave(Bitmap bitmap, int maxSize, String savePath) {
        Bitmap resultBitmap = BitmapUtil.resize(bitmap, maxSize);
        // giải phóng bộ nhớ
        /*if (bitmapSource!= newBitmap){
            bitmapSource.recycle();
        }*/
        return BitmapUtil.save(resultBitmap, savePath, Bitmap.CompressFormat.JPEG, 100);
    }

    /**
     * Lấy Bitmap từ file
     * @param filePath
     * @param quality chất lượng ảnh, từ 0 đến 10
     */
    public static Bitmap getFromPath(String filePath, int quality) {
        // bitmap factory
        BitmapFactory.Options options = new BitmapFactory.Options();
        // down sizing image as it throws OutOfMemory Exception
        options.inSampleSize = quality;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * Merge two bitmap
     * Ex:
      Bitmap qrBitmap = makeQRCodeBitmap(qrCode);
     Bitmap icon = BitmapFactory.decodeResource(requireContext().getResources(), R.drawable.ic_leep_placeholder);
      icon = Bitmap.createScaledBitmap(icon, qrBitmap.getHeight()/5, qrBitmap.getHeight()/5, false);
      qrBitmap = mergeBitmaps(icon, qrBitmap);
     * @param logo
     * @param qrcode
     * @return
     */
    public Bitmap mergeBitmaps(Bitmap logo, Bitmap qrcode) {

        Bitmap combined = Bitmap.createBitmap(qrcode.getWidth(), qrcode.getHeight(), qrcode.getConfig());
        Canvas canvas = new Canvas(combined);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        canvas.drawBitmap(qrcode, new Matrix(), null);

        //Bitmap resizeLogo = Bitmap.createScaledBitmap(logo, canvasWidth / 5, canvasHeight / 5, true);
        Bitmap resizeLogo = Bitmap.createBitmap(logo);
        int centreX = (canvasWidth - resizeLogo.getWidth()) /2;
        int centreY = (canvasHeight - resizeLogo.getHeight()) / 2;
        canvas.drawBitmap(resizeLogo, centreX, centreY, null);
        return combined;
    }
}
