package vik.com.appdetection.pojo;

public class FeatureData {
    long timestamp;
    String appName;
    double lat;
    double lon;
    int activityType;
    boolean isWifi;
    int bluetooth;
    int isAudioConnected;
    double brightness;

    public FeatureData(long timestamp, String appName, double lat, double lon, int activityType, boolean isWifi, int bluetooth, int isAudioConnected, double brightness) {
        this.timestamp = timestamp;
        this.appName = appName;
        this.lat = lat;
        this.lon = lon;
        this.activityType = activityType;
        this.isWifi = isWifi;
        this.bluetooth = bluetooth;
        this.isAudioConnected = isAudioConnected;
        this.brightness = brightness;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getAppName() {
        return appName;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int getActivityType() {
        return activityType;
    }

    public boolean isWifi() {
        return isWifi;
    }

    public int getBluetooth() {
        return bluetooth;
    }

    public int getIsAudioConnected() {
        return isAudioConnected;
    }

    public double getBrightness() {
        return brightness;
    }
}
