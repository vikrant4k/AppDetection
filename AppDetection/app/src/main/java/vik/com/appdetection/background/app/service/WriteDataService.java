package vik.com.appdetection.background.app.service;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vik.com.appdetection.pojo.FeatureData;

public class WriteDataService {
    public static String newline = System.getProperty("line.separator");

public static  String getCurrentDate()
{
    Date c = Calendar.getInstance().getTime();
    System.out.println("Current time => " + c);

    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c);
    return formattedDate+".csv";
}

private String convertListToString(List<FeatureData>featureDataList)
{
    StringBuilder stringBuilder=new StringBuilder();
    for (FeatureData featureData:featureDataList)
    {
        if(featureData!=null) {
            stringBuilder.append(featureData.getTimestamp()).append(',').append(featureData.getAppName()).append(',').append(featureData.getActivityType())
                    .append(',').append(featureData.getLat()).append(',')
                    .append(featureData.getLon()).append(',').append(featureData.getBluetooth()).append(',').append(featureData.getIsAudioConnected())
                    .append(',').append(featureData.isWifi())
                    .append(',').append(featureData.getIlluminance()).append(',').append(featureData.getIsWeekday()).append(",").append(featureData.getChargingStatus()).append(newline);
        }
    }
    if(featureDataList.size()>0) {
        return stringBuilder.toString();
    }
    else
    {
        return null;
    }
}

public void writeDataToFile(List<FeatureData>featureDataList, Context context)
{
    String data=convertListToString(featureDataList);
    if(data!=null) {
        String filepath= getCurrentDate();
        File file = new File(context.getFilesDir(), filepath);
        Log.d("com.vik",filepath);
        //File file = new File(filepath);
        FileOutputStream fos;
        Log.d("com.vik","file exist "+file.exists());
        if (!file.exists()) {
            try {
                fos = context.openFileOutput(filepath, context.MODE_PRIVATE);
                fos.write(data.getBytes());
                fos.close();
            } catch (Exception e) {
              Log.e("com.vik","error",e);
            }

        } else {
            try {
                fos = context.openFileOutput(filepath, context.MODE_APPEND);
                fos.write(data.getBytes());
                fos.close();
            } catch (Exception e) {
                Log.e("com.vik","error",e);
            }
        }
    }
    Log.d("com.vik","file written succesfully");

}

public void readDataFromFile(Context context)
{
    Log.d("com.vik","In file path");
    String filepath=getCurrentDate();
    try {
        //InputStream inputStream = context.openFileInput(filepath);
        FileInputStream inputStream = new FileInputStream (new File(context.getFilesDir(),filepath));
        Log.d("com.vik","In file path");
        Log.d("com.vik",filepath);
        if ( inputStream != null ) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ( (receiveString = bufferedReader.readLine()) != null ) {
                stringBuilder.append(receiveString);
            }

            inputStream.close();
            String ret = stringBuilder.toString();
            Log.d("com.vik",ret );
        }
        else
        {
            Log.d("com.vik","stream null");
        }
    }
    catch (FileNotFoundException e) {
        Log.e("com.vik", "File not found: " + e.toString());
    } catch (IOException e) {
        Log.e("com.vik", "Can not read file: " + e.toString());
    }
}


}
