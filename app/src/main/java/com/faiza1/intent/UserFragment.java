package com.faiza1.intent;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.faiza1.intent.callback.DataCallback;
import com.faiza1.intent.dao.UserDAO;
import com.faiza1.intent.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends Fragment {

    private ImageView btnicon;
    private CircleImageView ivPreview;
    private Uri imageUri;

    // Launcher for Camera
    private final ActivityResultLauncher<Uri> captureImageLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicture(),
                    new ActivityResultCallback<Boolean>() {
                        @Override
                        public void onActivityResult(Boolean isCaptured) {
                            if (isCaptured && imageUri != null) {
                                ivPreview.setImageURI(imageUri);
                                uploadImageToFirebase(imageUri);
                            } else {
                                Toast.makeText(getContext(), "Image capture failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    // Launcher for Gallery
    private final ActivityResultLauncher<String> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(),
                    new ActivityResultCallback<Uri>() {
                        @Override
                        public void onActivityResult(Uri uri) {
                            if (uri != null) {
                                ivPreview.setImageURI(uri);
                                uploadImageToFirebase(uri);
                            }
                        }
                    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        btnicon = view.findViewById(R.id.btn_icon);
        ivPreview = view.findViewById(R.id.ivPreview);
        Button btnLogOut = view.findViewById(R.id.btn_logout);
        TextView edtEmail = view.findViewById(R.id.edt_email);
        TextView tvName = view.findViewById(R.id.tv_name);

        // Show user name from Firebase
        new UserDAO().getUser(new DataCallback<>() {
            @Override
            public void onData(User user) {
                if(user!= null){
                    tvName.setText(user.getName());
                }
            }

            @Override
            public void onError(String error) {
                MyUtil.showToast(getContext(), error);
            }
        });

        // Set email
        edtEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        // Logout button
        btnLogOut.setOnClickListener(v -> showLogoutConfirmationDialog());

        // When icon is clicked, show custom dialog
        btnicon.setOnClickListener(v -> showImageOptionsDialog());

        return view;
    }

    private void showImageOptionsDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.activity_custom2, null);

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .create();

        dialogView.findViewById(R.id.iv_camera).setOnClickListener(v -> {
            dialog.dismiss();
            imageUri = requireActivity().getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
            captureImageLauncher.launch(imageUri);
        });

        dialogView.findViewById(R.id.iv_gallery).setOnClickListener(v -> {
            dialog.dismiss();
            pickImageLauncher.launch("image/*");
        });

        dialog.show();
    }


    private void uploadImageToFirebase(Uri uri) {
        String imageString = MyUtil.imageToBase64(uri, requireActivity().getContentResolver());
        if (imageString != null) {
            String authId = FirebaseAuth.getInstance().getUid();
            FirebaseDatabase.getInstance().getReference("UserImages")
                    .child(authId)
                    .setValue(imageString);
        } else {
            Toast.makeText(getContext(), "Image conversion failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Logout Confirmation")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    if (getActivity() != null) getActivity().finish();
                    Toast.makeText(getActivity(), "Logout successfully", Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
