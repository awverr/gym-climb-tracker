package com.awverret.gymclimbtracker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.awverret.gymclimbtracker.R;
import com.awverret.gymclimbtracker.model.BoulderClimb;
import com.awverret.gymclimbtracker.model.BoulderGrade;
import com.awverret.gymclimbtracker.model.Climb;
import com.awverret.gymclimbtracker.model.ClimbType;
import com.awverret.gymclimbtracker.model.LeadClimb;
import com.awverret.gymclimbtracker.model.RopeGrade;
import com.awverret.gymclimbtracker.model.TopRopeClimb;
import com.awverret.gymclimbtracker.store.CloudStore;
import com.awverret.gymclimbtracker.store.FirebaseCloudStore;
import com.google.firebase.FirebaseApp;

import static com.awverret.gymclimbtracker.model.ClimbType.BOULDER;
import static com.awverret.gymclimbtracker.model.ClimbType.LEAD;
import static com.awverret.gymclimbtracker.model.ClimbType.TOP_ROPE;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

        Spinner climbTypeSpinner, gradeSpinner;
        CloudStore store;
        ClimbType climbType = TOP_ROPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        climbTypeSpinner = (Spinner) findViewById(R.id.climb_type_spinner);
        gradeSpinner = (Spinner) findViewById(R.id.grade_spinner);
        initializeClimbTypeSpinner(climbTypeSpinner);
        climbTypeSpinner.setOnItemSelectedListener(this);

        store = new FirebaseCloudStore(this);
    }

    public void initializeClimbTypeSpinner(Spinner spinner){
        //climb_type_spinner

        ArrayAdapter<CharSequence> climbTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.climb_type_array, android.R.layout.simple_spinner_item);
        climbTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(climbTypeAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String climbTypeString= String.valueOf(climbTypeSpinner.getSelectedItem());
        if(climbTypeString.contentEquals("Top Rope")) {
            climbType = TOP_ROPE;
            ArrayAdapter<CharSequence> ropeGradeAdapter = ArrayAdapter.createFromResource(this,
                    R.array.rope_grade_array, android.R.layout.simple_spinner_item);
            ropeGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            gradeSpinner.setAdapter(ropeGradeAdapter);
        }
        if (climbTypeString.contentEquals("Lead")){
            climbType = LEAD;
            ArrayAdapter<CharSequence> ropeGradeAdapter = ArrayAdapter.createFromResource(this,
                    R.array.rope_grade_array, android.R.layout.simple_spinner_item);
            ropeGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            gradeSpinner.setAdapter(ropeGradeAdapter);

        }
        if(climbTypeString.contentEquals("Boulder")) {
            climbType = BOULDER;
            ArrayAdapter<CharSequence> boulderGradeAdapter = ArrayAdapter.createFromResource(this,
                    R.array.boulder_grade_array, android.R.layout.simple_spinner_item);
            boulderGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            gradeSpinner.setAdapter(boulderGradeAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void saveClimb(View view){
        String stringGrade = (String) gradeSpinner.getSelectedItem();
        if(climbType.equals(LEAD)){
            RopeGrade grade = RopeGrade.fromString(stringGrade);
            store.saveClimb(new LeadClimb(grade));
        }
        if(climbType.equals(TOP_ROPE)){
            RopeGrade grade = RopeGrade.fromString(stringGrade);
            store.saveClimb(new TopRopeClimb(grade));
        }
        if(climbType.equals(BOULDER)){
            BoulderGrade grade = BoulderGrade.fromString(stringGrade);
            store.saveClimb(new BoulderClimb(grade));
        }
    }
}
