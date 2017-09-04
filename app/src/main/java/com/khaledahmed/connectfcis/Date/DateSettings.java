package com.khaledahmed.connectfcis.Date;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.DatePicker;

/**
 * Created by Khaled Ahmed on 12/9/2016.
 */
public class DateSettings implements DatePickerDialog.OnDateSetListener{
    Context context;
    String date = "";

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DateSettings(Context context) {
        this.context = context;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        setDate(i2 + "/" + i1 + "/" + i);
        Intent intent = new Intent();
        intent.putExtra("c_date", i2 + "/" + i1 + "/" + i);
    }
}
