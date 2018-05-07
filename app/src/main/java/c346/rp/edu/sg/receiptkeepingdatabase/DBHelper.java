package c346.rp.edu.sg.receiptkeepingdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 16023018 on 17/12/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "notes.db";
    public static final String TABLE_COMMENTS = "comments";
    // Columns names
    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";
    public static final String COL_PRIORITY = "priority";
    public static final String COL_DATE = "date";
    public static final String COL_CAT = "category";
    public static final String COL_IMAGE = "image";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_create_table = "CREATE TABLE " + TABLE_COMMENTS +
                "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_CONTENT + " TEXT NOT NULL," + COL_PRIORITY +
                " REAL,"+ COL_DATE +" TEXT,"+ COL_CAT +" TEXT,"+ COL_IMAGE +" TEXT"+")";
        db.execSQL(sql_create_table);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        // Create tables
        onCreate(db);
    }
}
