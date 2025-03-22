package com.faiza1.intent;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.tvNumber.setText(alertcontact.number);
        holder.tvName.setText(alertcontact.name);


        holder.deleteIcon.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Confirmation")
                    .setMessage("Are you sure you want to delete this?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, i) -> {
                        alertContacts.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, alertContacts.size());
                        Toast.makeText(context,"Deleted successfully", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            builder.show();
        });
    }


        @Override
        public int getItemCount () {

            return alertContacts.size();
        }
    }




