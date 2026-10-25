package com.example.quiz9_p5;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Municipality extends AppCompatActivity {

    // Shared Intent key
    public static final String EXTRA_MUNICIPALITY_NAME =
            "municipality_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Step 1: Load the second screen layout
        setContentView(R.layout.activity_municipality);

        // Step 2: Receive the municipality name
        String receivedName = getIntent().getStringExtra(
                EXTRA_MUNICIPALITY_NAME
        );

        // Step 3: Validate the received name
        TextProcessor processor = new TextProcessor();
        String validName =
                processor.processMunicipality(receivedName);

        // Step 4: Handle invalid data safely
        if (validName == null) {
            Toast.makeText(
                    this,
                    "Invalid municipality",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        // Step 5: Find the TextView
        TextView tvSelectedMunicipality =
                findViewById(R.id.tvSelectedMunicipality);

        // Step 6: Display the normalized city name
        tvSelectedMunicipality.setText(validName);
    }
}
