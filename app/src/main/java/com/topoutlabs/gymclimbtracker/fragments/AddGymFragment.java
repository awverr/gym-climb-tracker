package com.topoutlabs.gymclimbtracker.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.activities.MainActivity;
import com.topoutlabs.gymclimbtracker.model.Gym;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;

public class AddGymFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    Spinner gymStateSpinner;

    Button saveGymButton;

    CloudStore store;

    private MainActivity activity;

    View view;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        if(activity == null && context instanceof MainActivity){
            activity = (MainActivity) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        if(view == null) {
            view = inflater.inflate(R.layout.fragment_add_gym, container, false);

            store = new FirebaseCloudStore();

            gymStateSpinner = view.findViewById(R.id.gym_state_spinner);
            initializeGymStateSpinner(gymStateSpinner);
            gymStateSpinner.setOnItemSelectedListener(this);

            saveGymButton = view.findViewById(R.id.save_gym_button);
            saveGymButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    EditText editTextName = view.findViewById(R.id.gym_name_text_view);
                    String stringGymState = (String) gymStateSpinner.getSelectedItem();
                    String stringName = editTextName.getText().toString();

                    store.saveGym(new Gym(stringName, stringGymState));

                    //Todo: Go to chooseGymFragment after new gym is saved.
                    ChooseGymFragment chooseGymFragment = new ChooseGymFragment();

                    chooseGymFragment.setArguments(getActivity().getIntent().getExtras());

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragment_container, chooseGymFragment).commit();
                    transaction.addToBackStack(null);
                }
            });
        }

        return view;
    }

    public void initializeGymStateSpinner(Spinner spinner){
        ArrayAdapter<CharSequence> gymStateAdapter = ArrayAdapter.createFromResource(activity,
                R.array.state_filter_array, android.R.layout.simple_spinner_item);
        gymStateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(gymStateAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
