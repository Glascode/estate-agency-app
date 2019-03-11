package io.github.glascode.estateagency.database;

import android.content.Context;
import android.os.AsyncTask;
import io.github.glascode.estateagency.model.Comment;

import java.util.List;

public class GetCommentsFromPropertyIdTask extends AsyncTask<Void, Void, List<Comment>> {

	private Context context;
	private String propertyId;

	public GetCommentsFromPropertyIdTask(Context context, String propertyId) {
		this.context = context;
		this.propertyId = propertyId;
	}

	@Override
	protected List<Comment> doInBackground(Void... voids) {
		return Database.getInstance(context).commentDao().getCommentsFromPropertyId(propertyId);
	}
}
