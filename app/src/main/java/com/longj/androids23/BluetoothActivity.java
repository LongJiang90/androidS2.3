package com.longj.androids23;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.longj.androids23.Util.JLAdapter_ViewHolder.JLAdapter;
import com.longj.androids23.Util.JLAdapter_ViewHolder.JLViewHolder;
import com.longj.androids23.Util.PrintUtil.PrintUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
    List<String> deviceNames = new ArrayList<String>();
    BluetoothAdapter mBluetoothAdapter;
    JLAdapter<String> jlAdapter;

    //UUID的格式如下：
    //xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
    final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");//随便定义一个
    private BluetoothSocket clientSocket;
    private BluetoothDevice clientDevice;
    private OutputStream os;//输出流

    /**
     * 定义广播接收器
     */
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {

                    String name = device.getName() != null?device.getName():device.getAddress();
                    if (!deviceNames.contains(name)){
                        deviceNames.add(name);
                        devices.add(device);
                    }
//                    listView.deferNotifyDataSetChanged();
                    jlAdapter.notifyDataSetChanged();//更新适配器
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //已搜素完成
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        Button searchBtn = (Button) findViewById(R.id.search_devices_btn);
        listView = (ListView) findViewById(R.id.ble_devices_listV);



        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();
                }
                mBluetoothAdapter.startDiscovery();
            }
        });



        //开始主动搜索
        if (haveBLEPermission()) {

        }
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.enable();

        Set<BluetoothDevice> pairedDevs = mBluetoothAdapter.getBondedDevices();
        if (pairedDevs.size() >0) {
            for (BluetoothDevice device : pairedDevs) {
                deviceNames.add(device.getName());
                devices.add(device);
            }
        }
        jlAdapter = new JLAdapter<String>(this, deviceNames, R.layout.list_view_item) {
            @Override
            protected void convertView(View itemV, String obj, int position, int type) {
                TextView tv = JLViewHolder.get(itemV, R.id.textView);//使用通用ViewHolder
                tv.setText(obj);
            }

            @Override
            protected int type(int position) {
                return 0;
            }
        };

        listView.setAdapter(jlAdapter);
        //每搜索到一个设备就发送一个广播
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(receiver, filter);
        //搜索完成后发送广播
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(receiver, filter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice device = devices.get(position);
                String address = device.getAddress();

                try {
                    if (mBluetoothAdapter.isDiscovering()) {
                        mBluetoothAdapter.cancelDiscovery();
                    }

                    if (clientDevice == null) {
                        clientDevice = mBluetoothAdapter.getRemoteDevice(address);
                    }
                    if (clientSocket == null) {
                        clientSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                        clientSocket.connect();
                        os = clientSocket.getOutputStream();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (os != null) {
                    PrintUtils.setOutputStream(os);
                    printAllInfo();
                }
            }
        });

    }

    public void printAllInfo() {

        PrintUtils.selectCommand(PrintUtils.RESET);
        PrintUtils.selectCommand(PrintUtils.LINE_SPACING_DEFAULT);
        PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
        PrintUtils.printText("美食餐厅\n\n");
        PrintUtils.selectCommand(PrintUtils.DOUBLE_HEIGHT_WIDTH);
        PrintUtils.printText("桌号：1号桌\n\n");
        PrintUtils.selectCommand(PrintUtils.NORMAL);
        PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
        PrintUtils.printText(PrintUtils.printTwoData("订单编号", "201507161515\n"));
        PrintUtils.printText(PrintUtils.printTwoData("点菜时间", "2016-02-16 10:46\n"));
        PrintUtils.printText(PrintUtils.printTwoData("上菜时间", "2016-02-16 11:46\n"));
        PrintUtils.printText(PrintUtils.printTwoData("人数：2人", "收银员：张三\n"));

        PrintUtils.printText("--------------------------------\n");
        PrintUtils.selectCommand(PrintUtils.BOLD);
        PrintUtils.printText(PrintUtils.printThreeData("项目", "数量", "金额\n"));
        PrintUtils.printText("--------------------------------\n");
        PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
        PrintUtils.printText(PrintUtils.printThreeData("面", "1", "0.00\n"));
        PrintUtils.printText(PrintUtils.printThreeData("米饭", "1", "6.00\n"));
        PrintUtils.printText(PrintUtils.printThreeData("铁板烧", "1", "26.00\n"));
        PrintUtils.printText(PrintUtils.printThreeData("一个测试", "1", "226.00\n"));
        PrintUtils.printText(PrintUtils.printThreeData("牛肉面啊啊", "1", "2226.00\n"));
        PrintUtils.printText(PrintUtils.printThreeData("牛肉面啊啊啊牛肉面啊啊啊", "888", "98886.00\n"));

        PrintUtils.printText("--------------------------------\n");
        PrintUtils.printText(PrintUtils.printTwoData("合计", "53.50\n"));
        PrintUtils.printText(PrintUtils.printTwoData("抹零", "3.50\n"));
        PrintUtils.printText("--------------------------------\n");
        PrintUtils.printText(PrintUtils.printTwoData("应收", "50.00\n"));
        PrintUtils.printText("--------------------------------\n");

        PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
        PrintUtils.printText("备注：不要辣、不要香菜");
        PrintUtils.printText("\n\n\n\n\n");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(receiver);
    }

    public boolean haveBLEPermission() {
        boolean have = false;

        //判断系统版本
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePer = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkCallPhonePer != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 10);

                    have = true;
                }
            }
        }
        return have;
    }

}
