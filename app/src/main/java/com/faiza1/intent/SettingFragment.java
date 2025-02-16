package com.faiza1.intent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class SettingFragment extends Fragment {


    public SettingFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_setting, container, false);
        TextView  setting = view.findViewById(R.id.tv_setting);
        Button btnFragmentClick = view.findViewById(R.id.btn_contact);
        Button btnEmergencyClick = view.findViewById(R.id.btn_emergency);

        // Set click listener
        btnFragmentClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start a new Activity when button is clicked
                Intent intent = new Intent(getActivity() , AlertContactListActivity.class);
                startActivity(intent);
            }
        });

        btnEmergencyClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start a new Activity when button is clicked
                Intent intent = new Intent(getActivity(),EmergencyActivity2.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
