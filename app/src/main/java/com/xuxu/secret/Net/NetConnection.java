package com.xuxu.secret.Net;


import android.os.AsyncTask;

import com.xuxu.secret.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import static com.xuxu.secret.Net.HttpMethod.*;

/**
 * Created by Administrator on 2015/6/28.
 */
public class NetConnection {
    public NetConnection(final String url, final HttpMethod method, final SuccessCallback successCallback, final FailCallback failCallback, final String ... kvs) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                /*
                * 遍历需要上传的网络请求参数*/
                StringBuffer param=null;//Stringbuffer有append方法
                for(int i=0;i<=kvs.length;i+=2) {
                    param.append(kvs[i]).append("=").append(kvs[i + 1]).append("&");
                }

                URLConnection urlConnection=null;
                InputStream inputStream=null;
                switch(method){
                    case POST:
                        try {
                            urlConnection=new URL(url).openConnection();
                            BufferedWriter br=new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
                            br.write(param.toString());//写入本地缓存
                            br.flush();//传入服务器,（flush：冲洗）
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case GET:
                        try {
                            urlConnection=new URL(url+"?"+param.toString()).openConnection();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                StringBuffer result=null;
                String line=null;
                try {
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), Config.CHARSET));
                    //此处注意把一个StringBuffer传入外部与写入StringBuffer的区别
                    while ((line=bufferedReader.readLine())!=null){
                        result.append(line);
                    }
                    return result.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;//注意此处返回null
            }

            @Override
            protected void onPostExecute(String result) {
                if(result!=null){
                    if(successCallback!=null){
                        successCallback.onSuccess(result);
                    }
                }else {
                    failCallback.onFail();
                }
                super.onPostExecute(result);//最后调用

            }
        }.execute();

    }
    public interface SuccessCallback{
        void onSuccess(String result);
    }
    public interface FailCallback{
        void onFail();
    }
}
