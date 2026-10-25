
package com.example.quiz9_p5;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
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

        // Step 1: Load the layout
        setContentView(R.layout.activity_municipality);

        // Step 2: Receive municipality name
        String receivedName = getIntent().getStringExtra(
                EXTRA_MUNICIPALITY_NAME
        );

        // Step 3: Validate the name
        TextProcessor processor = new TextProcessor();

        String validName =
                processor.processMunicipality(receivedName);

        // Step 4: Handle invalid input
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

        // Website URL
        String websiteUrl;

        // Step 7: Select resources and website
        switch (validName) {

            case "Beijing":
                imageResource = R.drawable.beijing;
                colorResource = R.color.municipality_beijing;
                informationIndex = 0;

                websiteUrl =
                        "https://english.beijing.gov.cn/";
                break;

            case "Shanghai":
                imageResource = R.drawable.shanghai;
                colorResource = R.color.municipality_shanghai;
                informationIndex = 1;

                websiteUrl =
                        "https://english.shanghai.gov.cn/";
                break;

            case "Chongqing":
                imageResource = R.drawable.chongqing;
                colorResource = R.color.municipality_chongqing;
                informationIndex = 2;

                websiteUrl =
                        "https://english.cq.gov.cn/";
                break;

            case "Tianjin":
                imageResource = R.drawable.tianjin;
                colorResource = R.color.municipality_tianjin;
                informationIndex = 3;

                websiteUrl =
                        "https://en.tj.gov.cn/";
                break;

            default:
                finish();
                return;
        }

        // Step 8: Get municipality theme colour
        int municipalityColor =
                ContextCompat.getColor(this, colorResource);

        // Step 9: Update title and image
        tvName.setText(validName);
        tvName.setBackgroundColor(municipalityColor);

        ivCity.setImageResource(imageResource);
        ivCity.setContentDescription(validName);

        // Step 10: Read information array
        String[] histories =
                getResources().getStringArray(
                        R.array.municipalities_array
                );

        // Step 11: Display municipality information
        if (informationIndex < histories.length) {
            tvInfo.setText(histories[informationIndex]);
        }

        // Step 12: Apply theme colours
        tvInfo.setTextColor(municipalityColor);

        btnVisitSite.setBackgroundTintList(
                ColorStateList.valueOf(municipalityColor)
        );

        // Step 13: Set readable text colours
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

        // ============================================
        // Step 14: Implement implicit Intent
        // ============================================

        btnVisitSite.setOnClickListener(v -> {

            // Open the official website
            openOfficialWebsite(websiteUrl);

        });
    }

    /**
     * Open the municipality's official website.
     *
     * @param url Official website URL
     */
    private void openOfficialWebsite(String url) {

        // Step 1: Convert URL string to Uri
        Uri webpage = Uri.parse(url);

        // Step 2: Create an implicit Intent
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                webpage
        );

        // Step 3: Ask Android to open the website
        try {
            startActivity(browserIntent);

        } catch (ActivityNotFoundException e) {

            // Step 4: Handle missing browser application
            Toast.makeText(
                    this,
                    "No application available to open the website",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
