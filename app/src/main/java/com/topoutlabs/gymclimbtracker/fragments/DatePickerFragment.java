package com.topoutlabs.gymclimbtracker.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;

import com.topoutlabs.gymclimbtracker.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
 //   System.out.println("Date info: " + month + " " + day + " " + year);

    String dateString = String.valueOf(month + 1) + "/" + String.valueOf(day) + "/" + String.valueOf(year);

 //   System.out.println("Date info: " + dateString);

        ViewClimbFragment viewClimbFragment = new ViewClimbFragment();

        Bundle bundle = new Bundle();
        bundle.putString("date", dateString);

//        viewClimbFragment.setArguments(bundle);
//
//        FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
//
//        FragmentTransaction transaction = manager.beginTransaction();
//
//        transaction.replace(R.id.fragment_container, viewClimbFragment);
//        transaction.addToBackStack(null);
//
//        transaction.commit();
    }
}
