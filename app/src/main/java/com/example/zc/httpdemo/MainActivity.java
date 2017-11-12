package com.example.zc.httpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //发送网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                //useHttpClientGet("http://www.baidu.com");
                useHttpClientPost("http://ip.taobao.com/instructions.php");
            }
        }).start();


    }


    //实例化HttpClient
    private HttpClient createHttpClient() {
        HttpParams mDefaultHttpParams = new BasicHttpParams();

        //设置连接超时
        HttpConnectionParams.setConnectionTimeout(mDefaultHttpParams, 15000);
        //设置请求超时
        HttpConnectionParams.setSoTimeout(mDefaultHttpParams, 15000);
        HttpConnectionParams.setTcpNoDelay(mDefaultHttpParams, true);

        HttpProtocolParams.setVersion(mDefaultHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(mDefaultHttpParams, HTTP.UTF_8);
        //持续握手
        HttpProtocolParams.setUseExpectContinue(mDefaultHttpParams, true);
        HttpClient mHttpClient = new DefaultHttpClient(mDefaultHttpParams);

        return mHttpClient;
    }

    //创建HttpGet和HttpClient,请求网络并得到HttpResponse并进行处理
    private void useHttpClientGet(String url) {
        HttpGet mHttpGet = new HttpGet(url);

        mHttpGet.addHeader("Connection", "Keep-Alive");

        HttpClient mHttpClinent = createHttpClient();
        try {
            HttpResponse mHttpResponse = mHttpClinent.execute(mHttpGet);
            HttpEntity mHttpResponseEntity = mHttpResponse.getEntity();
            int code = mHttpResponse.getStatusLine().getStatusCode();
            if (null != mHttpResponseEntity) {
                InputStream inputStream = mHttpResponseEntity.getContent();

                String response = converStreamToString(inputStream);
                Log.d("MainActivity", "请求状态码:" + code + "\n请求结果:\n" + response);
                inputStream.close();


            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void useHttpClientPost(String url) {
        HttpPost mHttpPost = new HttpPost(url);
        mHttpPost.addHeader("Connection","Keep-Alive");


        HttpClient httpClient = createHttpClient();
        List<NameValuePair> postParams = new ArrayList<>();
        //要传递的参数
        postParams.add(new BasicNameValuePair("ip","59.108.54.37"));
        try {
            mHttpPost.setEntity(new UrlEncodedFormEntity(postParams));
            HttpResponse httpResponse = httpClient.execute(mHttpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (null != httpEntity){
                InputStream inputStream = httpEntity.getContent();
                String response = converStreamToString(inputStream);
                Log.d("MainActivity", "请求状态码:" + statusCode + "\n请求结果:\n" + response);
                inputStream.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String converStreamToString(InputStream inputStream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");

        }

        String response = sb.toString();
        return response;


    }


}
