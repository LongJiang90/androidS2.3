package com.longj.androids23.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by xp on 2017/11/14.
 */

public class DownloadFileUtil {
    private String path;
    private String targetFile;
    private int threadNum;
    private DownThread[] threads;
    private int fileSize;

    public DownloadFileUtil(String path, String targetFile, int threadNum) {
        this.path = path;
        this.targetFile = targetFile;
        this.threadNum = threadNum;
        //初始化下载线程对象数组
        threads = new DownThread[threadNum];
    }

    public void downloadFile() throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5*1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg,"
                + "application/x-shockwave-flash, application/xaml+xml"
                + "application/vnd.ms-xpsdocument, application/x-ms-xbap"
                + "application/x-ms-application, application/vnd.ms-excel"
                + "application/vnd.ms-powerpoint, application/msword, */*");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Connection", "Keep-Alive");
        fileSize = conn.getContentLength();
        int currentPartSize = fileSize / threadNum +1;
        RandomAccessFile file = new RandomAccessFile(targetFile, "rw");
        file.setLength(fileSize);
        file.close();
        for (int i=0; i<threadNum; i++) {
            //计算每条线程下载的开始位置
            int startPos = i* currentPartSize;
            //每条线程使用一个file进行下载
            RandomAccessFile currentFile = new RandomAccessFile(targetFile,"rw");
            currentFile.seek(startPos);
            threads[i] = new DownThread(startPos, currentPartSize, currentFile);
            threads[i].start();
        }


    }

    public double getCompleteRate() {
        int sumSize = 0;
        for (int i=0; i<threadNum; i++) {
            sumSize += threads[i].length;
        }
        return sumSize * 1.0 / fileSize;
    }

    private class DownThread extends Thread {
        int startPos;
        int currentPartSize;
        RandomAccessFile currentPart;
        int length;
        public DownThread(int startPos, int currentPartSize, RandomAccessFile currentPart) {
            this.startPos = startPos;
            this.currentPartSize = currentPartSize;
            this.currentPart = currentPart;
        }

        @Override
        public void run() {
            super.run();
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5*1000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg,"
                        + "application/x-shockwave-flash, application/xaml+xml"
                        + "application/vnd.ms-xpsdocument, application/x-ms-xbap"
                        + "application/x-ms-application, application/vnd.ms-excel"
                        + "application/vnd.ms-powerpoint, application/msword, */*");
                conn.setRequestProperty("Accept-Language", "zh-CN");
                conn.setRequestProperty("Charset", "UTF-8");

                InputStream inStream = conn.getInputStream();
                skipFully(inStream, this.startPos);

                byte[] buffer = new byte[1024];
                int hasRead = 0;
                while (length < currentPartSize && (hasRead = inStream.read(buffer)) > 0) {
                    currentPart.write(buffer, 0, hasRead);
                    length += hasRead;
                }
                currentPart.close();
                inStream.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        public void skipFully(InputStream in, long bytes) throws IOException {
            long remainning = bytes;
            long len = 0;
            while (remainning>0) {
                len = in.skip(remainning);
                remainning -= len;
            }
        }

    }
}
