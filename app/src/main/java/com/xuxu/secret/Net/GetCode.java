package com.xuxu.secret.Net;

import com.xuxu.secret.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/6/29.
 */
public class GetCode {
    public GetCode(String phone, final SuccessCallback successCallback, final FailCallback failCallback){
        new NetConnection(Config.SERVER_URL,HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    switch (jsonObj.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallback!=null) {
                                successCallback.onSuccess();
                            }
                            break;
                        default:
                            if (failCallback!=null) {
                                failCallback.onFail();
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                failCallback.onFail();
            }
        },Config.KEY_ACTION,Config.ACTION_GET_CODE,Config.KEY_PHONE_NUM,phone);//上传电话号码用于短信发送
    }
    public interface SuccessCallback{
        void onSuccess();
    }
    public interface FailCallback{
        void onFail();
    }
}
