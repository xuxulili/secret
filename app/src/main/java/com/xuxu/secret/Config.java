package com.xuxu.secret;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/6/28.
 */
public class Config {
    public static final String SERVER_URL = "http://demo.eoeschool.com/api/v1/nimings/io";
    //	public static final String SERVER_URL = "http://10.0.0.52:8080/TestServer/api.jsp";

    public static final String APP_ID = "com.xuxu.secret";

    public static final String KEY_ACTION= "action";
    public static final String KEY_TOKEN= "token";
    public static final String KEY_PHONE_NUM = "phone";
    public static final String KEY_PHONE_MD5 = "phone_md5";
    public static final String KEY_STATUS = "status";
    public static final String KEY_CODE = "code";

    public static final int RESULT_STATUS_SUCCESS = 1;
    public static final int RESULT_STATUS_FAIL = 0;
    public static final int RESULT_STATUS_INVALID_TOKEN = 2;

    public static final String CHARSET = "utf_8";

    public static final String ACTION_GET_CODE ="send_pass" ;
    public static final String ACTION_LOGIN ="login" ;

    public static String  getCachedToken(Context context) {
        return context.getSharedPreferences(APP_ID, context.MODE_PRIVATE).getString(KEY_TOKEN, null);
    }
    public static void CachedToken(Context context,String token) {
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_TOKEN, token);
        e.commit();
    }

    public static void CachedPhoneNum(Context context,String phoneNum) {
        SharedPreferences.Editor share =context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).edit();
        share.putString(KEY_PHONE_NUM,phoneNum);
        share.commit();
    }
   public static String  getCachedPhoneNum(Context context) {
        return context.getSharedPreferences(APP_ID, context.MODE_PRIVATE).getString(KEY_PHONE_NUM, null);
    }
}
