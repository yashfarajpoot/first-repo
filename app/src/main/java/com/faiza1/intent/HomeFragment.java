package com.faiza1.intent;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {
   private LinearLayout box1,box2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the LinearLayouts
        box1 = view.findViewById(R.id.tv_box1);
        box2 = view.findViewById(R.id.tv_box2);


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
                Intent intent = new Intent(getActivity() ,  AlertContactListActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
