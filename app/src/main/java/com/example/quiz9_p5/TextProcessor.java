package com.example.quiz9_p5;

/**
 * TextProcessor
 * EN: A regular Java class used to validate municipality names.
 */
public class TextProcessor {

    /**
     * EN: Validate and normalize the municipality name.
     * @param municipality User input.
     * @return Valid municipality name, or null if invalid.
     */
    public String processMunicipality(String municipality) {

        // Step 1: Check null or empty input
        if (municipality == null || municipality.trim().isEmpty()) {
            return null;
        }

        // Step 2: Remove leading and trailing whitespace
        String cleanInput = municipality.trim();

        // Step 3: Validate without case sensitivity

        // Beijing
        if (cleanInput.equalsIgnoreCase("Beijing")) {
            return "Beijing";
        }

        // Shanghai
        if (cleanInput.equalsIgnoreCase("Shanghai")) {
            return "Shanghai";
        }

        // Chongqing
        if (cleanInput.equalsIgnoreCase("Chongqing")) {
            return "Chongqing";
        }

        // Tianjin
        if (cleanInput.equalsIgnoreCase("Tianjin")) {
            return "Tianjin";
        }

        // Step 4: Return null for unsupported input
        return null;
    }
}
