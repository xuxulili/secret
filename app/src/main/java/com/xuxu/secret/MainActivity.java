package com.xuxu.secret;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.xuxu.secret.Aty.AtyLogin;


public class MainActivity extends Activity {
       private Button login,getCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login = (Button) findViewById(R.id.btn_login);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               final  Intent intent;
//                intent = new Intent(MainActivity.this,AtyTimeLine.class);
//                startActivity(intent);
//            }
//        });
        String token = Config.getCachedToken(this);
        if(token!=null) {
            Intent intent = new Intent(this, AtyTimeLine.class);
            intent.putExtra(Config.KEY_TOKEN, token);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, AtyLogin.class);
            intent.putExtra(Config.KEY_TOKEN, token);
            startActivity(intent);
        }
    }
}
