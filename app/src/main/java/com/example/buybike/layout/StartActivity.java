package com.example.buybike.layout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.buybike.data.DataManager;
import com.example.buybike.R;

public class StartActivity extends AppCompatActivity {
    protected Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);

        DataManager dataManager = DataManager.getInstance();

        bindView();
        setEvent();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.start_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    void bindView() {
        startButton = findViewById(R.id.start_button);
    }
    void setEvent() {
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductionActivity.class);
            startActivity(intent);
        });
    }
}