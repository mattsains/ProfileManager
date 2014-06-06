package net.za.sainsbury.profilemanager;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.text.format.Time;
import android.widget.Toast;

public class ProfileChangeIntent extends WakefulBroadcastReceiver {
	public static void StartDaemon(Context context) {
		Time silent_time = ProfilePreferences.getSilentTime();
		Time normal_time = ProfilePreferences.getNormalTime();

		Calendar silent_cal = Calendar.getInstance();
		Calendar normal_cal = Calendar.getInstance();

		// Set up the silent change alarm
		silent_cal.set(Calendar.HOUR_OF_DAY, silent_time.hour);
		silent_cal.set(Calendar.MINUTE, silent_time.minute);

		if (silent_cal.before(Calendar.getInstance())) // it will happen
														// tomorrow
			silent_cal.add(Calendar.DATE, 1);

		silent_cal.set(Calendar.SECOND, 0); // clamp to the minute

		// Set an alarm every day at this time
		AlarmManager alarm_manager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent silent_intent = new Intent(context, ProfileChangeIntent.class);
		silent_intent.putExtra(context.getString(R.string.profile_change_to),
				context.getString(R.string.profile_change_to_silent));
		PendingIntent silent_alarmIntent = PendingIntent.getBroadcast(context,
				R.id.profile_change_silent, silent_intent, 0);
		alarm_manager.setRepeating(AlarmManager.RTC_WAKEUP,
				silent_cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
				silent_alarmIntent);

		// Set up the normal change alarm
		normal_cal.set(Calendar.HOUR_OF_DAY, normal_time.hour);
		normal_cal.set(Calendar.MINUTE, normal_time.minute);

		if (normal_cal.before(Calendar.getInstance())) // it will happen
														// tomorrow
			normal_cal.add(Calendar.DATE, 1);

		normal_cal.set(Calendar.SECOND, 0); // clamp to the minute

		// Set an alarm every day at this time
		Intent normal_intent = new Intent(context, ProfileChangeIntent.class);
		normal_intent.putExtra(context.getString(R.string.profile_change_to),
				context.getString(R.string.profile_change_to_normal));
		PendingIntent normal_alarmIntent = PendingIntent.getBroadcast(context,
				R.id.profile_change_normal, normal_intent, 0);
		alarm_manager.setRepeating(AlarmManager.RTC_WAKEUP,
				normal_cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
				normal_alarmIntent);

		// Make sure this function gets re-called at boot
		ComponentName bootreceiver = new ComponentName(context,
				BootReceiver.class);
		PackageManager pm = context.getPackageManager();

		pm.setComponentEnabledSetting(bootreceiver,
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
				PackageManager.DONT_KILL_APP);

	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();

		String changeType = (String) extras.get(context
				.getString(R.string.profile_change_to));

		String message;
		AudioManager am = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		if (changeType.equals(context
				.getString(R.string.profile_change_to_normal))) {
			message = context.getString(R.string.normal_notify);
			am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		} else {
			message = context.getString(R.string.silent_notify);
			am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		}

		Toast t = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		t.show();
	}
}
