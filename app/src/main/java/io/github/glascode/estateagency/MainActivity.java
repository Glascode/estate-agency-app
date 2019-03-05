package io.github.glascode.estateagency;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.squareup.moshi.Types;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void launchPropertyActivity(View view) {
		startActivity(new Intent(this, PropertyActivity.class));
	}

	public void launchPropertiesActivity(View view) {
		startActivity(new Intent(this, PropertiesActivity.class));
	}

	public List<Property> makePropertiesFromJson(String jsonResponse) {

		// Create adapter
		Moshi moshi = new Moshi.Builder().build();

		Type type = Types.newParameterizedType(List.class, Property.class);
		JsonAdapter<List<Property>> adapter = moshi.adapter(type);

		try {
			Log.d("Execution", "List<Property> response = adapter.fromJson(jsonResponse);");

			List<Property> properties = adapter.fromJson(jsonResponse);

			Log.d("Execution", "Executed!");
			Log.d("Size", properties.size() + " size");
			System.out.println("Size: " + properties.size());

			for (int i = 0; i < properties.size(); i++) {
				Log.d("Property", properties.get(i).toString());
			}

			return properties;
		} catch (IOException e) {
			Log.i("JML", "Error I/O");
		}

		return null;
	}

}
