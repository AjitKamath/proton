package com.android.bookmybook.util;

/**
 * Created by ajit on 6/1/15.
 */
public final class Constants{
    /*server properties*/
    public static final String SLASH = "/";
    public static final String SERVER_PROTOCOL = "http";
    public static final String SERVER_IP = "192.168.43.250";
    public static final String SERVER_PORT = "90";
    public static final String SERVER_PROJECT_DIRECTORY = "bmb";
    public static final String SERVER_ADDRESS = SERVER_PROTOCOL+"://"+SERVER_IP+":"+SERVER_PORT+SLASH+SERVER_PROJECT_DIRECTORY;
    public static final String SERVER_CHARSET = "UTF-8";

    //PHP
    public static final String PHP_FETCH_ALL_CATEGORIES = "fetch_cat.php";
    public static final String PHP_FETCH_ALL_TENURES = "fetch_tenure.php";
    public static final String PHP_POST_BOOK = "imupload.php";

    //fragment keys
    public static final int GALLERY_CHOICE = 1111;
    public static final int CAMERA_CHOICE = 2222;

    /*Async Task Purpose Keys*/
    public static final String ASYNC_TASK_UPLOAD_FILES = "UPLOAD_FILES";
    public static final String ASYNC_TASK_GET_BOOKS_ALL = "GET_BOOKS_ALL";
    public static final String ASYNC_TASK_REGISTER_USER = "REGISTER_USER";
    public static final String ASYNC_TASK_GET_CATEGORIES_ALL = "GET_CATEGORIES_ALL";
    public static final String ASYNC_TASK_GET_TENURES_ALL = "GET_TENURES_ALL";


    //DB
    public static final String DB_NAME = "FINAPPL.db";
    public static final int DB_VERSION = 87;

    //flag value for affirmative/non affirmative
    public static final String AFFIRMATIVE = "Y";
    public static final String NON_AFFIRMATIVE = "N";

    public static final String UI_FONT = "Roboto-Light.ttf";

    //Bundle Keys, Shared Prefs, Intent, fragment names
    //FRAGMENT NAMES
    public static final String FRAGMENT_SHARE_BOOK = "FRAGMENT_SHARE_BOOK";
    public static final String FRAGMENT_PICK_IMAGE = "FRAGMENT_PICK_IMAGE";
    public static final String FRAGMENT_NO_INTERNET = "FRAGMENT_NO_INTERNET";
    public static final String FRAGMENT_COMMON_SPINNER = "FRAGMENT_COMMON_SPINNER";
    public static final String FRAGMENT_LOGIN = "FRAGMENT_LOGIN";
    public static final String FRAGMENT_REGISTRATION = "FRAGMENT_REGISTRATION";

    //FRAGMENT OBJECT KEYS
    public static final String LOGGED_IN_USER = "LOGGED_IN_USER";
    public static final String BOOK = "BOOK";
    public static final String MASTER = "MASTER";
    public static final String LIST_DATA = "LIST_DATA";
    public static final String SELECTED_ITEM = "SELECTED_ITEM";
    public static final String MIN_DURATION = "MIN_DURATION";
    public static final String MAX_DURATION = "MAX_DURATION";
    public static final String DURATION_TYPE = "DURATION_TYPE";
    public static final String CATEGORY = "CATEGORY";

    //check master keys
    public static final String CHECK_MASTER_FOR_CATEGORIES = "CHECK_MASTER_FOR_CATEGORIES";
    public static final String CHECK_MASTER_FOR_TENURES = "CHECK_MASTER_FOR_TENURES";
    public static final String CHECK_MASTER_FOR_ALL = "CHECK_MASTER_FOR_ALL";


    //SHARED PREFS KEYS
    public static final String SHARED_PREF_FILE_NAME = "BMB_PREFS";
    public static final String SHARED_PREF_ACTIVE_USER_ID = "ACTIVE_USER_ID";

    //Messages
    public static final String UN_IDENTIFIED_PARENT_FRAGMENT = "Target Fragment hasn't been set before calling the current fragment";
    public static final String UN_IDENTIFIED_OBJECT_TYPE = "Object Type could not be identified for the object : ";
    public static final String UN_IDENTIFIED_VIEW = "Could not identify the view which has been clicked";

    //Snacks
    public static final String VERIFY_EMAIL = "VERIFY";
    public static final String OK = "OK";
    public static final String SAVED = "Saved";
    public static final String SOMETHING_WENT_WRONG = "Something Went Wrong";

    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int REQUEST_GALLERY_PHOTO = 2;
}
