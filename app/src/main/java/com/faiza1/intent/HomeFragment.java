package com.faiza1.intent;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class HomeFragment extends Fragment {

    private LinearLayout box1, box2, box3, box4, box5;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private static final int GPS_REQUEST_CODE = 102;

    Button btnActivate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find views
        box1 = view.findViewById(R.id.tv_box1);
        box2 = view.findViewById(R.id.tv_box2);
        box3 = view.findViewById(R.id.tv_box3);
        box4 = view.findViewById(R.id.tv_box4);
        box5 = view.findViewById(R.id.tv_box5);
        btnActivate = view.findViewById(R.id.btn_activate);

        // ✅ Activate Button Logic
        btnActivate.setOnClickListener(v -> {

            if (!hasAtLeastOneContact()) {
                Toast.makeText(getContext(), "Please add at least one contact to continue.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!hasLocationPermission()) {
                requestLocationPermission();
                return;
            }

            if (!isLocationEnabled()) {
                promptToEnableGPS();
                return;
            }

            // Sab kuch sahi hai
            startActivity(new Intent(getActivity(), AlertActivatedActivity.class));
        });

        // Navigation logic
        box1.setOnClickListener(v -> startActivity(new Intent(getActivity(), SafetyTipsActivity.class)));
        box2.setOnClickListener(v -> startActivity(new Intent(getActivity(), AlertContactActivity2.class)));
        box3.setOnClickListener(v -> startActivity(new Intent(getActivity(), EmergencyActivity2.class)));
        box4.setOnClickListener(v -> startActivity(new Intent(getActivity(), AlertContactsActivity.class)));
        box5.setOnClickListener(v -> startActivity(new Intent(getActivity(), LinksListActivity.class)));

        return view;
    }

    // ✅ Check if at least one contact is saved
    private boolean hasAtLeastOneContact() {
        SharedPreferences prefs = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int contactCount = prefs.getInt("contact_count", 0);
        return contactCount > 0;
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

        if (requestCode == GPS_REQUEST_CODE) {
            if (isLocationEnabled()) {
                Toast.makeText(getContext(), "GPS enabled", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), AlertActivatedActivity.class));
            } else {
                Toast.makeText(getContext(), "Please enable GPS to continue", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && allPermissionsGranted(grantResults)) {
                if (isLocationEnabled()) {
                    startActivity(new Intent(getActivity(), AlertActivatedActivity.class));
                } else {
                    promptToEnableGPS();
                }
            } else {
                Toast.makeText(getContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
                showPermissionDeniedDialog();
            }
        }
    }

    private boolean allPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void showPermissionDeniedDialog() {
        new AlertDialog.Builder(getContext())
                .setMessage("Location permission is required to proceed.")
                .setPositiveButton("Go to Settings", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
