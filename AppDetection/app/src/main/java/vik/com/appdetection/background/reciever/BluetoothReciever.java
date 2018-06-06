package vik.com.appdetection.background.reciever;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class BluetoothReciever extends BroadcastReceiver {
    public static int BLUETOOTH_STATE=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED))
        {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR);
            switch (state)
            {
                case BluetoothAdapter.STATE_CONNECTED:
                {
                    //Do something you need here
                    BLUETOOTH_STATE=1;
                    Log.d("com.vik","bluetoooth connected");
                    break;
                }
                default:
                    Log.d("com.vik","bluetoooth  not connected");
                    BLUETOOTH_STATE=0;
                    System.out.println("Default");
                    break;
            }
        }
    }

    public IntentFilter getFilter(){
        final IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);

        return filter;
    }
}
