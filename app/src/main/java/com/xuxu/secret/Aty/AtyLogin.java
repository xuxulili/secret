package com.xuxu.secret.Aty;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xuxu.secret.AtyTimeLine;
import com.xuxu.secret.Config;
import com.xuxu.secret.Net.GetCode;
import com.xuxu.secret.Net.Login;
import com.xuxu.secret.R;
import com.xuxu.secret.Tool.MD5Tool;

import java.net.CacheRequest;

/**
 * Created by Administrator on 2015/6/28.
 */
public class AtyLogin extends Activity {
    private EditText editPhoneText = null;
    private EditText editCodeText = null;
    private Button btn_getCode = null;
    private Button btn_login = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editPhoneText = (EditText) findViewById(R.id.edit_phone);
        editCodeText = (EditText) findViewById(R.id.edit_Code);
        btn_getCode = (Button) findViewById(R.id.btn_GetCode);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editPhoneText.getText())) {
                    Toast.makeText(AtyLogin.this, getString(R.string.phone_can_not_empty), Toast.LENGTH_LONG).show();
                    return;
                }
                final ProgressDialog progressDialog = ProgressDialog.show(AtyLogin.this, getResources().getString(R.string.progressDialog_tile), getResources().getString(R.string.progressDialog_message));
                new GetCode(editPhoneText.getText().toString(), new GetCode.SuccessCallback() {
                    @Override
                    public void onSuccess() {
                        progressDialog.dismiss();
                        Toast.makeText(AtyLogin.this, "请查看短信", Toast.LENGTH_LONG).show();
                    }
                }, new GetCode.FailCallback() {
                    @Override
                    public void onFail() {
                        progressDialog.dismiss();
                        Toast.makeText(AtyLogin.this, "获取验证码失败", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editPhoneText.getText())) {
                    Toast.makeText(AtyLogin.this, getString(R.string.phone_can_not_empty), Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(editCodeText.getText())) {
                    Toast.makeText(AtyLogin.this, getString(R.string.pleaseInputCode), Toast.LENGTH_LONG).show();
                    return;
                }

                final ProgressDialog pd = ProgressDialog.show(AtyLogin.this, getResources().getString(R.string.login_title), getResources().getString(R.string.login_content));
                new Login(MD5Tool.md5(editPhoneText.getText().toString()), editCodeText.getText().toString(), new Login.SuccessCallback() {
                    @Override
                    public void onSuccess(String token) {
                        pd.dismiss();//此句注意

                        Config.CachedToken(AtyLogin.this, token);
                        Config.CachedPhoneNum(AtyLogin.this, editPhoneText.getText().toString());

                        Intent intent = new Intent(AtyLogin.this, AtyTimeLine.class);
                        intent.putExtra(Config.KEY_PHONE_NUM, editPhoneText.getText().toString());
                        intent.putExtra(Config.KEY_TOKEN, token);
                        startActivity(intent);

                        finish();//此句注意
                    }
                }, new Login.FailCallback() {
                    @Override
                    public void onFail() {
                        pd.dismiss();//此句注意

                        Toast.makeText(AtyLogin.this, getString(R.string.FailLogin), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
