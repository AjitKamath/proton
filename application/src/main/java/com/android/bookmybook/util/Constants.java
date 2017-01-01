package com.android.bookmybook.util;

/**
 * Created by ajit on 6/1/15.
 */
public final class Constants{
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

    //Bundle Keys, Sharde Prefs, Intent, fragment names
    //FRAGMENT NAMES
    public static final String FRAGMENT_ADD_UPDATE_TRANSACTION = "FRAGMENT_ADD_UPDATE_TRANSACTION";
    public static final String FRAGMENT_ADD_UPDATE_TRANSFER = "FRAGMENT_ADD_UPDATE_TRANSFER";
    public static final String FRAGMENT_ADD_UPDATE_BUDGET = "FRAGMENT_ADD_UPDATE_BUDGET";
    public static final String FRAGMENT_ADD_UPDATE_CATEGORY = "FRAGMENT_ADD_UPDATE_CATEGORY";
    public static final String FRAGMENT_ADD_UPDATE_ACCOUNT = "FRAGMENT_ADD_UPDATE_ACCOUNT";
    public static final String FRAGMENT_ADD_UPDATE_SPENTON = "FRAGMENT_ADD_UPDATE_SPENTON";

    public static final String FRAGMENT_SELECT_REPEAT = "FRAGMENT_SELECT_REPEAT";
    public static final String FRAGMENT_SELECT_AMOUNT = "FRAGMENT_SELECT_AMOUNT";
    public static final String FRAGMENT_SELECT_CATEGORY = "FRAGMENT_SELECT_CATEGORY";
    public static final String FRAGMENT_SELECT_ACCOUNT = "FRAGMENT_SELECT_ACCOUNT";
    public static final String FRAGMENT_SELECT_SPENTON = "FRAGMENT_SELECT_SPENTON";
    public static final String FRAGMENT_SELECT_IMAGE = "FRAGMENT_SELECT_IMAGE";
    public static final String FRAGMENT_SELECT_COUNTRIES = "FRAGMENT_SELECT_COUNTRIES";

    public static final String FRAGMENT_TRANSACTION_DETAILS = "FRAGMENT_TRANSACTION_DETAILS";
    public static final String FRAGMENT_TRANSFER_DETAILS = "FRAGMENT_TRANSFER_DETAILS";
    public static final String FRAGMENT_BUDGET_DETAILS = "FRAGMENT_BUDGET_DETAILS";

    public static final String FRAGMENT_LOGIN = "FRAGMENT_LOGIN";
    public static final String FRAGMENT_CONFIRM = "FRAGMENT_CONFIRM";
    public static final String FRAGMENT_DELETE_CONFIRM = "FRAGMENT_DELETE_CONFIRM";
    public static final String FRAGMENT_ADD_ACTIVITY = "FRAGMENT_ADD_ACTIVITY";
    public static final String FRAGMENT_OPTIONS = "FRAGMENT_OPTIONS";
    public static final String FRAGMENT_SETTINGS = "FRAGMENT_SETTINGS";
    public static final String FRAGMENT_CATEGORIES = "FRAGMENT_CATEGORIES";
    public static final String FRAGMENT_ACCOUNTS = "FRAGMENT_ACCOUNTS";
    public static final String FRAGMENT_SPENTONS = "FRAGMENT_SPENTONS";

    //FRAGMENT OBJECT KEYS
    public static final String CONFIRM_MESSAGE = "CONFIRM_MESSAGE";

    public static final String TRANSACTION_OBJECT = "TRANSACTION_OBJECT";
    public static final String TRANSFER_OBJECT = "TRANSFER_OBJECT";
    public static final String LOGGED_IN_OBJECT = "LOGGED_IN_OBJECT";
    public static final String SELECTED_DATE = "SELECTED_DATE";
    public static final String REPEAT_OBJECT = "REPEAT_OBJECT";
    public static final String AMOUNT_OBJECT = "AMOUNT_OBJECT";
    public static final String CATEGORY_OBJECT = "CATEGORY_OBJECT";
    public static final String ACCOUNT_OBJECT = "ACCOUNT_OBJECT";
    public static final String SPENTON_OBJECT = "SPENTON_OBJECT";
    public static final String COUNTRY_OBJECT = "COUNTRY_OBJECT";
    public static final String BUDGET_OBJECT = "BUDGET_OBJECT";
    public static final String IMAGE_OBJECT = "IMAGE_OBJECT";

    public static final String SELECTED_CATEGORY_OBJECT = "SELECTED_CATEGORY_OBJECT";
    public static final String SELECTED_ACCOUNT_OBJECT = "SELECTED_ACCOUNT_OBJECT";
    public static final String SELECTED_SPENTON_OBJECT = "SELECTED_SPENTON_OBJECT";
    public static final String SELECTED_REPEAT_OBJECT = "SELECTED_REPEAT_OBJECT";
    public static final String SELECTED_AMOUNT_OBJECT = "SELECTED_AMOUNT_OBJECT";
    public static final String SELECTED_COUNTRY_OBJECT = "SELECTED_COUNTRY_OBJECT";
    public static final String SELECTED_IMAGE_OBJECT = "SELECTED_IMAGE_OBJECT";
    public static final String SELECTED_GENERIC_OBJECT = "SELECTED_GENERIC_OBJECT";

    public static final String ACCOUNT_TYPE_FLAG = "ACCOUNT_TYPE_FLAG";

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