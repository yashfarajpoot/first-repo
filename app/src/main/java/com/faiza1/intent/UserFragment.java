package com.faiza1.intent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.faiza1.intent.callback.DataCallback;
import com.faiza1.intent.dao.UserDAO;
import com.faiza1.intent.model.User;
import com.google.firebase.auth.FirebaseAuth;

public class UserFragment extends Fragment {

    private ImageView btnicon; // Declare globally if needed in other methods

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        btnicon = view.findViewById(R.id.btn_icon); // assign to global
        Button btnLogOut = view.findViewById(R.id.btn_logout);
        TextView edtEmail = view.findViewById(R.id.edt_email);
        TextView tvName = view.findViewById(R.id.tv_name);

        // Show user name from Firebase
        new UserDAO().getUser(new DataCallback<>() {
            @Override
            public void onData(User user) {
                tvName.setText(user.getName());
            }

            @Override
            public void onError(String error) {
                MyUtil.showToast(getContext(), error);
            }
        });

        // Set email
        edtEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        // Logout Button Click
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });

        // ✅ ImageView Click Listener: Open SecondActivity
        btnicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomActivity2.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Logout Confirmation")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        if (getActivity() != null) getActivity().finish();
                        Toast.makeText(getActivity(), "Logout successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", null)
                .show(); // ✅ this is the correct placement of .show()
    }
}

