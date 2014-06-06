package net.za.sainsbury.profilemanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.Time;

public class ProfilePreferences {
	public static SharedPreferences preferences;
	private static Context context;

	public static void Initialize(Context context) {
		ProfilePreferences.preferences = context.getSharedPreferences(
				context.getString(R.string.preference_location),
				Context.MODE_PRIVATE);
		ProfilePreferences.context = context;
	}

	public static Time getSilentTime() {
		int silent_default_hour = context.getResources().getInteger(
				R.integer.silent_default_hour);
		int silent_default_minute = context.getResources().getInteger(
				R.integer.silent_default_minute);

		Time silent_time = new Time();
		silent_time.hour = preferences.getInt(
				context.getString(R.string.silent_pref_hour),
				silent_default_hour);
		silent_time.minute = preferences.getInt(
				context.getString(R.string.silent_pref_minute),
				silent_default_minute);

		return silent_time;
	}

	public static Time getNormalTime() {
		int normal_default_hour = context.getResources().getInteger(
				R.integer.normal_default_hour);
		int normal_default_minute = context.getResources().getInteger(
				R.integer.normal_default_minute);

		Time normal_time = new Time();
		normal_time.hour = preferences.getInt(
				context.getString(R.string.normal_pref_hour),
				normal_default_hour);
		normal_time.minute = preferences.getInt(
				context.getString(R.string.normal_pref_minute),
				normal_default_minute);

		return normal_time;
	}

	public static void setSilentTime(Time t) {
		setSilentTime(t.hour, t.minute);
	}

	public static void setSilentTime(int hour, int minute) {
		SharedPreferences.Editor editor = preferences.edit();

		editor.putInt(context.getString(R.string.silent_pref_hour), hour);
		editor.putInt(context.getString(R.string.silent_pref_minute), minute);

		editor.commit();
	}

	public static void setNormalTime(Time t) {
		setNormalTime(t.hour, t.minute);
	}

	public static void setNormalTime(int hour, int minute) {
		SharedPreferences.Editor editor = preferences.edit();

		editor.putInt(context.getString(R.string.normal_pref_hour), hour);
		editor.putInt(context.getString(R.string.normal_pref_minute), minute);

		editor.commit();
	}
}
