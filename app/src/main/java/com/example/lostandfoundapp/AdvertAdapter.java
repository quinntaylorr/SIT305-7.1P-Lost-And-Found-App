package com.example.lostandfoundapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.ViewHolder> {

    private List<Item> itemList;
    private Context context;

    public AdvertAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    // This creates a new row using your item_layout.xml
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    // This fills in the data for each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemName.setText(item.getPostType() + " " + item.getName());
        holder.timeStamp.setText(item.getTimestamp());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ItemDetail.class);
            intent.putExtra("item_id", item.getId());
            context.startActivity(intent);
        });
    }

    // This tells the RecyclerView how many items there are
    @Override
    public int getItemCount() {
        // Return the size of itemList
        return itemList.size();
    }

    // The ViewHolder holds references to the views in each row
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, timeStamp;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            timeStamp = itemView.findViewById(R.id.timeStamp);
        }
    }

    public void updateList(List<Item> newList) {
        itemList = newList;
        notifyDataSetChanged();
    }
}