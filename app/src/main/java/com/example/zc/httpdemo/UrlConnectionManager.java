package com.example.zc.httpdemo;

import android.text.TextUtils;

import org.apache.http.NameValuePair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by zengli18 on 2017/11/12.
 */

public class UrlConnectionManager {

    public static HttpURLConnection getHttpUrlConnection(String url) throws IOException {

        HttpURLConnection httpURLConnection = null;
        URL mUrl = new URL(url);

        httpURLConnection = (HttpURLConnection) mUrl.openConnection();
        //设置连接时间
        httpURLConnection.setConnectTimeout(15000);
        //设置超时时间
        httpURLConnection.setReadTimeout(15000);
        //设置请求参数
        httpURLConnection.setRequestMethod("POST");
        //添加Header
        httpURLConnection.setRequestProperty("Connection","Keep-Alive");
        //接受输入流
        httpURLConnection.setDoInput(true);
        //传递参数是需要开启
        httpURLConnection.setDoOutput(true);

        return httpURLConnection;
    }


    public static void postParams(OutputStream outputStream, List<NameValuePair> paramsList) throws IOException {
        StringBuilder sb = new StringBuilder();

        for (NameValuePair nameValuePair : paramsList) {
            if (!TextUtils.isEmpty(sb)){
                sb.append("&");
            }

            sb.append(URLEncoder.encode(nameValuePair.getName(),"UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(nameValuePair.getValue(),"UTF-8"));
        }


        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
        bufferedWriter.write(sb.toString());
        bufferedWriter.flush();
        bufferedWriter.close();



    }








}
