package com.faiza1.intent;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class HomeFragment extends Fragment {

    private LinearLayout box1, box2, box3, box4, box5;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private static final int GPS_REQUEST_CODE = 102;
    Button btnActivate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        box1 = view.findViewById(R.id.tv_box1);
        box2 = view.findViewById(R.id.tv_box2);
        box3 = view.findViewById(R.id.tv_box3);
        box4 = view.findViewById(R.id.tv_box4);
        box5 = view.findViewById(R.id.tv_box5);
        btnActivate = view.findViewById(R.id.btn_activate);

        btnActivate.setOnClickListener(v -> {
            // Step 1: Check Location Permissions
            if (!hasLocationPermission()) {
                requestLocationPermission();
                return;
            }

            // Step 2: Check if GPS is enabled
            if (!isLocationEnabled()) {
                promptToEnableGPS();
                return;
            }

            // Step 3: Check Firebase AlertContacts
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("AlertContacts")
                    .child(FirebaseAuth.getInstance().getUid());

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long contactCount = snapshot.getChildrenCount();
                    if (contactCount >= 1) {
                        startActivity(new Intent(getActivity(), AlertActivatedActivity.class));
                    } else {
                        Toast.makeText(getContext(), "Please add at least one contact first.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Failed to check contacts", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Navigation setup
        box1.setOnClickListener(v -> startActivity(new Intent(getActivity(), SafetyTipsActivity.class)));
        box2.setOnClickListener(v -> startActivity(new Intent(getActivity(), AlertContactActivity2.class)));
        box3.setOnClickListener(v -> startActivity(new Intent(getActivity(), EmergencyActivity2.class)));
        box4.setOnClickListener(v -> startActivity(new Intent(getActivity(), AlertContactsActivity.class)));
        box5.setOnClickListener(v -> startActivity(new Intent(getActivity(), LinksListActivity.class)));

        return view;
    }

    private boolean hasLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
            }, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void promptToEnableGPS() {
        new AlertDialog.Builder(getContext())
                .setMessage("GPS is required for this feature. Please enable it.")
                .setPositiveButton("Go to Settings", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, GPS_REQUEST_CODE);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE && isLocationEnabled()) {
            startActivity(new Intent(getActivity(), AlertActivatedActivity.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && allPermissionsGranted(grantResults)) {
            if (isLocationEnabled()) {
                startActivity(new Intent(getActivity(), AlertActivatedActivity.class));
            } else {
                promptToEnableGPS();
            }
        } else {
            showPermissionDeniedDialog();
        }
    }

    private boolean allPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }

    private void showPermissionDeniedDialog() {
        new AlertDialog.Builder(getContext())
                .setMessage("Location permission is required to proceed.")
                .setPositiveButton("Go to Settings", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", requireActivity().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
