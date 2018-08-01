package meojike.com.android_sqlite_notes.handler;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

import meojike.com.android_sqlite_notes.database.MyDatabaseManager;
import meojike.com.android_sqlite_notes.database.NoteDataModel;

public class CustomHandlerThread extends HandlerThread {
    private static final String TAG = "CustomHandlerThread";

    public static final int GET_ALL_NOTES = 0xA11;
    public static final int GET_NOTE_TEXT = 0x7EC57;
    public static final int SAVE_NOTE = 0x6EE5;
    public static final int GET_ALL_NOTES_REPLY = 0xA1112E;
    public static final int GET_NOTE_TEXT_REPLY = 0x7EC5712E;
    public static final int UPDATE_NOTE = 0xABDE17;
    public static final int DELETE_NOTE = 0xD1E;

    public static final String CUSTOM_HANDLER_THREAD_NAME = "CustomHandlerThreadNameNumbuhOne";

    private final MyDatabaseManager myDatabaseManager;
    private final CustomHandler customHandler;

    private static CustomHandlerThread customHandlerThreadInstance;


    private CustomHandlerThread(String name, Context context) {
        super(name);
        this.start();
        Looper looper = this.getLooper();
        customHandler = new CustomHandler(looper);
        myDatabaseManager = MyDatabaseManager.getInstance(context);
    }


    public static CustomHandlerThread getCustomHandlerThreadInstance(String name, Context context) {
        //TODO Y SO COMPLICATED?
        if(customHandlerThreadInstance == null) {
            customHandlerThreadInstance = new CustomHandlerThread(name, context);
            Log.d(TAG, "getCustomHandlerThreadInstance: == null constructor");
        } else if(customHandlerThreadInstance.getName().equals(name)) {
            Log.d(TAG, "getCustomHandlerThreadInstance: equals name constructor");
//            return customHandlerThreadInstance;
        }

        Log.d(TAG, "getCustomHandlerThreadInstance: default constructor");
        return customHandlerThreadInstance;
    }


    public void startTask(Message msg) {
        customHandler.sendMessage(msg);
        Log.d(TAG, "startTask: STARTUEM tasky " + msg.what + " dlya threada named " + customHandlerThreadInstance.getName());
    }



    /**
     * Получить все заметки из базы данных
     *
     * @param msg полученный месседж
     */

    private void getAllNotes(Message msg) {
        List<String> names = myDatabaseManager.getNames();
        Message message = new Message();
        message.what = GET_ALL_NOTES_REPLY;
        message.obj = names;
        try {
            msg.replyTo.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * Получить текст заметки
     *
     * @param msg полученный месседж
     */
    private void getNoteText(Message msg) {
        String text = myDatabaseManager.getNote(msg.obj.toString());
        Message message = new Message();
        message.what = GET_NOTE_TEXT_REPLY;
        message.obj = text;
        try {
            msg.replyTo.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * Добавить новую заметку
     *
     * @param msg полученный месседж
     */

    private void addNote(Message msg) {
        myDatabaseManager.addNote((NoteDataModel) msg.obj);
    }


    /**
     * Обновить заметку
     *
     * @param msg полученный месседж
     */

    private void updateNote(Message msg) {
        myDatabaseManager.updateNote((NoteDataModel) msg.obj);
    }


    /**
     * Удалить заметку
     *
     * @param msg полученный месседж
     */

    private void deleteNote(Message msg) {
        myDatabaseManager.deleteNote(msg.obj.toString());
    }


    private class CustomHandler extends Handler {
        CustomHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case GET_ALL_NOTES:
                    getAllNotes(msg);
                    break;

                case GET_NOTE_TEXT:
                    getNoteText(msg);
                    break;

                case SAVE_NOTE:
                    addNote(msg);
                    break;

                case UPDATE_NOTE:
                    updateNote(msg);
                    break;

                case DELETE_NOTE:
                    deleteNote(msg);
                    break;
            }
        }
    }
}
