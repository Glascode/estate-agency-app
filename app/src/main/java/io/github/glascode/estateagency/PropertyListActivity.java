package io.github.glascode.estateagency;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class PropertyListActivity extends AppCompatActivity {

	private List<Property> propertyList;

	private RecyclerView propertyListRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property_list);

		propertyListRecyclerView = findViewById(R.id.layout_property_list_view);
		propertyListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		makeRequest("https://ensweb.users.info.unicaen.fr/android-estate/mock-api/dernieres.json");
	}

	public void viewItem(View v) {
		String propertyID = ((TextView) v.findViewById(R.id.text_property_item_id)).getText().toString();

		Intent intent = new Intent(this, PropertyActivity.class);
		intent.putExtra("property_id", propertyID);

		startActivity(intent);
	}

	private void updateUI() {
		propertyListRecyclerView.setAdapter(new PropertyAdapter(propertyList));
	}

	private void makeRequest(String url) {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url(url).build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				try (ResponseBody responseBody = response.body()) {
					if (!response.isSuccessful()) {
						throw new IOException("Unexpected HTTP code " + response);
					}

					Moshi moshi = new Moshi.Builder().build();

					JsonAdapter<PropertiesResponse> adapter = moshi.adapter(PropertiesResponse.class);

					PropertiesResponse propertiesResponse = adapter.fromJson(responseBody.string());
					propertyList = propertiesResponse.getResponse();

					for(Property property : propertyList)
						property.setDate(property.getDate() * 1000);

					// Update UI
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							updateUI();
						}
					});
				}
			}
		});
	}

	public static class PropertiesResponse {

		public List<Property> response;

		public List<Property> getResponse() {
			return response;
		}

	}

}
