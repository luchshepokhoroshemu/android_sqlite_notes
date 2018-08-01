package meojike.com.android_sqlite_notes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

public class MyDatabaseManager {
    private static final String TAG = "MyDatabaseManager";

    private static MyDatabaseManager myDatabaseManagerInstance;

    private final MyDatabaseHelper databaseHelper;
    private final NoteDao noteDao;

    public static MyDatabaseManager getInstance(Context context) {
        if (myDatabaseManagerInstance == null) {
            myDatabaseManagerInstance = new MyDatabaseManager(context);
        }

        return myDatabaseManagerInstance;
    }

    private MyDatabaseManager(Context context) {
        this.databaseHelper = new MyDatabaseHelper(context);
        this.noteDao = new NoteDao();
    }


    /**
     * Launches a callable with dao request
     *
     * @param name название заметки
     * @return текст заметки
     */
    public String getNote(final String name) {
        return noteDao.getNote(databaseHelper.getReadableDatabase(), name);
    }


    /**
     * Launches a callable with dao request
     *
     * @return названия всех заметок
     */
    public List<String> getNames() {
        return noteDao.getNotesNames(databaseHelper.getReadableDatabase());
    }


    /**
     * Добавляет заметку
     *
     * @param note - note to be added
     */
    public long addNote(final NoteDataModel note) {
        return noteDao.addNote(databaseHelper.getWritableDatabase(), note);
    }

    /**
     * Обновить текст заметки
     *
     * @param note заметка к удалению
     */
    public int updateNote(final NoteDataModel note) {
        return noteDao.updateNote(databaseHelper.getWritableDatabase(), note);

    }

    /**
     * Удаляет заметку
     *
     * @param noteName название заметки, представленной к удалению
     */
    public void deleteNote(String noteName) {
        noteDao.deleteNote(databaseHelper.getWritableDatabase(), noteName);
    }

    /**
     * Возвращает состояние базы данных
     *
     * @return
     */
    public boolean databaseReady() {
        try {
            return databaseHelper.getWritableDatabase() == null;
        } catch (NullPointerException npe) {
            return false;
        }
    }

    /**
     * @return возвращает экземпляр базы данных
     */
    public SQLiteDatabase getDatabase() {
        return databaseHelper.getWritableDatabase();
    }
}
