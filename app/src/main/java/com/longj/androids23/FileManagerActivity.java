package com.longj.androids23;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class FileManagerActivity extends AppCompatActivity {

    String newFolderName;
    String folderPath;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);

        /*
        * 程序一旦在虚拟机或真机运行后，程序就会在固定地方创建一个文件夹。
        * 应用程序的数据文件默认位置保存在：/data/data/程序的包名/files 目录下。
        * */



        Button addFolderBtn = (Button) findViewById(R.id.add_folder_btn);
        Button addFileBtn = (Button) findViewById(R.id.add_file_btn);
        Button readFileBtn = (Button) findViewById(R.id.read_file_btn);

        final EditText folderNameEditTV = (EditText) findViewById(R.id.folder_name_editTextV);
        final EditText fileNameEditTV = (EditText) findViewById(R.id.file_name_editTextV);
        final EditText fileDetialEditTV = (EditText) findViewById(R.id.file_detial_editTextV);
        final TextView readTextV = (TextView) findViewById(R.id.read_textView);

        addFolderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String folderName = folderNameEditTV.getText().toString();
                newFolderName = folderName;
                File wordDir = getDir(folderName, MODE_PRIVATE);
                Log.i("123", "path:" + wordDir.getAbsolutePath());
                folderPath = wordDir.getAbsolutePath();
            }
        });
        /*
        mode指定打开文件的模式，该模式支持如下值：
        * MODE_PRIVATE ：该文件只能被当前程序读写。
        * MODE_APPEND：以追加方式打开该文件，应用程序可以向该文件中追加内容。
        * MODE_WORLD_READABLE：该文件的内容可以被其他应用程序读取。
        * MODE_WORLD_WRITEABLE：该文件的内容可由其他程序读、写。
        * */

        addFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = fileNameEditTV.getText().toString();
                String detial = fileDetialEditTV.getText().toString();
                fileName = fName;
                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(fName, Context.MODE_PRIVATE);
                    OutputStreamWriter osw = new OutputStreamWriter(outputStream, "UTF-8");
                    osw.write(detial);
                    //保证输出缓冲区的所有内容
                    osw.flush();
                    outputStream.flush();
                    //逐层向内关闭，后打开的先关闭
                    outputStream.close();
                    osw.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        readFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fis = openFileInput(fileName);
                    InputStreamReader is = new InputStreamReader(fis,"UTF-8");
                    //获取文件的长度
                    char input[] = new char[fis.available()];
                    is.read(input);
                    is.close();
                    fis.close();
                    String read = new String(input);
                    readTextV.setText("读取文件："+fileName + ",内容："+read);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });







    }



}
