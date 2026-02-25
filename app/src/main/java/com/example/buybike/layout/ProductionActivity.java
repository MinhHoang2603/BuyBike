package com.example.buybike.layout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.buybike.data.BikeAdapter;
import com.example.buybike.data.DataManager;
import com.example.buybike.R;

public class ProductionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private BikeAdapter bikeAdapter;
    private ActivityResultLauncher<Intent> launcher;
    private Button allItemButton;
    private Button roadBikeItemButton;
    private Button mountainBikeItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_production);

        bindView();
        setEvent();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.production_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    void bindView() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result != null) {
                bikeAdapter.notifyDataSetChanged();
            }
        });
        recyclerView = findViewById(R.id.product);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        bikeAdapter = new BikeAdapter(DataManager.getInstance().getBikes());
        recyclerView.setAdapter(bikeAdapter);
        allItemButton = findViewById(R.id.all_item);
        roadBikeItemButton = findViewById(R.id.road_bike_item);
        mountainBikeItemButton = findViewById(R.id.mountain_bike_item);
    }
    void setEvent() {
        allItemButton.setTextColor(getColor(R.color.red));

        bikeAdapter.setOnItemClickListener(bike -> {
            Intent intent = new Intent(ProductionActivity.this, ItemActivity.class);
            intent.putExtra("bike_name", bike.getName());
            intent.putExtra("bike_price", bike.getPrice());
            intent.putExtra("bike_type", bike.getType());
            launcher.launch(intent);
        });

        allItemButton.setOnClickListener(v -> {
            resetButtonColors();
            allItemButton.setTextColor(getColor(R.color.red));
            bikeAdapter.updateBikes(DataManager.getInstance().getBikes());
        });

        roadBikeItemButton.setOnClickListener(v -> {
            resetButtonColors();
            roadBikeItemButton.setTextColor(getColor(R.color.red));
            bikeAdapter.updateBikes(DataManager.getInstance().getBikesByType("RoadBike"));
        });

        mountainBikeItemButton.setOnClickListener(v -> {
            resetButtonColors();
            mountainBikeItemButton.setTextColor(getColor(R.color.red));
            bikeAdapter.updateBikes(DataManager.getInstance().getBikesByType("MountainBike"));
        });
    }

    private void resetButtonColors() {
        allItemButton.setTextColor(getColor(R.color.gray));
        roadBikeItemButton.setTextColor(getColor(R.color.gray));
        mountainBikeItemButton.setTextColor(getColor(R.color.gray));
    }

    @Override
    protected void onResume() {
        super.onResume();

        bikeAdapter.notifyDataSetChanged();
    }
}