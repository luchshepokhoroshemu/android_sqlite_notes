package meojike.com.android_sqlite_notes.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import meojike.com.android_sqlite_notes.handler.CustomHandlerThread;
import meojike.com.android_sqlite_notes.R;
import meojike.com.android_sqlite_notes.adapter.NoteAdapter;
import meojike.com.android_sqlite_notes.adapter.ViewHolderBinder;

import static meojike.com.android_sqlite_notes.activity.NoteSettingsActivity.TEXT_COLOR_KEY;
import static meojike.com.android_sqlite_notes.activity.NoteSettingsActivity.TEXT_SIZE_KEY;
import static meojike.com.android_sqlite_notes.activity.NoteSettingsActivity.THIS_APP_PREFERENCES;
import static meojike.com.android_sqlite_notes.handler.CustomHandlerThread.CUSTOM_HANDLER_THREAD_NAME;
import static meojike.com.android_sqlite_notes.handler.CustomHandlerThread.DELETE_NOTE;
import static meojike.com.android_sqlite_notes.handler.CustomHandlerThread.GET_ALL_NOTES;
import static meojike.com.android_sqlite_notes.handler.CustomHandlerThread.GET_ALL_NOTES_REPLY;

/**
 * Приложение должно состоять из четырех экранов
 * Каждый экран - отдельное активити
 * Первый экран выводит список созданных заметок
 * Второй экран позволяет либо просмотреть заметку, либо отредактировать её
 * Третий экран позволяет создавать заметку
 * Четвертый экран позволяет настраивать режим вывода заметки (размер текста, цвет текста и тд)
 * */


public class NotesMainActivity extends AppCompatActivity implements ViewHolderBinder.MyBinderCallback{
    private static final String TAG = "NotesMainActivity";

    private RecyclerView rvNotes;
    private TextView tvFiller;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;
    private NoteAdapter noteAdapter;
    private DatabaseHandler databaseHandler = new DatabaseHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_main);

        setUI();
        setOnClickListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getAllNotes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: " + item.getItemId());
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(NoteSettingsActivity.newIntent(NotesMainActivity.this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUI() {
        rvNotes = findViewById(R.id.recyclerViewMain);
        tvFiller = findViewById(R.id.emptyText);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        toolbar = findViewById(R.id.mainActivityToolbar);

        setSupportActionBar(toolbar);

    }

    private void getAllNotes() {
        Message message = new Message();
        message.what = GET_ALL_NOTES;
        message.replyTo = new Messenger(databaseHandler);
        CustomHandlerThread.getCustomHandlerThreadInstance(CUSTOM_HANDLER_THREAD_NAME, getApplicationContext()).startTask(message);
    }

    private void deleteNote(String name) {
        Message message = new Message();
        message.what = DELETE_NOTE;
        message.replyTo = new Messenger(databaseHandler);
        message.obj = name;
        CustomHandlerThread.getCustomHandlerThreadInstance(CUSTOM_HANDLER_THREAD_NAME, getApplicationContext()).startTask(message);
    }

    private void setOnClickListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(NoteCreationActivity.newIntent(NotesMainActivity.this));
            }
        });
    }

    @Override
    public void handleClickToDelete(String name) {
        deleteNote(name);

        getAllNotes();
    }

    @Override
    public void handleClickToOpen(String name) {
        startActivity(NoteViewOrEditActivity.newIntent(NotesMainActivity.this, name));
    }

    /**
     * Наследник {@link Handler}, который обрабатывает методы обратного вызова операций с базами данных
     */

    private class DatabaseHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            List<String> names;
            if(msg.what == GET_ALL_NOTES_REPLY) {
                names = ((List<String>) msg.obj);

                if(names.isEmpty()) {
                    rvNotes.setVisibility(View.GONE);
                    tvFiller.setVisibility(View.VISIBLE);
                } else {
                    tvFiller.setVisibility(View.GONE);
                    rvNotes.setVisibility(View.VISIBLE);

                    Log.d(TAG, "handleMessage: setting color");
//                    int color = getSharedPreferences(THIS_APP_PREFERENCES, Context.MODE_PRIVATE).getInt(TEXT_COLOR_KEY, R.color.colorPrimaryDark);
//                    int size = getSharedPreferences(THIS_APP_PREFERENCES, Context.MODE_PRIVATE).getInt(TEXT_SIZE_KEY, 14);

                    noteAdapter = new NoteAdapter(names, NotesMainActivity.this);
                    rvNotes.setAdapter(noteAdapter);
                }
            }
        }
    }




}
