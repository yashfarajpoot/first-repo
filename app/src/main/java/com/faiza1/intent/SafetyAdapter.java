package com.faiza1.intent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faiza1.intent.safetytip.TipDetailActivity;

import java.util.List;

public class SafetyAdapter extends RecyclerView.Adapter<SafetyAdapter.ViewHolder> {

    private Context context;
    private List<Safety> safetyList;

    public SafetyAdapter(Context context, List<Safety> safetyList) {
        this.context = context;
        this.safetyList = safetyList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button shareButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shareButton = itemView.findViewById(R.id.shareButton);
        }
    }

    @NonNull
    @Override
    public SafetyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.safetytips_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SafetyAdapter.ViewHolder holder, int position) {
        Safety safety = safetyList.get(position);
        holder.shareButton.setText(safety.getTitle());

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TipDetailActivity.class);
                intent.putExtra("id", safety.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return safetyList.size();
    }

}



