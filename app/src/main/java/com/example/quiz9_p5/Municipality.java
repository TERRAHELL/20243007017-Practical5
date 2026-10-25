package com.example.quiz9_p5;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Municipality extends AppCompatActivity {

    // Shared Intent key
    public static final String EXTRA_MUNICIPALITY_NAME =
            "municipality_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Step 1: Load municipality layout
        setContentView(R.layout.activity_municipality);

        // Step 2: Receive municipality name
        String receivedName = getIntent().getStringExtra(
                EXTRA_MUNICIPALITY_NAME
        );

        // Step 3: Validate input
        TextProcessor processor = new TextProcessor();

        String validName =
                processor.processMunicipality(receivedName);

        // Step 4: Handle invalid data
        if (validName == null) {
            Toast.makeText(
                    this,
                    "Invalid municipality",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        // Step 5: Find UI components
        TextView tvName =
                findViewById(R.id.tvSelectedMunicipality);

        ImageView ivCity =
                findViewById(R.id.ivCity);

        TextView tvInfo =
                findViewById(R.id.tvCityInfo);

        Button btnVisitSite =
                findViewById(R.id.btnVisitSite);

        // Step 6: Prepare resource variables
        int imageResource;
        int colorResource;
        int informationIndex;

        // Step 7: Select municipality resources
        switch (validName) {

            case "Beijing":
                imageResource = R.drawable.beijing;
                colorResource = R.color.municipality_beijing;
                informationIndex = 0;
                break;

            case "Shanghai":
                imageResource = R.drawable.shanghai;
                colorResource = R.color.municipality_shanghai;
                informationIndex = 1;
                break;

            case "Chongqing":
                imageResource = R.drawable.chongqing;
                colorResource = R.color.municipality_chongqing;
                informationIndex = 2;
                break;

            case "Tianjin":
                imageResource = R.drawable.tianjin;
                colorResource = R.color.municipality_tianjin;
                informationIndex = 3;
                break;

            default:
                finish();
                return;
        }

        // Step 8: Get municipality colour
        int municipalityColor =
                ContextCompat.getColor(this, colorResource);

        // Step 9: Update city name and image
        tvName.setText(validName);
        tvName.setBackgroundColor(municipalityColor);

        ivCity.setImageResource(imageResource);
        ivCity.setContentDescription(validName);

        // Step 10: Read information array
        String[] histories =
                getResources().getStringArray(
                        R.array.municipalities_array
                );

        // Step 11: Display city information
        if (informationIndex < histories.length) {
            tvInfo.setText(histories[informationIndex]);
        }

        // Step 12: Set information text colour
        tvInfo.setTextColor(municipalityColor);

        // Step 13: Set button background colour
        btnVisitSite.setBackgroundTintList(
                ColorStateList.valueOf(municipalityColor)
        );

        // Step 14: Set readable text colours
        int textColorResource;

        if (validName.equals("Chongqing")) {
            textColorResource = R.color.color_text_dark;
        } else {
            textColorResource = R.color.color_text_light;
        }

        int textColor = ContextCompat.getColor(
                this,
                textColorResource
        );

        tvName.setTextColor(textColor);
        btnVisitSite.setTextColor(textColor);
    }
}
