package com.faiza1.intent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment {
   private LinearLayout box1,box2,box3,box4,box5;
Button btnActivate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the LinearLayouts
        box1 = view.findViewById(R.id.tv_box1);
        box2 = view.findViewById(R.id.tv_box2);
        box3 = view.findViewById(R.id.tv_box3);
        box4 = view.findViewById(R.id.tv_box4);
        box5 = view.findViewById(R.id.tv_box5);
        btnActivate = view.findViewById(R.id.btn_activate);

        btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity() , AlertActivatedActivity.class);
                startActivity(intent);
            }
        });




        box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity() ,  SafetyTipsActivity.class );
                startActivity(intent);
            }
        });

        box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Contact
                Intent intent = new Intent(getActivity() , AlertContactActivity2.class);
                startActivity(intent);
            }
        });
        box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Contact
                Intent intent = new Intent(getActivity() ,  EmergencyActivity2.class);
                startActivity(intent);
            }
        });
        box4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() ,  AlertContactsActivity.class);
                startActivity(intent);
            }
        });
        box5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() ,  LinksListActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
