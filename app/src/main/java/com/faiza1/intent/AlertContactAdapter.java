package com.faiza1.intent;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AlertContactAdapter extends RecyclerView.Adapter<AlertContactViewHolder> {

    List<AlertContact> alertContacts;

    Context context;

    public AlertContactAdapter(List<AlertContact> alertContacts) {
        this.alertContacts = alertContacts;
    }

    @NonNull
    @Override
    public AlertContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.alertcontact_list_item, parent, false);
        return new AlertContactViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertContactViewHolder holder, int position) {

        AlertContact alertcontact = alertContacts.get(position);
       Log.d("AlertContactAdapter", "Name: " + alertcontact.getName() + ", Number: " + alertcontact.getNumber());

        holder.tvNumber.setText(alertcontact.getNumber());
        holder.tvName.setText(alertcontact.getName());


        holder.deleteIcon.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Confirmation")
                    .setMessage("Are you sure you want to delete this?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, i) -> {
                        // Firebase delete logic here
                        String contactId = alertcontact.getId();
                        if (contactId != null) {
                            FirebaseDatabase.getInstance()
                                    .getReference("contacts")
                                    .child(contactId)
                                    .removeValue()
                                    .addOnSuccessListener(aVoid -> {
//                                        alertContacts.remove(position);
//                                        notifyDataSetChanged();
//                                        notifyItemRemoved(position);
//                                        notifyItemRangeChanged(position, alertContacts.size());
                                        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(context, "Failed to delete: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(context, "Invalid contact ID", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
                    })
                    .setNegativeButton("No", (dialog1, which) -> dialog1.dismiss());
            builder.show();
        });
    }



        @Override
        public int getItemCount () {

            return alertContacts.size();
        }
    }




