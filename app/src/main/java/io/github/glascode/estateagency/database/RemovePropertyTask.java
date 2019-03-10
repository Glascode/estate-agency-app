package io.github.glascode.estateagency.database;

import android.content.Context;
import android.os.AsyncTask;
import io.github.glascode.estateagency.model.Property;

public class RemovePropertyTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private Property property;

	public RemovePropertyTask(Context context, Property property) {
		this.context = context;
		this.property = property;
	}

	@Override
	protected Void doInBackground(Void... voids) {
		Database.getInstance(context).propertyDao().removeProperty(property);

		return null;
	}
}
