package c346.rp.edu.sg.receiptkeepingdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by 16023018 on 17/12/2017.
 */

    public class CommentsDataSource {
    // Database fields
    private DBHelper dbHelper;
    private String[] allColumns = {DBHelper.COL_ID,
            DBHelper.COL_CONTENT,
            DBHelper.COL_PRIORITY,
            DBHelper.COL_DATE,
            DBHelper.COL_CAT,
            DBHelper.COL_IMAGE
            };

    public CommentsDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public boolean addComment(String content, Double priority, String date, String category, String image) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_CONTENT, content);
        values.put(DBHelper.COL_PRIORITY, priority);
        values.put(DBHelper.COL_DATE, date);
        values.put(DBHelper.COL_CAT, category);
        values.put(DBHelper.COL_IMAGE, image);
        long insertId = database.insert(DBHelper.TABLE_COMMENTS, null, values);
        database.close();
        if (insertId == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateComment(String content, Double priority, String date, String category, String image, long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_CONTENT, content);
        values.put(DBHelper.COL_PRIORITY, priority);
        values.put(DBHelper.COL_DATE, date);
        values.put(DBHelper.COL_CAT, category);
        values.put(DBHelper.COL_IMAGE, image);

        String condition = DBHelper.COL_ID + " = ?";
        String[] args = {String.valueOf(id)};
        long affectedRow = db.update(DBHelper.TABLE_COMMENTS, values, condition, args);
        db.close();
        if (affectedRow != 1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteComment(long id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String condition = DBHelper.COL_ID + " = ?";
        String[] args = {String.valueOf(id)};
        long affectedRow = database.delete(DBHelper.TABLE_COMMENTS, condition, args);
        database.close();
        if (affectedRow != 1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Comment> getAllComments() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ArrayList<Comment> comments = new ArrayList<Comment>();
        Cursor cursor = database.query(DBHelper.TABLE_COMMENTS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(0);
            String content = cursor.getString(1);
            Double priority = cursor.getDouble(2);
            String date = cursor.getString(3);
            String category = cursor.getString(4);
            String image = cursor.getString(5);

            Comment comment = new Comment(id, content, priority, date, category, image);
            comments.add(comment); cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return comments;
    }
}
