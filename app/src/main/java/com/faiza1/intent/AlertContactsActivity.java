package com.faiza1.intent;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faiza1.intent.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class AlertContactsActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> scanQrResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_contacts);

        RecyclerView rvContacts = findViewById(R.id.rv_contacts);
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnAddMe = findViewById(R.id.btn_add_me);

        scanQrResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                resultData -> {
                    if (resultData.getResultCode() == RESULT_OK) {
                        ScanIntentResult result = ScanIntentResult.parseActivityResult(resultData.getResultCode(), resultData.getData());

                        //this will be qr activity result
                        if (result.getContents() == null) {
                            Toast.makeText(AlertContactsActivity.this, "Error ayu reee", Toast.LENGTH_LONG).show();

                        } else {
                            String qrContents = result.getContents();
                            Toast.makeText(this, qrContents, Toast.LENGTH_SHORT).show();
                            FirebaseDatabase.getInstance().getReference("AlertContacts")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .child(qrContents)
                                    .setValue(true);

                            Log.e("qr-re: ", qrContents);
                            //TODO Handle qr result here
                        }
                    }
                });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scanQrResultLauncher.launch(new ScanContract().createIntent(AlertContactsActivity.this, new ScanOptions()));
            }
        });
        btnAddMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                QRGEncoder qrgEncoder = new QRGEncoder(FirebaseAuth.getInstance().getUid(), null, QRGContents.Type.TEXT, 250);
                qrgEncoder.setColorBlack(Color.BLACK);
                qrgEncoder.setColorWhite(Color.WHITE);
                // Getting QR-Code as Bitmap
                Bitmap bitmap = qrgEncoder.getBitmap(0);
                // Setting Bitmap to ImageView

                ImageView imageView = new ImageView(AlertContactsActivity.this);
                imageView.setPadding(10, 10, 10, 80);
                imageView.setImageBitmap(bitmap);

                AlertDialog.Builder builder = new AlertDialog.Builder(AlertContactsActivity.this);
                builder.setTitle("Add Me")
                        .setMessage("Scan QR code from app to add")
                        .setCancelable(true)
                        .setView(imageView);
                //              .setPositiveButton("Yes", (dialog, i) -> {
                //      alertContacts.remove(position);
                //     notifyItemRemoved(position);
                //     notifyItemRangeChanged(position, alertContacts.size());
                //    Toast.makeText(context, "Deleted successfully", Toast.LENGTH_LONG).show();
                //    dialog.dismiss();
                //   })
                //        .setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                builder.show();
            }
        });

        //   dialog.dismiss();
        // })
        //        .setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        //  builder.show();
        //  }
        // });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = currentUser.getUid();
        DatabaseReference alertContactsRef = FirebaseDatabase.getInstance().getReference("AlertContacts").child(currentUser.getUid());
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

        List<User> userList = new ArrayList<>();
        UserAdapter adapter = new UserAdapter( AlertContactsActivity.this, userList);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setAdapter(adapter);

        alertContactsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                userList.clear();

                for (DataSnapshot contactSnapshot : snapshot.getChildren()) {
                    String contactUid = contactSnapshot.getKey();  // UID of scanned user

                    usersRef.child(contactUid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot userSnapshot) {
                            User user = userSnapshot.getValue(User.class);
                            if (user != null) {
                                user.setId(contactUid);
                                userList.add(user);
                                adapter.notifyDataSetChanged();  // notify after adding each user
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.e("Error", "Failed to fetch user details");
                        }
                    });
                }

                if (!snapshot.hasChildren()) {
                    Toast.makeText(AlertContactsActivity.this, "No alert contacts added yet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(AlertContactsActivity.this, "Failed to load alert contacts", Toast.LENGTH_SHORT).show();
            }
        });

        //get AlertContacts ids
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .get()
                .addOnSuccessListener(result -> {
                    for (DocumentSnapshot doc : result) {
                        String uid = doc.getId();  // Har user ka UID
                        Log.d("UID", uid);
                    }
                })
                .addOnFailureListener(error -> {
                    Log.e("Error", error.getMessage());
                });

//        List<User> userList = new ArrayList<>();
//
//        User user = new User();
//       user.email = "Sameer2325@gmail.com";
//        user.name = "Sameer";
//
//        userList.add(user);
//
//        User user1 = new User();
//        user1.email = "Waleed2225@gmail.com";
//        user1.name = "Waleed";
//
//        userList.add(user1);
//        User user2 = new User();
//        user2.email = "Ahad1234@gmail.com";
//        user2.name = "Ahad";
//
//        userList.add(user2);
//
//
//
//        UserAdapter adapter = new UserAdapter(userList);
//        rvUser.setAdapter(adapter);

    }

}