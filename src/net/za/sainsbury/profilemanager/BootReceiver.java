package net.za.sainsbury.profilemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			ProfilePreferences.Initialize(context);
			ProfileChangeIntent.StartDaemon(context);
			Toast t = Toast.makeText(context, "Initialized Profile scheduler",
					Toast.LENGTH_LONG);
			t.show();
		}
	}
}