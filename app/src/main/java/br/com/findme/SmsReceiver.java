package br.com.findme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {

    private String ringMessage = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdus = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdus.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    String message = currentMessage.getDisplayMessageBody();

                    loadRingMessage(context);

                    if (message.trim().equalsIgnoreCase(ringMessage)) {
                        Intent startIntent = new Intent(context, RingActivity.class);
                        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(startIntent);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }
    }

    private void loadRingMessage(Context context) {
        if (ringMessage == null) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

            ringMessage = sharedPref.getString(context.getString(R.string.preference_message_key), null);
        }
    }
}
