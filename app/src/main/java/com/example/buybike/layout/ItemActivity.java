package com.example.buybike.layout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.buybike.data.Bike;
import com.example.buybike.data.DataManager;
import com.example.buybike.R;

public class ItemActivity extends AppCompatActivity {
    protected Intent intent;
    protected String bikeName;
    protected int bikePrice;
    protected TextView nameText;
    protected TextView costText;
    protected String formattedPrice;
    protected Button addButton;
    protected View like;
    protected SharedPreferences preferences;
    protected boolean isLiked = false;
    protected Bike currentBike;
    protected ImageView imgItem;
    protected TextView saleCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item);

        bindViews();
        setEvents();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.item_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    void bindViews() {
        intent = getIntent();
        bikeName = intent.getStringExtra("bike_name");
        bikePrice = intent.getIntExtra("bike_price", 0);
        saleCost = findViewById(R.id.sale_cost);
        nameText = findViewById(R.id.name_item);
        costText = findViewById(R.id.cost_item);
        imgItem = findViewById(R.id.img_item);
        formattedPrice = getString(R.string.price_format, bikePrice);
        addButton = findViewById(R.id.add_button);
        like = findViewById(R.id.like);
        preferences = getSharedPreferences("liked_bikes", MODE_PRIVATE);
        currentBike = findBikeByName(bikeName);
        isLiked = DataManager.getInstance().getUser().isLikedBike(currentBike);
        setLikeIcon();
        setProductImage();
    }
    void setEvents() {
        addButton.setOnClickListener(v -> finish());
        like.setOnClickListener(v -> toggleLike());
        nameText.setText(bikeName);
        costText.setText(formattedPrice);
        costText.setPaintFlags(costText.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
        setSaleCost();
    }
    private void setSaleCost() {
        int salePrice = (int) (bikePrice *0.85);
        String saleFormattedPrice = getString(R.string.price_format, salePrice);
        saleCost.setText(saleFormattedPrice);
    }
    private void toggleLike() {
        isLiked = !isLiked;
        setLikeIcon();
        saveLikedState();
    }
    private Bike findBikeByName(String name) {
        return DataManager.getInstance().getBikes().stream()
                .filter(bike -> bike.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
    private void setLikeIcon() {
        if (isLiked) {
            like.setBackgroundResource(R.drawable.ic_red_heart);
        }else {
            like.setBackgroundResource(R.drawable.ic_heart);
        }
    }
    private void setProductImage() {
        if (bikeName != null){
            String imageName = bikeName.toLowerCase().replace(" ", "_");
            int imageResourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());

            if (imageResourceId != 0) {
                imgItem.setImageResource(imageResourceId);
            }else {
                imgItem.setImageResource(R.drawable.pinarello);
            }
        }
    }
    private void saveLikedState() {
        preferences.edit().putBoolean(bikeName, isLiked).apply();

        if(currentBike != null) {
            if (isLiked) {
                DataManager.getInstance().getUser().addLikedBike(currentBike);
            } else {
                DataManager.getInstance().getUser().removeLikedBike(currentBike);
            }
        }

        setResult(RESULT_OK);
    }
}