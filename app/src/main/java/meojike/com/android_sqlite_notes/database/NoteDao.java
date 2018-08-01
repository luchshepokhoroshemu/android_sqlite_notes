package meojike.com.android_sqlite_notes.database;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.sql.Struct;
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

            String[] columns = {SECOND_COLUMN};
            String selection = FIRST_COLUMN + " = ?";
            String[] selectionArguments = {name};

            Cursor cursor = database.query(TABLE_NAME, columns, selection, selectionArguments, null, null, null);

            note = parseSingleName(cursor);
            cursor.close();
            database.setTransactionSuccessful(); // sic! ¡MUY IMPORTANTE!

        } catch (SQLiteException sqLiteException) {
            Log.d(TAG, "getNote: SQLITE EXCEPTION DURING ONE NOTE GETTING");
        } finally {
            if (database != null) {
                if (database.inTransaction()) {
                    database.endTransaction();
                }
                database.close();
            }
            return note;
        }
    }


    /**
     * Запрос на получение всех заметок из БД
     *
     * @param database экземпляр базы данных
     * @return список всех заметок
     */

    protected List<NoteDataModel> getNotes(SQLiteDatabase database) {
        List<NoteDataModel> noteDataModelList = null;
        String[] columns = {FIRST_COLUMN, SECOND_COLUMN};

        try {
            database.beginTransaction();
            Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);

            noteDataModelList = parseNotes(cursor);
            cursor.close();
            database.setTransactionSuccessful();
        } catch (SQLiteException sqLiteException) {
            Log.d(TAG, "getNotes: SQLITE EXCEPTION DURING NOTES GETTING");
        } finally {
            if (database != null) {
                if (database.inTransaction()) {
                    database.endTransaction();
                }
                database.close();
            }
//            return noteDataModelList;
        }
        return noteDataModelList;
    }

    /**
     * Запрос на получение названий заметок из БД
     *
     * @param database экземпляр базы данных
     * @return список названий всех заметок
     */

    protected List<String> getNotesNames(SQLiteDatabase database) {
        List<String> noteNamesList = null;
        String[] columns = {FIRST_COLUMN};

        try {
            database.beginTransaction();
            Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);

            noteNamesList = parseNotesNamesList(cursor);
            cursor.close();
            database.setTransactionSuccessful();
        } catch (SQLiteException sqLiteException) {
            Log.d(TAG, "getNotesNames: SQLITE EXCEPTION DURING GETTING NOTES NAMES");
        } finally {
            if (database != null) {
                if (database.inTransaction()) {
                    database.endTransaction();
                }
                database.close();
            }
//            return noteNamesList;
        }
        return noteNamesList;
    }

    /**
     * Запрос на добавление новой заметки в БД
     *
     * @param database          экземпляр базы данных
     * @param note     объект заметки, которую нужно будет создать
     * @return                  айди новой записи
     */

    protected long addNote(SQLiteDatabase database, NoteDataModel note) {
        long id = 0;
        try {
            database.beginTransaction();
            ContentValues contentValues = makeMyContentValues(note.getName(), note.getNoteContent());
            id = database.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_ABORT);
            database.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.d(TAG, "addNote: SQLITE EXCEPTION ADDING NOTE");
        } finally {
            if(database != null) {
                if(database.inTransaction()) {
                    database.endTransaction();
                }
                database.close();
            }
            return id;
        }
    }


    /**
     * Запрос на обновление содержания заметки
     *
     * @param database      экземпляр базы данных
     * @param note          содержание заметки
     */
    protected int updateNote(SQLiteDatabase database, NoteDataModel note) {
        int id = 0;
        String whereClause = FIRST_COLUMN + " = ?";
        String[] noteName = { note.getName() };

        try {
            database.beginTransaction();
            ContentValues contentValues = makeMyContentValues(note.getName(), note.getNoteContent());
            id = database.update(TABLE_NAME, contentValues, whereClause, noteName);
            database.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.d(TAG, "updateNote: SQLITE EXCEPTION UPDATING NOTE");
        } finally {
            if(database != null) {
                if(database.inTransaction()) {
                    database.endTransaction();
                }
                database.close();
            }
            return id;
        }
    }


    /**
     * Запрос на удаление заметки
     *
     * @param database       - a {@link SQLiteDatabase} instance
     * @param noteName - a name of note to delete
     */
    protected void deleteNote(SQLiteDatabase database, String noteName) {

        String whereClause = FIRST_COLUMN + " = ?";
        String[] whereArgs = { noteName };


        try {
            database.beginTransaction();
            database.delete(TABLE_NAME, whereClause, whereArgs);
            database.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.d(TAG, "deleteNote: SQLITE EXCEPTION NOTE DELETION");
        } finally {
            if(database != null) {
                if(database.inTransaction()) {
                    database.endTransaction();
                }
                database.close();
            }
        }
    }


    /**
     * Выпаршивает список названий заметок
     *
     * @param cursor
     * @return список названий заметок
     */
    private List<String> parseNotesNamesList(Cursor cursor) {
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(FIRST_COLUMN)));
        }
        return list;
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


    /**
     * Создает экземпляр ContentValues для записи в базу данных
     *
     * @param name название заметки
     * @param noteContent содержание заметки
     * @return экземпляр контент вельюсов
     */
    private ContentValues makeMyContentValues(String name, String noteContent) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_COLUMN, name);
        contentValues.put(SECOND_COLUMN, noteContent);
        return contentValues;
    }
}
