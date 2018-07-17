package meojike.com.android_sqlite_notes.database;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NoteDao {
    private static final String TAG = "NoteDao";
    private static final String TABLE_NAME = "NOTE";
    private static final String FIRST_COLUMN = "name";
    private static final String SECOND_COLUMN = "note_contents";

    protected String getNote(SQLiteDatabase database, String name) {
        String note = null;

        try {
            database.beginTransaction();

            String[] columns = { SECOND_COLUMN };
            String selection = FIRST_COLUMN + " = ?";
            String[] selectionArguments = { name };

            Cursor cursor = database.query(TABLE_NAME, columns, selection, selectionArguments, null, null, null );

            note = parseSingleName(cursor);
            cursor.close();
            database.setTransactionSuccessful(); // sic! ¡MUY IMPORTANTE!

        } catch (SQLiteException sqLiteException) {
            Log.d(TAG, "getNote: SQLITE EXCEPTION");
        } finally {
            if(database != null) {
                if(database.inTransaction()) {
                    database.endTransaction();
                }
                database.close();
            }
            return note; //TODO VNIMATEJIbHEE MOZHET DOLZHNO 6bITb Hu}I{E
        }
    }


    /**
     * Запрос на получение всех заметок из БД
     *
     * @param database экземпляр базы данных
     * @return a list of notes
     */

    protected List<NoteDataModel> getNotes(SQLiteDatabase database) {
        List<NoteDataModel> noteDataModelList;
        String[] columns = { FIRST_COLUMN, SECOND_COLUMN };

        database.beginTransaction();
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);

    }







    /**
     * Выпаршивает текст из данного курсора
     *
     * @param cursor курсор с именем
     * @return note текст заметки
     */
    private String parseSingleName(Cursor cursor) {
        return cursor.moveToFirst() ? cursor.getString(cursor.getColumnIndex(SECOND_COLUMN)) : null;
    }

    /**
     * Выпаршивает заметки из данного курсора
     *
     * @param cursor курсор с именем
     * @return список заметок
     */

    private List<NoteDataModel> parseNotes(Cursor cursor) {
        List<NoteDataModel> noteDataModelList = new ArrayList<>();
        while (cursor.moveToNext()) {
            noteDataModelList.add(new NoteDataModel(cursor.getString(cursor.getColumnIndex(FIRST_COLUMN)),
                    cursor.getString(cursor.getColumnIndex(SECOND_COLUMN))));
        }
        return noteDataModelList;
    }
}
