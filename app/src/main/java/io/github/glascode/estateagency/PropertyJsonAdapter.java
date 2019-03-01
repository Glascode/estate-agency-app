package io.github.glascode.estateagency;

import android.util.Log;
import com.squareup.moshi.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PropertyJsonAdapter {

	@FromJson
	Property fromJson(JsonReader reader, JsonAdapter<Property> delegate) throws IOException {

		Property property = null;

		// démarrer le parsing du Json
		reader.beginObject();
		while (reader.hasNext()) {
			// récupérer le nom de la clé
			String name = reader.nextName();
			if (name.equals("success")) {
				boolean success = reader.nextBoolean();
				Log.i("JML", "Success vaut " + success);
				if (!success) {
					// @todo : récupérer le message d'erreur et le donner à l'exception
					// @todo : créer une exception spécifique pour la distinguer des IOException
					throw new IOException("API a répondu FALSE");
				}
			} else if (name.equals("response")) {
				// déléguer l'extraction à l'adapteur qui transforme du Json en Personne
				property = delegate.fromJson(reader);
			} else {
				// dans notre cas on ne devrait pas avoir d'autres clés que success et response dans le Json
				throw new IOException("Response contient des données non conformes");
			}
		}
		// fermer le reader
		reader.endObject();
		return property;
	}

	@ToJson
	void toJson(JsonWriter jsonWriter, List<Property> properties) {
		throw new UnsupportedOperationException();
	}

}
