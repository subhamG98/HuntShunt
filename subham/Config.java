package com.github.florent37.materialviewpager.subham;

/**
 * Created by my pc on 24-01-2016.
 */

public class Config {

    public static String[] title_friend;
    public static String[] fullname;
    public static String[] sex;
    public static String[] distance;
    public static String[] type;
    public static String[] image;



    public static final String GET_URL = "http://resolvefinance.co.uk/Whatsapp/getlist.php";

    public static final String TAG_USER_NAME = "username";
    public static final String TAG_FULL_NAME = "fullname";
    public static final String TAG_SEX = "sex";
    public static final String TAG_DISTANCE = "distance";
    public static final String TAG_TYPE = "type";
    public static final String TAG_IMAGE = "image";

    public static final String TAG_JSON_ARRAY="result";

    public Config(int i){
        title_friend = new String[i];
        fullname = new String[i];
        sex = new String[i];
        distance = new String[i];
        type=new String[i];
        image=new String[i];

    }
}
