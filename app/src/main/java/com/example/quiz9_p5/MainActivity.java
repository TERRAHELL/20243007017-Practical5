package com.example.quiz9_p5;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Input field
    private EditText etMunicipality;

    // Input validator
    private final TextProcessor processor = new TextProcessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Step 1: Initialize UI
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Keep content clear of system bars
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        // Step 2: Find the input field
        etMunicipality = findViewById(R.id.etMunicipality);

        // Step 3: Listen for the Go or Enter key
        etMunicipality.setOnEditorActionListener(
                (textView, actionId, event) -> {

                    // Soft keyboard Go action
                    boolean isGo =
                            actionId == EditorInfo.IME_ACTION_GO;

                    // Physical keyboard Enter key
                    boolean isEnter =
                            event != null
                                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                                    && event.getAction() == KeyEvent.ACTION_DOWN;

                    if (isGo || isEnter) {
                        handleMunicipalityInput();
                        return true;
                    }

                    return false;
                }
        );
    }

    /**
     * Process input and navigate to Municipality.
     */
    private void handleMunicipalityInput() {

        // Step 4: Read user input
        String input = etMunicipality.getText().toString();

        // Step 5: Validate input
        String validName = processor.processMunicipality(input);

        // Step 6: Display errors for invalid input
        if (validName == null) {

            if (input.trim().isEmpty()) {
                etMunicipality.setError(
                        getString(R.string.error_empty_input)
                );
            } else {
                etMunicipality.setError(
                        getString(R.string.error_invalid_municipality)
                );
            }

            etMunicipality.requestFocus();
            return;
        }

        // Step 7: Create an explicit Intent
        Intent intent = new Intent(
                MainActivity.this,
                Municipality.class
        );

        // Step 8: Pass the normalized municipality name
        intent.putExtra(
                Municipality.EXTRA_MUNICIPALITY_NAME,
                validName
        );

        // Step 9: Start the second Activity
        startActivity(intent);
    }
}
