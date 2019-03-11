package io.github.glascode.estateagency.database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import io.github.glascode.estateagency.database.dao.CommentDao;
import io.github.glascode.estateagency.database.dao.PropertyDao;
import io.github.glascode.estateagency.model.Comment;
import io.github.glascode.estateagency.model.Property;

@android.arch.persistence.room.Database(entities = {Property.class, Comment.class}, version = 4, exportSchema = false)
public abstract class Database extends RoomDatabase {

	private static volatile Database INSTANCE;

	public abstract PropertyDao propertyDao();

	public abstract CommentDao commentDao();

	public static Database getInstance(Context context) {
		if (INSTANCE == null) synchronized (Database.class) {
			if (INSTANCE == null)
				INSTANCE = Room.databaseBuilder(
						context.getApplicationContext(),
						Database.class,
						"saved.db"
				).fallbackToDestructiveMigration().build();
		}
		return INSTANCE;
	}
}
