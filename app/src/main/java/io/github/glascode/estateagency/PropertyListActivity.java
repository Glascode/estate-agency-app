package io.github.glascode.estateagency;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
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

		Moshi moshi = new Moshi.Builder().build();

		Type type = Types.newParameterizedType(List.class, Property.class);
		JsonAdapter<List<Property>> adapter = moshi.adapter(type);

		String jsonPropertyListString = getIntent().getStringExtra("json_property_list");

		try {
			propertyList = adapter.fromJson(jsonPropertyListString);
		} catch (IOException e) {
			e.printStackTrace();
		}

		updateUI();
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

}
