package com.faiza1.intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkViewHolder>{

    List<Link> Links;

    public LinkAdapter(List<Link> links){

        this.Links = links ;
    }
    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_list_item, parent, false);
        return new LinkViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {

         Link person =  Links.get(position);
        holder.tvEmail.setText(person.email);
        holder.tvName.setText(person.name);
    }

    @Override
    public int getItemCount() {
        return  Links.size();
    }
}


