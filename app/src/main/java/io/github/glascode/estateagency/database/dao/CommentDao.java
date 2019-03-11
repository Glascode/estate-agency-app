package io.github.glascode.estateagency.database.dao;

import android.arch.persistence.room.*;
import io.github.glascode.estateagency.model.Comment;

import java.util.List;

@Dao
public interface CommentDao {

	@Query("SELECT * FROM Comment WHERE id = :commentId")
	Comment getProperty(String commentId);

	@Query("SELECT * FROM Comment WHERE property_id = :propertyId")
	List<Comment> getCommentsFromPropertyId(String propertyId);

	@Insert
	void insertComment(Comment comment);

	@Update
	void updateComment(Comment comment);

	@Delete
	void removeComment(Comment comment);

}
