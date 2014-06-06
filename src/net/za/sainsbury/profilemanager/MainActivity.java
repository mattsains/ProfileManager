package net.za.sainsbury.profilemanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.TimePicker;

public class MainActivity extends ActionBarActivity {

	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		preferences = getPreferences(Context.MODE_PRIVATE);
		
		int silent_default_hour=getResources().getInteger(R.integer.silent_default_hour);
		int silent_default_minute=getResources().getInteger(R.integer.silent_default_minute);
		int normal_default_hour=getResources().getInteger(R.integer.normal_default_hour);
		int normal_default_minute=getResources().getInteger(R.integer.normal_default_minute);
		
		TimePicker silent=(TimePicker)findViewById(R.id.silent_time);
		TimePicker normal=(TimePicker)findViewById(R.id.normal_time);
		
		Time silent_time=new Time();
		silent_time.hour=preferences.getInt(getString(R.id.silent_pref_hour), silent_default_hour);
		silent_time.minute=preferences.getInt(getString(R.id.silent_pref_minute), silent_default_minute);
		
		Time normal_time=new Time();
		normal_time.hour=preferences.getInt(getString(R.id.normal_pref_hour), normal_default_hour);
		normal_time.minute=preferences.getInt(getString(R.id.normal_pref_minute), normal_default_minute);
		
		
		silent.setCurrentHour(silent_time.hour);
		silent.setCurrentMinute(silent_time.minute);
		
		normal.setCurrentHour(normal_time.hour);
		normal.setCurrentMinute(normal_time.minute);
	}
	
	public void Apply_Click(View view) {
		TimePicker silent=(TimePicker)findViewById(R.id.silent_time);
		TimePicker normal=(TimePicker)findViewById(R.id.normal_time);
		
		SharedPreferences.Editor editor=preferences.edit();
		
		editor.putInt(getString(R.id.silent_pref_hour), silent.getCurrentHour());
		editor.putInt(getString(R.id.silent_pref_minute), silent.getCurrentMinute());
		editor.putInt(getString(R.id.normal_pref_hour), normal.getCurrentHour());
		editor.putInt(getString(R.id.normal_pref_minute), normal.getCurrentMinute());
		
		editor.commit();
	}
}
