package com.longj.androids23.Util.BluetoothUtil;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xp on 2017/12/8.
 *
 * 存储蓝牙设备信息
 */

public class BLEDevice implements Parcelable {

    String deviceName;//UUID 名称
    String mac;//mac地址
    int rssi;//信号强度

    //get函数


    public String getDeviceName() {
        return deviceName;
    }

    protected BLEDevice(Parcel in) {
        int[] intArr = new int[1];
        in.readIntArray(intArr);
        rssi = intArr[0];

        String[] strings = new String[3];
        in.readStringArray(strings);
        deviceName = strings[0];
        mac = strings[1];
    }

    public static final Creator<BLEDevice> CREATOR = new Creator<BLEDevice>() {
        @Override
        public BLEDevice createFromParcel(Parcel in) {
            return new BLEDevice(in);
        }

        @Override
        public BLEDevice[] newArray(int size) {
            return new BLEDevice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        int[] intArr = new int[1];
        dest.writeIntArray(intArr);
        rssi = intArr[0];

        String[] strings = new String[3];
        dest.writeStringArray(strings);
        deviceName = strings[0];
        mac = strings[1];
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        BLEDevice device = (BLEDevice) obj;
        return mac.equals(device.mac);
    }

    @Override
    public int hashCode() {
        return mac.hashCode();
    }

    @Override
    public String toString() {

        return "BLEDevice{" +
                "deviceName='" + deviceName + '\'' +
                ", mac='" + mac + '\'' +
                ", rssi=" + rssi +
                '}';
    }
}
