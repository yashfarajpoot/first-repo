package com.faiza1.intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faiza1.intent.model.User;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkViewHolder>{

    List<User> links;

    public LinkAdapter(List<User> links) {

        this.links = links;
    }
    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_list_item, parent, false);
        return new LinkViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {

        User person = links.get(position);
        holder.tvEmail.setText(person.getEmail());
        holder.tvName.setText(person.getName());
    }

    @Override
    public int getItemCount() {
        return links.size();
    }
}


