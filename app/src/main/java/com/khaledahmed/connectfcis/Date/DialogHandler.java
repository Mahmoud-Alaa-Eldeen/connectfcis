package com.khaledahmed.connectfcis.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by Khaled Ahmed on 12/9/2016.
 */
public class DialogHandler extends DialogFragment {
    String date = "";

    public String getDate() {
        return date;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog;
        DateSettings dateSettings = new DateSettings(getActivity());
        dialog = new DatePickerDialog(getActivity(), dateSettings, year, month, day);
        date = dateSettings.getDate();
        return dialog;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, android.support.design.R.style.);
//    }
}
