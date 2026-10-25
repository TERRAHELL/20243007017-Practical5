package com.example.quiz9_p5;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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

    // Prevent duplicate navigation
    private boolean isNavigating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Step 1: Initialize the main screen
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Step 2: Apply system window insets
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

        // Step 3: Find the input field
        etMunicipality = findViewById(R.id.etMunicipality);

        // Step 4: Listen for Go or Enter
        etMunicipality.setOnEditorActionListener(
                (textView, actionId, event) -> {

                    // Check soft keyboard Go
                    boolean isGo =
                            actionId == EditorInfo.IME_ACTION_GO;

                    // Check physical keyboard Enter
                    boolean isEnter =
                            event != null
                                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                                    && event.getAction() == KeyEvent.ACTION_DOWN;

                    if (isGo || isEnter) {

                        // Process the user input
                        handleMunicipalityInput();

                        return true;
                    }

                    return false;
                }
        );
    }

    /**
     * Process the input and open Municipality.
     */
    private void handleMunicipalityInput() {

        // Step 1: Prevent repeated navigation
        if (isNavigating) {
            return;
        }

        // Step 2: Read the input
        String input = etMunicipality.getText().toString();

        // Step 3: Validate the municipality
        String validName =
                processor.processMunicipality(input);

        // Step 4: Handle invalid input
        if (validName == null) {

            if (input.trim().isEmpty()) {

                // Empty input
                etMunicipality.setError(
                        getString(R.string.error_empty_input)
                );

            } else {

                // Unsupported municipality
                etMunicipality.setError(
                        getString(R.string.error_invalid_municipality)
                );
            }

            etMunicipality.requestFocus();
            return;
        }

        // Step 5: Mark navigation as started
        isNavigating = true;

        // Step 6: Hide the soft keyboard
        InputMethodManager imm =
                (InputMethodManager) getSystemService(
                        INPUT_METHOD_SERVICE
                );

        if (imm != null) {
            imm.hideSoftInputFromWindow(
                    etMunicipality.getWindowToken(),
                    0
            );
        }

        // Step 7: Clear input focus
        etMunicipality.clearFocus();

        // Step 8: Create an explicit Intent
        Intent intent = new Intent(
                MainActivity.this,
                Municipality.class
        );

        // Step 9: Pass the normalized name
        intent.putExtra(
                Municipality.EXTRA_MUNICIPALITY_NAME,
                validName
        );

        // Step 10: Open the second Activity
        startActivity(intent);
    }

    /**
     * Reset navigation when MainActivity resumes.
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Allow another municipality search
        isNavigating = false;
    }
}
