package io.github.glascode.estateagency;

import android.util.Log;
import com.squareup.moshi.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PropertiesJsonAdapter {

	@FromJson
	List<Property> fromJson(JsonReader reader, JsonAdapter<Property> delegate) throws IOException {

		List<Property> properties = new ArrayList<>();

		// begin Json parsing
		reader.beginObject();
		while (reader.hasNext()) {

			// get the key name
			String name = reader.nextName();
			if (name.equals("success")) {
				boolean success = reader.nextBoolean();
				Log.i("JML", "Success is " + success);
				if (!success) {
					// TODO: récupérer le message d'erreur et le donner à l'exception
					// TODO: créer une exception spécifique pour la distinguer des IOException
					throw new IOException("API responded FALSE");
				}
			} else if (name.equals("response")) {

				Property property = delegate.fromJson(reader);
				properties.add(property);
			} else {

				// dans notre cas on ne devrait pas avoir d'autres clés que success et response dans le Json
				throw new IOException("Response has non conform data.");
			}
		}
		// fermer le reader
		reader.endObject();
		return properties;
	}

	@ToJson
	void toJson(JsonWriter jsonWriter, List<Property> properties) {
		throw new UnsupportedOperationException();
	}

}
