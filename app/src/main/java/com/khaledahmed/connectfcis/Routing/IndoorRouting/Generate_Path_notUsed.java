package com.khaledahmed.connectfcis.Routing.IndoorRouting;

import android.graphics.Bitmap;

/**
 * Created by mahmo on 4/18/2017.
 * Not used. draw a line bet. 2 pixels
 */

public class Generate_Path_notUsed {


    private Bitmap mImage;
    private boolean mIsError = false;

    public void ImageProcessor(final Bitmap image) {
        mImage = image.copy(image.getConfig(), image.isMutable());
        if (mImage == null) {
            mIsError = true;
        }
    }


    public boolean isError() {
        return mIsError;
    }

    public void setImage(final Bitmap image) {
        mImage = image.copy(image.getConfig(), image.isMutable());
        if (mImage == null) {
            mIsError = true;
        } else {
            mIsError = false;
        }
    }

    public Bitmap getImage() {
        if (mImage == null) {
            return null;
        }
        return mImage.copy(mImage.getConfig(), mImage.isMutable());
    }

    public void free() {
        if (mImage != null && !mImage.isRecycled()) {
            mImage.recycle();
            mImage = null;
        }
    }

    //   int fromColor,
    public void DrawLine(int srcPixel, int destPixel) {
        if (mImage == null) {
            return;
        }


        int width = mImage.getWidth();
        int height = mImage.getHeight();
        int[] pixels = new int[width * height];
        mImage.getPixels(pixels, 0, width, 0, 0, width, height);


        //draw line bet these 2 pixels: on  mImage


        //


        Bitmap newImage = Bitmap.createBitmap(width, height, mImage.getConfig());
        newImage.setPixels(pixels, 0, width, 0, 0, width, height);

        // return newImage;
    }


}



