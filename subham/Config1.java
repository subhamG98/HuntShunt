package com.github.florent37.materialviewpager.subham;

/**
 * Created by my pc on 24-01-2016.
 */

public class Config1 {

    public static String[] username;
    public static String[] message;
    public static String[] type;
    public static String[] link;




    public static final String TAG_USER_NAME = "username";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_TYPE = "type";
    public static final String TAG_LINK = "link";

    public static final String TAG_JSON_ARRAY="result";

    public Config1(int i){
        username = new String[i];
        message = new String[i];
        type = new String[i];
        link = new String[i];

    }
}
