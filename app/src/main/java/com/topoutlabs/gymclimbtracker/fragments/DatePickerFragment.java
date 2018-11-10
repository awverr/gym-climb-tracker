package com.topoutlabs.gymclimbtracker.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDatePicked(int year, int month, int day);
//        public void onDialogPositiveClick(DialogFragment dialog);
//        public void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

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

    // Use this instance of the interface to deliver action events

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user

        NoticeDialogListener listener = (NoticeDialogListener) getParentFragment();

        listener.onDatePicked(year, month, day);

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
