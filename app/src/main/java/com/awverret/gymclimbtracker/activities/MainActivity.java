package com.awverret.gymclimbtracker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.awverret.gymclimbtracker.R;
import com.awverret.gymclimbtracker.model.BoulderRoute;
import com.awverret.gymclimbtracker.model.BoulderGrade;
import com.awverret.gymclimbtracker.model.RouteColor;
import com.awverret.gymclimbtracker.model.RouteSetter;
import com.awverret.gymclimbtracker.model.RouteType;
import com.awverret.gymclimbtracker.model.LeadRoute;
import com.awverret.gymclimbtracker.model.RopeGrade;
import com.awverret.gymclimbtracker.model.RouteWall;
import com.awverret.gymclimbtracker.model.TopRopeRoute;
import com.awverret.gymclimbtracker.store.CloudStore;
import com.awverret.gymclimbtracker.store.FirebaseCloudStore;
import com.google.firebase.FirebaseApp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.awverret.gymclimbtracker.model.RouteType.BOULDER;
import static com.awverret.gymclimbtracker.model.RouteType.LEAD;
import static com.awverret.gymclimbtracker.model.RouteType.TOP_ROPE;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

        Spinner routeTypeSpinner, routeGradeSpinner, routeSetterSpinner, routeWallSpinner, routeColorSpinner;
        CloudStore store;
        RouteType routeType = TOP_ROPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        routeTypeSpinner = (Spinner) findViewById(R.id.route_type_spinner);
        routeGradeSpinner = (Spinner) findViewById(R.id.grade_spinner);
        routeSetterSpinner = (Spinner) findViewById(R.id.route_setter_spinner);
        routeWallSpinner = (Spinner) findViewById(R.id.route_wall_spinner);
        routeColorSpinner = (Spinner) findViewById(R.id.route_color_spinner);
        initializeRouteTypeSpinner(routeTypeSpinner);
        initializeRouteSetterSpinner(routeSetterSpinner);
        initializeRouteWallSpinner(routeWallSpinner);
        initializeRouteColorSpinner(routeColorSpinner);
        routeTypeSpinner.setOnItemSelectedListener(this);
        routeSetterSpinner.setOnItemSelectedListener(this);
        routeWallSpinner.setOnItemSelectedListener(this);
        routeColorSpinner.setOnItemSelectedListener(this);

        store = new FirebaseCloudStore(this);
    }

    public void initializeRouteTypeSpinner(Spinner spinner){
        //route_type_spinner

        ArrayAdapter<CharSequence> routeTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.route_type_array, android.R.layout.simple_spinner_item);
        routeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(routeTypeAdapter);
    }

    public void initializeRouteSetterSpinner(Spinner spinner){
        //route_setter_spinner

        ArrayAdapter<CharSequence> routeSetterAdapter = ArrayAdapter.createFromResource(this,
                R.array.route_setter_array, android.R.layout.simple_spinner_item);
        routeSetterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(routeSetterAdapter);
    }

    public void initializeRouteColorSpinner(Spinner spinner){
        //route_color_spinner

        ArrayAdapter<CharSequence> routeColorAdapter = ArrayAdapter.createFromResource(this,
                R.array.route_color_array, android.R.layout.simple_spinner_item);
        routeColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(routeColorAdapter);
    }

    public void initializeRouteWallSpinner(Spinner spinner){
        //route_wall_spinner

        ArrayAdapter<CharSequence> routeWallAdapter = ArrayAdapter.createFromResource(this,
                R.array.route_wall_array, android.R.layout.simple_spinner_item);
        routeWallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(routeWallAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String routeTypeString= String.valueOf(routeTypeSpinner.getSelectedItem());
        if(routeTypeString.contentEquals("Top Rope")) {
            routeType = TOP_ROPE;
            ArrayAdapter<CharSequence> ropeGradeAdapter = ArrayAdapter.createFromResource(this,
                    R.array.rope_grade_array, android.R.layout.simple_spinner_item);
            ropeGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            routeGradeSpinner.setAdapter(ropeGradeAdapter);
        }
        if (routeTypeString.contentEquals("Lead")){
            routeType = LEAD;
            ArrayAdapter<CharSequence> ropeGradeAdapter = ArrayAdapter.createFromResource(this,
                    R.array.rope_grade_array, android.R.layout.simple_spinner_item);
            ropeGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            routeGradeSpinner.setAdapter(ropeGradeAdapter);

        }
        if(routeTypeString.contentEquals("Boulder")) {
            routeType = BOULDER;
            ArrayAdapter<CharSequence> boulderGradeAdapter = ArrayAdapter.createFromResource(this,
                    R.array.boulder_grade_array, android.R.layout.simple_spinner_item);
            boulderGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            routeGradeSpinner.setAdapter(boulderGradeAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void saveRoute(View view) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("MM/dd", Locale.ENGLISH); // Make sure user insert date into edittext in this format.

        String stringGrade = (String) routeGradeSpinner.getSelectedItem();
        EditText editTextDate = (EditText) findViewById(R.id.date_text_box);
        String stringDate = editTextDate.getText().toString();
        String stringSetter = (String) routeSetterSpinner.getSelectedItem();
        String stringColor = (String) routeColorSpinner.getSelectedItem();
        String stringWall = (String) routeWallSpinner.getSelectedItem();
        RouteSetter setter = RouteSetter.fromString(stringSetter);
        RouteColor color = RouteColor.fromString(stringColor);
        RouteWall wall = RouteWall.fromString(stringWall);

        System.out.println("Date string is: " + stringDate);

        Date date = formatter.parse(stringDate);

        if(routeType.equals(LEAD)){
            RopeGrade grade = RopeGrade.fromString(stringGrade);
            store.saveRoute(new LeadRoute(grade, "unnamed", setter, color, wall, date));
        }
        if(routeType.equals(TOP_ROPE)){
            RopeGrade grade = RopeGrade.fromString(stringGrade);
            store.saveRoute(new TopRopeRoute(grade, "unnamed", setter, color, wall, date));
        }
        if(routeType.equals(BOULDER)){
            BoulderGrade grade = BoulderGrade.fromString(stringGrade);
            store.saveRoute(new BoulderRoute(grade, "unnamed", setter, color, wall, date));
        }
    }
}
