package notes.brain.secondbrain.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_SUBJECTS_TABLE =
            "CREATE TABLE " + DatabaseContract.Subjects.TABLE_NAME + " (" +
                    DatabaseContract.Subjects._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.Subjects.COLUMN_NAME_SUBJECT_NAME + " TEXT)";

    private static final String SQL_CREATE_TAGS_TABLE =
            "CREATE TABLE " + DatabaseContract.Tags.TABLE_NAME + " (" +
                    DatabaseContract.Tags._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.Tags.COLUMN_NAME_TAG_NAME + " TEXT)";

    private static final String SQL_CREATE_TOPICS_TABLE =
            "CREATE TABLE " + DatabaseContract.Topics.TABLE_NAME + " (" +
                    DatabaseContract.Topics._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.Topics.COLUMN_NAME_TOPIC_NAME + " TEXT," +
                    DatabaseContract.Topics.COLUMN_NAME_SUBJECT_ID + " INTEGER," +
                    "FOREIGN KEY(" + DatabaseContract.Topics.COLUMN_NAME_SUBJECT_ID + ") REFERENCES " +
                    DatabaseContract.Subjects.TABLE_NAME + "(" + DatabaseContract.Subjects._ID + "))";

    private static final String SQL_CREATE_TOPIC_TAGS_TABLE =
            "CREATE TABLE " + DatabaseContract.TopicTags.TABLE_NAME + " (" +
                    DatabaseContract.TopicTags._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.TopicTags.COLUMN_NAME_TOPIC_ID + " INTEGER," +
                    DatabaseContract.TopicTags.COLUMN_NAME_TAG_ID + " INTEGER," +
                    "FOREIGN KEY(" + DatabaseContract.TopicTags.COLUMN_NAME_TOPIC_ID + ") REFERENCES " +
                    DatabaseContract.Topics.TABLE_NAME + "(" + DatabaseContract.Topics._ID + ")," +
                    "FOREIGN KEY(" + DatabaseContract.TopicTags.COLUMN_NAME_TAG_ID + ") REFERENCES " +
                    DatabaseContract.Tags.TABLE_NAME + "(" + DatabaseContract.Tags._ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SUBJECTS_TABLE);
        db.execSQL(SQL_CREATE_TAGS_TABLE);
        db.execSQL(SQL_CREATE_TOPICS_TABLE);
        db.execSQL(SQL_CREATE_TOPIC_TAGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // Other methods
}
