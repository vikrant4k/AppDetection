package vik.com.appdetection.pojo;

public class FeatureDataBuilder {
    private long timestamp;
    private String appName;
    private double lat;
    private double lon;
    private int activityType;
    private boolean isWifi;
    private int bluetooth;
    private int isAudioConnected;
    private double brightness;

    public FeatureDataBuilder setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public FeatureDataBuilder setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public FeatureDataBuilder setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public FeatureDataBuilder setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public FeatureDataBuilder setActivityType(int activityType) {
        this.activityType = activityType;
        return this;
    }

    public FeatureDataBuilder setIsWifi(boolean isWifi) {
        this.isWifi = isWifi;
        return this;
    }

    public FeatureDataBuilder setBluetooth(int bluetooth) {
        this.bluetooth = bluetooth;
        return this;
    }

    public FeatureDataBuilder setIsAudioConnected(int isAudioConnected) {
        this.isAudioConnected = isAudioConnected;
        return this;
    }

    public FeatureDataBuilder setBrightness(double brightness) {
        this.brightness = brightness;
        return this;
    }

    public FeatureData createFeatureData() {
        return new FeatureData(timestamp, appName, lat, lon, activityType, isWifi, bluetooth, isAudioConnected, brightness);
    }
}