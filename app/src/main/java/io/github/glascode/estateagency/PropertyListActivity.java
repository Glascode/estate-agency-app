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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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

	public void viewItem(View view) {
		int pos = propertyListRecyclerView.getChildLayoutPosition(view);
		Property property = propertyList.get(pos);

		Intent intent = new Intent(this, PropertyActivity.class);

		intent.putExtra("property_id", property.getId());
		intent.putExtra("property_title", property.getTitre());
		intent.putExtra("property_desc", property.getDescription());
		intent.putExtra("property_nbRooms", property.getNbPieces());

		List<String> features = new ArrayList<>(property.getCaracteristiques());
		intent.putExtra("property_features", (ArrayList<String>) features);

		intent.putExtra("property_price", property.getPrix());
		intent.putExtra("property_city", property.getVille());
		intent.putExtra("property_zipCode", property.getCodePostal());

		intent.putExtra("property_sellerId", property.getVendeur().getId());
		intent.putExtra("property_sellerSurname", property.getVendeur().getPrenom());
		intent.putExtra("property_sellerName", property.getVendeur().getNom());
		intent.putExtra("property_sellerEmail", property.getVendeur().getEmail());
		intent.putExtra("property_sellerNumber", property.getVendeur().getTelephone());

		List<String> images = new ArrayList<>(property.getImages());
		intent.putExtra("property_images", (ArrayList<String>) images);

		intent.putExtra("property_publicationDate", property.getDate());

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
				try {
					ResponseBody responseBody = response.body();

					JSONObject jsonObject = new JSONObject(responseBody.string());
					jsonObject = new JSONArray(jsonObject.getString("response")).getJSONObject(1);

					String jsonString = jsonObject.toString();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
