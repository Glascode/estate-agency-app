package io.github.glascode.estateagency.database;

import android.content.Context;
import android.os.AsyncTask;
import io.github.glascode.estateagency.model.Property;

public class InsertPropertyTask extends AsyncTask<Void, Void, Void> {

	private final Context context;
	private final Property property;

	public InsertPropertyTask(Context context, Property property) {
		this.context = context;
		this.property = property;
	}

	@Override
	protected Void doInBackground(Void... voids) {
		Database.getInstance(context).propertyDao().insertProperty(property);

		return null;
	}
}
