package com.longj.androids23.Util.BluetoothUtil;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.longj.androids23.Util.OkHttpUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by xp on 2017/12/8.
 *
 * 蓝牙控制类. 使用这一个类连接蓝牙设备的时候，最好在连接之前扫描一下附件的设备，
 * 如果能够扫描得到才进行连接，降低连接蓝牙的出错率。
 *
 */
public class BluetoothUtil {

    @SuppressLint("StaticFieldLeak")
    private static volatile BluetoothUtil mInstance;

    private final static String TAG = "BluetoothLe";

    private final Context mContext;

    private static Handler mBluetoothWorker;

    private BluetoothAdapter mBluetoothAdapter;

    private BluetoothManager mBluetoothManager;

    private Map<String, BLEConnector> mGattConnectorMap = new ConcurrentHashMap<>();

    private BLESearcher mBluetoothSearcher;

    /*
    * 蓝牙接收广播
    * */
    BroadcastReceiver searchDevices = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle b = intent.getExtras();
            Object[] lsNames = b.keySet().toArray();

            for (int i=0; i<lsNames.length; i++) {
                String key = lsNames[i].toString();
                Log.e(TAG, key + ">>>" +String.valueOf(b.get(key)));
            }

            //在下面的状态改变中回调自己回调
            BluetoothDevice device;
            if (BluetoothDevice.ACTION_FOUND.equals(action)) { //搜索发现设备，可能发现同一设备
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                onRegisterBltReceiver.onBluetoothDevice(device);

            }else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) { //状态改变时
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING://正在配对
                        Log.d("BlueToothTestActivity", "正在配对......");
//                        onRegisterBltReceiver.onBltIng(device);
                        break;
                    case BluetoothDevice.BOND_BONDED://配对结束
                        Log.d("BlueToothTestActivity", "完成配对");
//                        onRegisterBltReceiver.onBltEnd(device);
                        break;
                    case BluetoothDevice.BOND_NONE://取消配对/未配对
                        Log.d("BlueToothTestActivity", "取消配对");
//                        onRegisterBltReceiver.onBltNone(device);
                    default:
                        break;
                }

            }

        }
    };


    private BluetoothUtil(Context context) {
        mContext = context.getApplicationContext();

        HandlerThread thread = new HandlerThread("bluetooth worker");
        thread.start();
        mBluetoothWorker = new Handler(thread.getLooper());
    }

    public static BluetoothUtil getInstance(Context mContext) {
        if (mInstance == null){
            synchronized (OkHttpUtil.class) {
                if (mInstance == null) {
                    mInstance = new BluetoothUtil(mContext);
                }
            }
        }
        return mInstance;
    }

    //初始化
    private boolean initialize() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);//搜索发现设备
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);//状态改变
        intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);//行动扫描模式改变了
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);//动作状态发生了变化
        mContext.registerReceiver(searchDevices, intentFilter);




        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = mBluetoothManager.getAdapter();
            if (mBluetoothAdapter == null) {
                Log.e(TAG, "Unable to initialize BluetoothAdapter.");
                return false;
            }
        }

        return mBluetoothAdapter.isEnabled() || mBluetoothAdapter.enable();
    }

    //获取Search服务
    public BLESearcher getBluetoothSearcher() {
        if (mBluetoothSearcher == null) {
            synchronized (BluetoothUtil.class) {
                if (mBluetoothSearcher == null) {
                    if (mBluetoothAdapter == null) {
                        String err = "cannot create BluetoothLeSearcher instance because not " +
                                "initialize, please call initialize() method";
                        Log.e(TAG, err);
                        return null;
                    }
                    mBluetoothSearcher = new BLESearcher(mContext, mBluetoothAdapter, mBluetoothWorker);
                }
            }
        }

        return mBluetoothSearcher;
    }

    public BLEConnector getBluetoothLeConnector(String mac) {
        BLEConnector result;
        if ((result = mGattConnectorMap.get(mac)) != null) {
            return result;
        }

        result = new BLEConnector(mContext, mBluetoothAdapter, mac, mBluetoothWorker);
        mGattConnectorMap.put(mac, result);
        return result;
    }

    public void cleanConnector(String mac) {
        BLEConnector result;
        if ((result = mGattConnectorMap.get(mac)) != null) {
            mGattConnectorMap.remove(mac);
            result.disconnect();
            result.setOnDataAvailableListener(null);
        }
    }

    /**
     * 判断是否支持蓝牙，并打开蓝牙
     * 获取到BluetoothAdapter之后，还需要判断是否支持蓝牙，以及蓝牙是否打开。
     * 如果没打开，需要让用户打开蓝牙：
     */
    public void checkBleDevice(Context context) {
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(enableBtIntent);
            }
        } else {
            Log.i("blueTooth", "该手机不支持蓝牙");
        }
    }

    public void unregisterReceiver(Context context) {
        context.unregisterReceiver(searchDevices);
        if (mBluetoothAdapter != null)
            mBluetoothAdapter.cancelDiscovery();
    }

    //操作函数
    /**
     * 打开蓝牙扫描操作. 如果此时正在扫描将会抛出正在扫描 {@link BluetoothSearchConflictException} 错误。
     * 如果想强制中断当前扫描操作，set cancel value to true.
     *
     * @param millis 扫描时间
     * @param cancel 如果正在进行扫描操作，设置是否中断当前扫描。true 中断当前扫描操作，
     *               false 如果当前正在进行扫描操作则会抛出 {@link BluetoothSearchConflictException} 错误
     * @return 扫描结果的列表（无重复设备）
     */
    ArrayList<BLEDevice> search(int millis, boolean cancel) {
        checkBleDevice(mContext);
        if (mBluetoothAdapter.isDiscovering()) {
            stopSearch();
        }

        mBluetoothAdapter.startDiscovery();

        return new ArrayList<>();
    }

    void stopSearch() {
        if (mBluetoothAdapter != null)
            getBluetoothSearcher().stopScanLeDevice();
    }

    /**
     * 连接一台蓝牙设备. 连接的蓝牙设备有最大限制，
     * 如果超出这一个数量，即使连接上了蓝牙设备也扫描不到该设备的服务通道
     *
     * @param mac 需要连接蓝牙设备的地址
     * @return 成功，返回连接设备的地址
     */
    Observable<String> connect(String mac) {
        return null;
    }

    /**
     * 断开蓝牙连接, 释放蓝牙连接占用的蓝牙服务
     *
     * @param mac 需要断开连接的 mac 地址
     */
    void disconnect(String mac) {
        getBluetoothLeConnector(mac).disconnect();
    }

    /**
     * 向一个蓝牙设备写入值
     *
     * @param mac            设备 mac 地址
     * @param service        设备服务地址
     * @param characteristic 设备 characteristic 地址
     * @param values         需要写入的值
     * @return 写入成功返回
     */
    Observable<String> write(String mac, UUID service, UUID characteristic, byte[] values) {
        return null;
    }

    /**
     * 向蓝牙设备注册一个通道值改变的监听器,
     * 每一个设备的每一个通道只允许同时存在一个监听器。
     *
     * @param mac            需要监听的 mac 地址
     * @param service        需要监听的设备的服务地址
     * @param characteristic 需要监听设备的 characteristic
     * @param callback       需要注册的监听器
     * @return 成功或失败返回
     */
    Observable<String> registerNotify(String mac, UUID service, UUID characteristic,
                                      BluetoothUtil.BaseResultCallback<byte[]> callback) {
        return null;
    }

    /**
     * 解除在对应设备对应通道注册了的监听器
     *
     * @param mac            需要监听的 mac 地址
     * @param service        需要监听的设备的服务地址
     * @param characteristic 需要监听设备的 characteristic
     */
    Observable<String> unRegisterNotify(String mac, UUID service, UUID characteristic) {
        return null;
    }

    /**
     * 清空对应 MAC 地址的蓝牙设备缓存
     *
     * @param mac 蓝牙设备硬件地址
     */
    void clean(String mac) {
        cleanConnector(mac);
    }

    /**
     * 清空所有缓存的蓝牙设备
     */
    void cleanAll() {
        cleanAllConnector();
    }

    /**
     * 启动蓝牙
     */
    void openBluetooth() {
        initialize();
    }

    /**
     * 关闭蓝牙
     */
    void closeBluetooth() {
        mInstance.closeBluetooth();
    }



    /**
     * 在不在需要连接蓝牙设备的时候，
     * 或者生命周期暂停的时候调用这一个方法
     */
    public void cleanAllConnector() {
        for (String mac : mGattConnectorMap.keySet()) {
            cleanConnector(mac);
        }
    }

    public interface BaseResultCallback<D> {

        /**
         * 成功拿到数据
         *
         * @param data 回传的数据
         */
        void onSuccess(D data);

        /**
         * 操作失败
         *
         * @param msg 失败的返回的异常信息
         */
        void onFail(String msg);
    }

    public abstract class SuccessResultCallback<D> implements BaseResultCallback<D> {

        private BaseResultCallback errorCallback;

        public SuccessResultCallback(BaseResultCallback errorCallback) {
            this.errorCallback = errorCallback;
        }

        @Override
        public void onSuccess(D data) {
            errorCallback.onSuccess(data);
        }

        @Override
        public void onFail(String msg) {
            errorCallback.onFail(msg);
        }
    }
}


