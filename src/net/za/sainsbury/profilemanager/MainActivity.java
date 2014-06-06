package net.za.sainsbury.profilemanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ProfilePreferences.Initialize(getApplicationContext());

		TimePicker silent = (TimePicker) findViewById(R.id.silent_time);
		TimePicker normal = (TimePicker) findViewById(R.id.normal_time);

		Time silent_time = ProfilePreferences.getSilentTime();
		Time normal_time = ProfilePreferences.getNormalTime();

		silent.setCurrentHour(silent_time.hour);
		silent.setCurrentMinute(silent_time.minute);

		normal.setCurrentHour(normal_time.hour);
		normal.setCurrentMinute(normal_time.minute);
	}

	public void Apply_Click(View view) {
		Context context=getApplicationContext();
		
		TimePicker silent = (TimePicker) findViewById(R.id.silent_time);
		TimePicker normal = (TimePicker) findViewById(R.id.normal_time);

		ProfilePreferences.setSilentTime(silent.getCurrentHour(),
				silent.getCurrentMinute());
		ProfilePreferences.setNormalTime(normal.getCurrentHour(),
				normal.getCurrentMinute());

		ProfileChangeIntent.StartDaemon(context);
		
		Toast t=Toast.makeText(context, context.getString(R.string.apply_confirm) , Toast.LENGTH_SHORT);
		t.show();
	}
}
