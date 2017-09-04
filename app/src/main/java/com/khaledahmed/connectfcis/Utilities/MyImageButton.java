package com.khaledahmed.connectfcis.Utilities;

import android.content.Context;
import android.widget.ImageButton;

/**
 * Created by Khaled Ahmed on 3/5/2017.
 */
public class MyImageButton extends ImageButton {

    private int mImageResource = 0;

    public MyImageButton(Context context) {
        super(context);
    }

    @Override
    public void setImageResource(int resId) {
        mImageResource = resId;
        super.setImageResource(resId);
    }

    public int getImageResource() {
        return mImageResource;
    }
}
