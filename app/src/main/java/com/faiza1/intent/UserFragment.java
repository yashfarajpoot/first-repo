package com.faiza1.intent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.faiza1.intent.callback.DataCallback;
import com.faiza1.intent.dao.UserDAO;
import com.faiza1.intent.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserFragment extends Fragment {


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


        Button btnLogOut = view.findViewById(R.id.btn_logout);
        TextView edtEmail = view.findViewById(R.id.edt_email);
        TextView tvName = view.findViewById(R.id.tv_name);

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
//        DatabaseReference userRef = FirebaseDatabase.getInstance()
//                .getReference("Users")
//                .child(FirebaseAuth.getInstance().getUid());
//
//        userRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//
//                tvName.setText(user.getName());
//
//                Log.e("onDataChange: ", user.getName() + " ");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        edtEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLogoutConfirmationDialog();

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
                        // Perform logout action
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), MainActivity.class); // Redirect to MainActivity
                        startActivity(intent);

                        if (getActivity() != null)
                            getActivity().finish();

                        Toast.makeText(getActivity(), "Logout successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Close the dialog
                    }
                })
                .show();
    }
}
