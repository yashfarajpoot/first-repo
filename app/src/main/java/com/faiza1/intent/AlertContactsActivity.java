package com.faiza1.intent;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class AlertContactsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_contacts);

        RecyclerView rvContacts = findViewById(R.id.rv_contacts);
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnAddMe = findViewById(R.id.btn_add_me);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                imageView.setPadding(10,10,10,80);
                imageView.setImageBitmap(bitmap);

                AlertDialog.Builder builder = new AlertDialog.Builder(AlertContactsActivity.this);
                builder.setTitle("Add Me")
                        .setMessage("Scan QR code from app to add")
                        .setCancelable(true)
                                .setView(imageView);
//                            .setPositiveButton("Yes", (dialog, i) -> {
//                                alertContacts.remove(position);
//                                notifyItemRemoved(position);
//                                notifyItemRangeChanged(position, alertContacts.size());
//                                Toast.makeText(context,"Deleted successfully", Toast.LENGTH_LONG).show();
//                                dialog.dismiss();
//                            })
//                            .setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                builder.show();
            }
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