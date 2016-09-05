package com.github.florent37.materialviewpager.subham;

/**
 * Created by my pc on 24-01-2016.
 */

public class ConfigUser {

    public static String[] username;
    public static String[] status;
    public static String[] image;
    public static String[] age;
    public static String[] location;
    public static String[] hobbies;
    public static String[] about;
    public static String[] checkfriend;


    public static final String TAG_USER_NAME = "username";
    public static final String TAG_STATUS = "status";
    public static final String TAG_IMAGE = "image";
    public static final String TAG_AGE = "age";
    public static final String TAG_LOCATION = "location";
    public static final String TAG_HOBBIES = "hobbies";
    public static final String TAG_ABOUT = "about";
    public static final String TAG_CHECKFRIEND = "checkfriend";

    public static final String TAG_JSON_ARRAY="result";

    public ConfigUser(int i){
        username = new String[i];
        status = new String[i];
        image = new String[i];
        age = new String[i];
        location=new String[i];
        hobbies=new String[i];
        about=new String[i];
        checkfriend=new String[i];

    }
}
