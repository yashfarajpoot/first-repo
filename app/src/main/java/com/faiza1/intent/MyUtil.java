package com.faiza1.intent;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MyUtil {
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void loadFragment(AppCompatActivity activity, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id. container, fragment)
                .commit();
    }
}

