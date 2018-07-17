package meojike.com.android_sqlite_notes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mysuperdatabase.db";
    private static final int DB_VERSION = 9000;

    protected MyDatabaseHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    private MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createEmptyTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     *  Создает пустую таблицу заметок (NOTE)
     *
     * @param db
     */
    private void createEmptyTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE NOTE(name TEXT PRIMARY KEY, note TEXT)");
    }
}
