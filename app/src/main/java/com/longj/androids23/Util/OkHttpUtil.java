package com.longj.androids23.Util;

import android.app.Activity;
import android.os.Handler;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xp on 2017/11/27.
 */

public class OkHttpUtil {

    static HttpUrl baseUrl = HttpUrl.parse("http://api.winowe.com/Server/QueryObjectOut");

    private static OkHttpUtil mInstance;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private Handler mHandler;
    private Gson mGson;

    private static final String TAG = "OkHttpUtil";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static OkHttpUtil getInstance() {
        if (mInstance == null){
            synchronized (OkHttpUtil.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtil();
                }
            }
        }
        return mInstance;
    }

    //同步请求
    public static String ok_SynGetSend(String url) throws IOException {
        HttpUrl link = baseUrl.resolve(url);
        Request request = new Request.Builder().url(link).build();
        Response response = okHttpClient.newCall(request).execute();

        return response.body().string();
    }

    public static String ok_SynPostSend(String url, Map<String,String> params) throws IOException {
        FormBody.Builder budiler = new FormBody.Builder();
        for (String key : params.keySet()) {
            budiler.add(key, params.get(key));
        }
        HttpUrl link = baseUrl.resolve(url);
        Request request = new Request.Builder().url(link).post(budiler.build()).build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }



    // 异步请求
    public static void ok_AsynGetSend(String url, Map<String,String> params, Callback responseCallback) {

        HttpUrl link = baseUrl.resolve(url);
        Request request = new Request.Builder().url(link).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(responseCallback);
    }

    public static void ok_AsynPostSend(String url, Map<String,String> params, Callback responseCallback) {
        FormBody.Builder budiler = new FormBody.Builder();
        for (String key : params.keySet()) {
            budiler.add(key, params.get(key));
        }
        HttpUrl link = baseUrl.resolve(url);
        Request request = new Request.Builder().url(link).post(budiler.build()).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(responseCallback);
    }

    //异步上传文件
    public static void ok_AsynUploadFile(String url, String path, String fileName, final MyCallBack mCallBack) {
        //判断文件类型
        MediaType mediaType = MediaType.parse(judgeType(path));
        //创建文件参数
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(mediaType)
                .addFormDataPart(mediaType.type(), fileName, RequestBody.create(mediaType, new File(path)));
        HttpUrl link = baseUrl.resolve(url);
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID" + "9199fdef135c122")
                .url(link)
                .post(builder.build())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mCallBack.failed(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }
    //根据文件路径判断文件类型
    private static String judgeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet0stream";
        }
        return contentTypeFor;
    }

    //异步下载文件
    public static void ok_AsynDownFile(String url, final String fileDir, final String fileName, final MyCallBack mCallBack) {
        HttpUrl link = baseUrl.resolve(url);
        final Request request = new Request.Builder().url(link).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //获取当前显示的Activity
                Activity curActivity = MyActivityManager.getInstance().getCurrentActivity();

                mCallBack.failed(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(fileDir, fileName);
                    fos = new FileOutputStream(file);
                    //计算进度
                    long totalSize = response.body().contentLength();
                    long downSum = 0;
                    int progress = 0;
                    while ((len = is.read(buf)) != -1) {
                        downSum += len;
                        progress = (int) (downSum * 1.0f/totalSize * 100);
                        fos.write(buf, 0, len);
                    }
                    fos.flush();

                    if (downSum == totalSize) {
                        mCallBack.suceed();
                    }
                    mCallBack.updating(progress, downSum, totalSize);

                } catch (IOException e) {

                } finally {

                    if (is != null) is.close();
                    if (fos != null) fos.close();
                }


            }
        });



    }



    public interface MyCallBack {
        void failed(Call call, IOException e);
        void updating(int progress, long downSize, long totalSize);
        void suceed();
    }


}
