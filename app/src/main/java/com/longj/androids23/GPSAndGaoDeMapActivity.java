package com.longj.androids23;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Locale;

public class GPSAndGaoDeMapActivity extends AppCompatActivity {

    TextView locTextV;
    LocationManager locManager;
    TextView gdMapTextV;

    //高德地图相关属性
    AMapLocationClient mLocationClient = null;
    AMapLocationListener locationListener;
    AMapLocationClientOption mLocationOption = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsand_gao_de_map);

        locTextV = (TextView) findViewById(R.id.use_locLotification_textV);
        gdMapTextV = (TextView) findViewById(R.id.use_gaoDeMap_textV);

        String sha1Code = sHA1(this);
        Log.d("123", sha1Code);

        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            locTextV.setText("定位失败:权限问题");

            return;
        }
        Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateView(location, "");
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 8, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateView(location, "");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                if (ActivityCompat.checkSelfPermission(GPSAndGaoDeMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(GPSAndGaoDeMapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    locTextV.setText("定位失败:权限问题");
                    return;
                }
                updateView(locManager.getLastKnownLocation(provider), provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                updateView(null, provider);
            }
        });



        //高德地图定位SDK使用
        mLocationClient = new AMapLocationClient(this);
//        mLocationClient.setApiKey("4dabd45339dec60692d24106b1d00c59");
        locationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                int lType = aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                aMapLocation.getAoiName();//获取当前定位点的AOI信息
                aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                aMapLocation.getFloor();//获取当前室内定位的楼层
                aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                // 获取定位时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
                df.setTimeZone(timeZone);
                Date date = new Date(aMapLocation.getTime());
                df.format(date);

                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        gdMapTextV.setText("高德地图的定位SDK定位信息："+"\n  经度:"+aMapLocation.getLongitude() + "\n  纬度:"+aMapLocation.getLatitude() +"\n  高度:"+aMapLocation.getAltitude()
                                +"\n  速度:"+aMapLocation.getSpeed() +"\n  方向:"+aMapLocation.getBearing() +"\n  定位时间:"+df.format(date) );
                    }else {
                        gdMapTextV.setText("高德地图定位失败：\nerrorCode: "+ aMapLocation.getErrorCode() +"\nerrorInfo: "+aMapLocation.getErrorInfo());
                        Log.d("123", "高德地图定位失败：\nerrorCode: "+ aMapLocation.getErrorCode() +"\nerrorInfo: "+aMapLocation.getErrorInfo());
                    }
                }else {
                    gdMapTextV.setText("定位失败:定位信息为空");
                }

            }
        };
        mLocationClient.setLocationListener(locationListener);

        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//高精度
        mLocationOption.setInterval(10000);//设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setNeedAddress(true);//设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setHttpTimeOut(30000);//设置请求超时时间，默认30s，最好不低于8s
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();// 启动定位

        MapView mapV = (MapView) findViewById(R.id.mapView);
        mapV.onCreate(savedInstanceState);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mLocationClient.stopLocation();
    }

    public void updateView(Location newLoc, String stateStr) {
        if (newLoc != null) {
            locTextV.setText("Android自带的定位功能"+"\n  经度:"+newLoc.getLongitude() + "\n  纬度:"+newLoc.getLatitude() +"\n  高度:"+newLoc.getAltitude()
                    +"\n  速度:"+newLoc.getSpeed() +"\n  方向:"+newLoc.getBearing());
        }else {
            locTextV.setText("定位失败:定位信息为空  "+stateStr);
        }
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");

                if (i!=0 && i!= publicKey.length) {
                    hexString.append(":");
                }
                hexString.append(appendString);
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
