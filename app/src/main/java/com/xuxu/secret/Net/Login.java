package com.xuxu.secret.Net;

import com.xuxu.secret.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/7/1.
 */
public class Login {
    public Login(String phone_md5,String code,final SuccessCallback successCallback,final FailCallback failCallback) {
        new NetConnection(Config.SERVER_URL,HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    /*
                    * 登录成功获取TOKEN，并且传入接口*/
                    switch (object.getInt(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallback!=null) {
                                successCallback.onSuccess(object.getString(Config.KEY_TOKEN));
                            }
                            break;
                        case Config.RESULT_STATUS_FAIL:
                            if (failCallback!=null) {
                                failCallback.onFail();
                            }
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new NetConnection.FailCallback() {
            @Override
            public void onFail() {

            }
        },Config.KEY_ACTION,Config.ACTION_LOGIN,Config.KEY_PHONE_MD5,phone_md5,Config.KEY_CODE,code);
    }
    public interface SuccessCallback{
        void onSuccess(String token);
    }
    public interface FailCallback{
        void onFail();
    }
}
