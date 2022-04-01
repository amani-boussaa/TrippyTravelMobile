package com.esprit.myapp.gui;

import com.codename1.io.Preferences;

public class SessionManager {

    public static Preferences pref ;

    private static int id ;
    private static String email;
    private static String passowrd ;

    public static Preferences getPref() {
        return pref;
    }

    public static void setPref(Preferences pref) {
        SessionManager.pref = pref;
    }

    public static int getId() {
        return pref.get("id",id);
    }

    public static void setId(int id) {
        pref.set("id",id);
    }

    public static String getEmail() {
        return pref.get("email",email);
    }

    public static void setEmail(String email) {
        pref.set("email",email);
    }

    public static String getPassowrd() {
        return pref.get("passowrd",passowrd);
    }

    public static void setPassowrd(String passowrd) {
        pref.set("passowrd",passowrd);
    }
}
