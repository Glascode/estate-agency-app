package io.github.glascode.estateagency.database;

import android.content.Context;
import android.os.AsyncTask;
import io.github.glascode.estateagency.model.Property;

public class GetPropertyTask extends AsyncTask<Void, Void, Property> {

	private final Context context;
	private final String propertyId;

	public GetPropertyTask(Context context, String propertyId) {
		this.context = context;
		this.propertyId = propertyId;
	}

	@Override
	protected Property doInBackground(Void... voids) {
		return Database.getInstance(context).propertyDao().getProperty(propertyId);
	}
}
