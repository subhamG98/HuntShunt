package com.github.florent37.materialviewpager.subham;

/**
 * Created by my pc on 24-01-2016.
 */

public class Config3 {

    public static String[] sender;
    public static String[] receiver;
    public static String[] message;
    public static String[] image;
    public static String[] type;




    public static final String TAG_SENDER = "sender";
    public static final String TAG_RECEIVER = "receiver";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_IMAGE = "image";
    public static final String TAG_TYPE = "type";

    public static final String TAG_JSON_ARRAY="result";

    public Config3(int i){
        sender = new String[i];
        receiver = new String[i];
        message = new String[i];
        image = new String[i];
        type=new String[i];

    }
}
