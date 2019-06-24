package com.skyfree.pedometer_and_calorimeter.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.skyfree.pedometer_and_calorimeter.Key;
import com.skyfree.pedometer_and_calorimeter.R;
import com.skyfree.pedometer_and_calorimeter.StepLength;


import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.media.CamcorderProfile.get;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    Spinner spnStepGoal, spnSensitivity, spnWeight, spnGender, spnStepLength, spnFirstDay;
    RelativeLayout rlStepLength;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    int spinnerPosition;



    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        spnStepGoal = (Spinner) view.findViewById(R.id.spnStepGoal);
        spnSensitivity = (Spinner) view.findViewById(R.id.spnSensitivity);
        spnWeight = (Spinner) view.findViewById(R.id.spnWeight);
        spnGender = (Spinner) view.findViewById(R.id.spnGender);
        spnStepLength = (Spinner) view.findViewById(R.id.spnStepLength);
        spnFirstDay = (Spinner) view.findViewById(R.id.spnFirstDay);
        rlStepLength = (RelativeLayout) view.findViewById(R.id.stepLength);

        final List<Integer> listStepGoal = new ArrayList<>();
        final List<String> listGender = new ArrayList<>();
        final List<String> listSensitivity = new ArrayList<>();
        final List<Integer> listWeight = new ArrayList<>();

        sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();

        stepGoalData(listStepGoal);
        genderData(listGender);
        sensitivityData(listSensitivity);
        weightData(listWeight);

        ArrayAdapter<Integer> adapterStepGoal = new ArrayAdapter<Integer>(this.getActivity(), R.layout.custom_spinner_item, listStepGoal);
        adapterStepGoal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStepGoal.setAdapter(adapterStepGoal);

        ArrayAdapter<String> adapterGender = new ArrayAdapter<String>(this.getActivity(), R.layout.custom_spinner_item, listGender);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(adapterGender);

        ArrayAdapter<String> adapterSensitivity = new ArrayAdapter<String>(this.getActivity(), R.layout.custom_spinner_item, listSensitivity);
        adapterSensitivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSensitivity.setAdapter(adapterSensitivity);

        ArrayAdapter<Integer> adapterWeight = new ArrayAdapter<Integer>(this.getActivity(), R.layout.custom_spinner_item, listWeight);
        adapterWeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnWeight.setAdapter(adapterWeight);

        int stepGoalPosition= sharedPreferences.getInt(Key.STEP_GOAL_VALUES,-1);
        if(stepGoalPosition != -1)
            spnStepGoal.setSelection(stepGoalPosition);

        int genderPosition= sharedPreferences.getInt(Key.GENDER,-1);
        if(genderPosition != -1)
            spnGender.setSelection(genderPosition);

        int sensitivityPosition= sharedPreferences.getInt(Key.SENSITIVITY,-1);
        if(sensitivityPosition != -1)
            spnSensitivity.setSelection(sensitivityPosition);

        int weightPosition= sharedPreferences.getInt(Key.WEIGHT, -1);
        if(weightPosition != -1){
            spnWeight.setSelection(weightPosition);
        }


        spnStepGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerPosition = spnStepGoal.getSelectedItemPosition();
                saveStepGoal(Key.STEP_GOAL,listStepGoal.get(i));
                saveValuesStepGoal(Key.STEP_GOAL_VALUES,spinnerPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerPosition = spnGender.getSelectedItemPosition();
                saveGender(Key.GENDER, spinnerPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spnSensitivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerPosition = spnSensitivity.getSelectedItemPosition();
                saveSensitivity(Key.SENSITIVITY, spinnerPosition);
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerPosition = spnWeight.getSelectedItemPosition();
                saveWeight(Key.WEIGHT, spinnerPosition);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rlStepLength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StepLength.class);
                startActivity(intent);
            }
        });




        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void stepGoalData(List list){
        list.add(500);
        list.add(1000);
        list.add(1500);
        list.add(2000);
        list.add(2500);
        list.add(3000);
        list.add(3500);
        list.add(4000);
        list.add(4500);
        list.add(5000);
        list.add(5500);
        list.add(6000);
        list.add(6500);
        list.add(7000);
    }

    public void genderData(List list){
        list.add("Male");
        list.add("Female");
    }

    public void sensitivityData(List list){
        list.add("Low");
        list.add("Medium");
        list.add("High");
    }

    public void weightData(List list){
        for(int i=1; i<=100;i++){
            list.add(i + " Kg");
        }
    }

    public void saveSensitivity(String key, Integer value){
        editor.putInt(key, value);
        editor.commit();
    }

    public void saveStepGoal(String key, Integer value){
        editor.putInt(key, value);
        editor.commit();
    }

    public void saveValuesStepGoal(String key, Integer value){
        editor.putInt(key, value);
        editor.commit();
    }

    public void saveGender(String key, Integer value){
        editor.putInt(key, value);
        editor.commit();
    }

    public void saveWeight(String key, Integer value){
        editor.putInt(key, value);
        editor.commit();
    }
}
