package com.kazirahiv.httpserver.Util;

import java.net.URL;

public class URIValidator {
    public static boolean isValidURL(String url)
    {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
