package com.android.bookmybook.util;

/**
 * Created by ajit on 6/1/15.
 */
public final class Constants{
    /*server properties*/
    public static final String SERVER_PROTOCOL = "http";
    public static final String SERVER_IP = "192.168.43.250";
    public static final String SERVER_PORT = "90";
    public static final String SERVER_PROJECT_DIRECTORY = "bmb";
    public static final String SERVER_ADDRESS = SERVER_PROTOCOL+"://"+SERVER_IP+":"+SERVER_PORT+"/"+SERVER_PROJECT_DIRECTORY+"/";
    public static final String SERVER_CHARSET = "UTF-8";

    //fragment keys
    public static final int GALLERY_CHOICE = 1111;
    public static final int CAMERA_CHOICE = 2222;

    /*Async Task Purpose Keys*/
    public static final String ASYNC_TASK_UPLOAD_FILES = "UPLOAD_FILES";
    public static final String ASYNC_TASK_GET_BOOKS_ALL = "GET_BOOKS_ALL";
    public static final String ASYNC_TASK_REGISTER_USER = "REGISTER_USER";


    //DB
    public static final String DB_NAME = "FINAPPL.db";
    public static final int DB_VERSION = 87;

    //DB flag value for affirmative/non affirmative
    public static final String DB_AFFIRMATIVE = "Y";
    public static final String DB_NONAFFIRMATIVE = "N";

    public static final String UI_FONT = "Roboto-Light.ttf";

    //DB Tables
    public static final String DB_TABLE_USER = "USERS";
    public static final String DB_TABLE_ACCOUNT = "ACCOUNTS";
    public static final String DB_TABLE_CATEGORY = "CATEGORIES";
    public static final String DB_TABLE_SPENTON = "SPENT_ONS";
    public static final String DB_TABLE_TRANSACTION = "TRANSACTIONS";
    public static final String DB_TABLE_BUDGET = "BUDGETS";
    public static final String DB_TABLE_TRANSFER = "TRANSFERS";
    public static final String DB_TABLE_REPEAT = "REPEATS";
    public static final String DB_TABLE_COUNTRY = "COUNTRIES";
    public static final String DB_TABLE_NOTIFICATION = "NOTIFICATIONS";
    public static final String DB_TABLE_SETTING = "SETTINGS";
    public static final String DB_TABLE_TAGS = "DB_TABLE_TAGS";
    //DB

    //Date formats
    public static final String JAVA_DATE_FORMAT = "dd-MM-yyyy";
    public static final String JAVA_DATE_FORMAT_1 = "MM-yyyy";
    public static final String DB_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DB_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DB_TIME_FORMAT = "HH:mm";
    public static final String UI_DATE_TIME_FORMAT = "d MMM ''yy H:mm:ss a";
    public static final String UI_DATE_FORMAT = "d MMM yyyy";
    public static final String UI_TIME_FORMAT = "hh:mm a";

    //Bundle Keys, Shared Prefs, Intent, fragment names
    //FRAGMENT NAMES
    public static final String FRAGMENT_SHARE_BOOK = "FRAGMENT_SHARE_BOOK";
    public static final String FRAGMENT_PICK_IMAGE = "FRAGMENT_PICK_IMAGE";

    //FRAGMENT OBJECT KEYS
    public static final String LOGGED_IN_USER = "LOGGED_IN_USER";

    public static final String BOOK = "BOOK";


    //SHARED PREFS KEYS
    public static final String SHARED_PREF = "SHARED_PREFERENCE";
    public static final String SHARED_PREF_ACTIVE_USER_ID = "ACTIVE_USER_ID";

    //Messages
    public static final String UN_IDENTIFIED_PARENT_FRAGMENT = "Target Fragment hasn't been set before calling the current fragment";
    public static final String UN_IDENTIFIED_OBJECT_TYPE = "Object Type could not be identified for the object : ";
    public static final String UN_IDENTIFIED_VIEW = "Could not identify the view which has been clicked";
    public static final String EMAIL_NOT_VERIFIED = "EMAIL NOT VERIFIED";
    public static final String VERIFICATION_EMAIL_SENT = " VERIFICATION MAIL SENT";

    //Snacks
    public static final String VERIFY_EMAIL = "VERIFY";
    public static final String OK = "OK";
    public static final String SAVED = "Saved";
    public static final String SOMETHING_WENT_WRONG = "Something Went Wrong";
}