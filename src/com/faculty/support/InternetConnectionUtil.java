package com.faculty.support;

import java.net.URL;
import java.net.URLConnection;

public class InternetConnectionUtil {
    public static boolean isConnected() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();

            conn.connect();
            conn.getInputStream().close();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
