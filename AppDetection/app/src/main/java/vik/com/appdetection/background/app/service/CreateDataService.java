package vik.com.appdetection.background.app.service;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.provider.Settings;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import vik.com.appdetection.background.listener.LightSensor;
import vik.com.appdetection.background.reciever.BluetoothReciever;
import vik.com.appdetection.background.service.GeoLocationService;
import vik.com.appdetection.background.service.UserActivityService;
import vik.com.appdetection.pojo.FeatureData;
import vik.com.appdetection.pojo.FeatureDataBuilder;

public class CreateDataService {

    public  List<FeatureData>featureDataList=new LinkedList<>();

    public void createData(Context context,String appName)
    {
        long time=System.currentTimeMillis();
        double lat= GeoLocationService.lat;
        double lon =GeoLocationService.lon;
        int activity= UserActivityService.ACTiTIVITY_TYPE;
        boolean isWifi=isWifiOn(context);
        int isBluetoothOn= BluetoothReciever.BLUETOOTH_STATE;
        int isAudioConnected=isHeadsetOn(context);
        float illuminance= LightSensor.illuminance;
        int isWeekDay=isWeekDay();
        int charging=checkCharging(context);
        //double brightness=getScreenBrightNessMode(context);
        FeatureData featureData=new FeatureDataBuilder().setActivityType(activity).setAppName(appName).setBluetooth(isBluetoothOn).setIsAudioConnected(isAudioConnected)
                .setIsWifi(isWifi).setLat(lat).setLon(lon)
                .setTimestamp(time).setIlluminance(illuminance).setIsWeekday(isWeekDay).setChargingStatus(charging).createFeatureData();
        featureDataList.add(featureData);
    }
    public boolean isWifiOn(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        return isWiFi;
    }
    private int isHeadsetOn(Context context)
    {
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        AudioDeviceInfo[] audioDevices = audioManager.getDevices(AudioManager.GET_DEVICES_ALL);
        if(audioDevices.length>0)
        {
            return audioDevices[0].getType();
        }
        return -1;
    }
    private double getScreenBrightNessMode(Context context)
    {
        try {
            int value=Settings.System.getInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE);
            double reltBrightness=Settings.System.getInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS)/255;
            Log.d("com.vik","screen brightness "+value);
            return reltBrightness;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int isWeekDay()
    {
        Calendar calendar = Calendar.getInstance();
        int day=calendar.get(Calendar.DAY_OF_WEEK);
        if(day==1 || day==7)
        {
            return 0;
        }
        return 1;
    }

    private int checkCharging(Context context)
    {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        if(isCharging)
        {
            int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
            if(usbCharge)
            {
                return 2;
            }
            else
            {
                return 1;
            }
        }
        return 0;
    }



}
