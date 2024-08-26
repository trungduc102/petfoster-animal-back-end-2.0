package com.poly.petfoster.ultils;

import java.net.URL;

public class Validate {
    public static boolean isUrl(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }
}
