package com.longj.androids23;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.longj.androids23.Util.DownloadFileUtil;

import java.util.Timer;
import java.util.TimerTask;

public class MultiThreadingActivity extends AppCompatActivity {

    EditText urlTextV;
    EditText target;
    Button downBtn;
    ProgressBar bar;
    DownloadFileUtil downloadFileUtil;
    private int mDownStatus;

    WebView webView;
    Button jsBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View view= this.getLayoutInflater().inflate(R.layout.activity_multi_threading,null);

        setContentView(view);

        urlTextV = (EditText) findViewById(R.id.url_edit_textV);
        target = (EditText) findViewById(R.id.path_edit_textV);
        downBtn = (Button) findViewById(R.id.download_btn);
        bar = (ProgressBar) findViewById(R.id.download_state_progress);

        webView = (WebView) findViewById(R.id.webview);
        jsBtn = (Button) findViewById(R.id.install_js_btn);

        urlTextV.setText("http://upload-images.jianshu.io/upload_images/1693266-8c1f4056ede72e37.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240");
        target.setText("/mnt/sdcard/123.png");

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x123) {
                    bar.setProgress(mDownStatus);
                }
            }
        };
        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFileUtil = new DownloadFileUtil(urlTextV.getText().toString(), target.getText().toString(), 6);

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            downloadFileUtil.downloadFile();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        final Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                double completeRate = downloadFileUtil.getCompleteRate();
                                mDownStatus = (int) (completeRate * 100);
                                handler.sendEmptyMessage(0x123);
                                if (mDownStatus >= 100) {
                                    timer.cancel();
                                    Toast.makeText(view.getContext(), "下载完成",3).show();
                                }
                            }
                        }, 0, 100);
                    }
                }.start();
            }
        });

        ///WebView中的JS交互
        webView.loadUrl("file:///android_asset/test.html");
        WebSettings webSettings = webView.getSettings();
        // 编码方式
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyObject(this), "myObj");

        jsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:funFromjs()");
            }
        });

    }


    public class MyObject {
        Context myContext;
        MyObject(Context c){
            myContext = c;
        }
        //将该方法暴露给JS调用
        @JavascriptInterface
        public void showToast(String name) {
            Toast.makeText(myContext, name +"你好", Toast.LENGTH_LONG).show();
        }
        @JavascriptInterface
        public void showList() {
            new AlertDialog.Builder(myContext)
                    .setTitle("图书列表")
                    .setIcon(R.drawable.image3)
                    .setItems(new  String[]{"学习安卓讲义","学习讲义", "轻量级Android实战"}, null)
                    .setPositiveButton("确定", null)
                    .create().show();
        }


    }





}
