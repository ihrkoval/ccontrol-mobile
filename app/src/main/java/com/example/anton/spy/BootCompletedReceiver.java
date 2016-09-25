package com.example.anton.spy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Anton on 03.07.2016.
 */
public class BootCompletedReceiver extends BroadcastReceiver {
    public BootCompletedReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Toast toast = Toast.makeText(context.getApplicationContext(),
                    "loaaaaaaaaddddd", Toast.LENGTH_LONG);
            toast.show();
            //Log.d("Spy", context.getResources().getString(R.string.your_message));
            // ваш код здесь при загрузке после ребута или включения
        }
    }
}
