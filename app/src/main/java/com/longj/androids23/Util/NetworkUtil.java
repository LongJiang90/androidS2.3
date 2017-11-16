package com.longj.androids23.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by xp on 2017/11/14.
 */

public class NetworkUtil {

    public static String getSend(String url, String params) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlName = url+"?"+params;
            URL realUrl = new URL(urlName);
            //打开连接
            URLConnection urlCon = realUrl.openConnection();
            urlCon.setRequestProperty("accept", "*/*");
            urlCon.setRequestProperty("connection", "Keep-Alive");
            urlCon.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            urlCon.connect();

            Map<String, List<String>> map = urlCon.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" +line;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String postSend(String url, String params) {
        PrintWriter out = null;
        String result = "";
        BufferedReader in = null;
        try {
            String urlName = url+"?"+params;
            URL realUrl = new URL(urlName);
            //打开连接
            URLConnection urlCon = realUrl.openConnection();
            urlCon.setRequestProperty("accept", "*/*");
            urlCon.setRequestProperty("connection", "Keep-Alive");
            urlCon.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            //POST请求必须设置下面2行
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);

            out = new PrintWriter(urlCon.getOutputStream());
            out.print(params);//发送请求
            out.flush();// flush输出流的缓冲

            in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" +line;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
