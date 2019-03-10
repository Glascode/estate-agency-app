package io.github.glascode.estateagency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import io.github.glascode.estateagency.model.Property;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class PropertyListActivity extends AppCompatActivity {

	private String jsonPropertyList;
	private List<Property> propertyList;

	private RecyclerView propertyListRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Objects.requireNonNull(getSupportActionBar()).hide();

		setContentView(R.layout.activity_property_list);

		propertyListRecyclerView = findViewById(R.id.layout_property_list_view);
		propertyListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		Moshi moshi = new Moshi.Builder().build();

		Type type = Types.newParameterizedType(List.class, Property.class);
		JsonAdapter<List<Property>> adapter = moshi.adapter(type);

		jsonPropertyList = getIntent().getStringExtra("json_property_list");

		try {
			propertyList = adapter.fromJson(jsonPropertyList);
		} catch (IOException e) {
			e.printStackTrace();
		}

		updateUI();
	}

	public void viewItem(View view) throws JSONException {
		int itemPos = propertyListRecyclerView.getChildLayoutPosition(view);
		String jsonPropertyString = new JSONArray(jsonPropertyList).get(itemPos).toString();

		Intent intent = new Intent(this, PropertyActivity.class);
		intent.putExtra("json_property", jsonPropertyString);

		startActivity(intent);
	}

	private void updateUI() {
		propertyListRecyclerView.setAdapter(new PropertyAdapter(propertyList));
	}

}
