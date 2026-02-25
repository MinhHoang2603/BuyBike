package com.example.buybike.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buybike.R;

import java.util.List;

public class BikeAdapter extends RecyclerView.Adapter<BikeAdapter.BikeViewHolder>{
    private final List<Bike> bikes;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Bike bike);
    }

    public BikeAdapter(List<Bike> bikes) {
        this.bikes = bikes;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public BikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_chip, parent, false);
        return new BikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BikeViewHolder holder, int position) {
        Bike bike = bikes.get(position);
        holder.bikeName.setText(bike.getName());
        holder.bikePrice.setText(String.valueOf(bike.getPrice()));

        String imageName = bike.getName().toLowerCase().replace(" ", "_");
        int imageResourceId = holder.itemView.getContext().getResources()
                .getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());

        if (imageResourceId != 0) {
            holder.imageItem.setImageResource(imageResourceId);
        }else {
            holder.imageItem.setImageResource(R.drawable.pinarello);
        }

        updateLikeIcon(holder.likeIcon, bike);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(bike);
            }
        });

        holder.likeIcon.setOnClickListener(v -> {
            boolean isLiked = DataManager.getInstance().getUser().isLikedBike(bike);
            if (isLiked) {
                DataManager.getInstance().getUser().removeLikedBike(bike);
            } else {
                DataManager.getInstance().getUser().addLikedBike(bike);
            }

                updateLikeIcon(holder.likeIcon, bike);
        });
    }

    private void updateLikeIcon( View likeIcon, Bike bike) {
        boolean isLiked = DataManager.getInstance().getUser().isLikedBike(bike);
        if (isLiked) {
            likeIcon.setBackgroundResource(R.drawable.ic_red_heart);
        } else {
            likeIcon.setBackgroundResource(R.drawable.ic_heart);
        }
    }

    public void updateBikes(List<Bike> newBikes) {
        this.bikes.clear();
        this.bikes.addAll(newBikes);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return bikes.size();
    }

    public static class BikeViewHolder extends RecyclerView.ViewHolder {
        protected TextView bikeName;
        protected TextView bikePrice;
        protected View likeIcon;
        private final ImageView imageItem;

        public BikeViewHolder(@NonNull View itemView) {
            super(itemView);
            bikeName = itemView.findViewById(R.id.name_item);
            bikePrice = itemView.findViewById(R.id.cost_item);
            likeIcon = itemView.findViewById(R.id.like);
            imageItem = itemView.findViewById(R.id.img_item);
        }
    }
}
