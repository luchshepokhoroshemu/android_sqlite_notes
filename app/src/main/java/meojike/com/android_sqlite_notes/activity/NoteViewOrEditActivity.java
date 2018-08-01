package meojike.com.android_sqlite_notes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import meojike.com.android_sqlite_notes.fragment.EditNoteFragment;
import meojike.com.android_sqlite_notes.fragment.ViewNoteFragment;
import meojike.com.android_sqlite_notes.handler.CustomHandlerThread;
import meojike.com.android_sqlite_notes.R;
import meojike.com.android_sqlite_notes.database.NoteDataModel;

import static meojike.com.android_sqlite_notes.activity.NoteSettingsActivity.TEXT_COLOR_KEY;
import static meojike.com.android_sqlite_notes.activity.NoteSettingsActivity.TEXT_SIZE_KEY;
import static meojike.com.android_sqlite_notes.activity.NoteSettingsActivity.THIS_APP_PREFERENCES;
import static meojike.com.android_sqlite_notes.fragment.EditNoteFragment.EDIT_NOTE_FRAGMENT_TAG;
import static meojike.com.android_sqlite_notes.fragment.ViewNoteFragment.VIEW_NOTE_FRAGMENT_TAG;
import static meojike.com.android_sqlite_notes.handler.CustomHandlerThread.CUSTOM_HANDLER_THREAD_NAME;

public class NoteViewOrEditActivity extends AppCompatActivity {
    private static final String TAG = "NoteViewOrEditActivity";

    public static final String NOTE_NAME_KEY = "noteNameKey";

    private NoteDataModel noteDataModel;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;

    private int textColor;
    private int textSize;

    private NoteInteractionHandler noteInteractionHandler = new NoteInteractionHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view_or_edit);

        setUI();

        getTextColorAndSizeData();

        processMessageGetting();
    }

    private void setUI() {
        toolbar = findViewById(R.id.noteViewEditToolbar);
        floatingActionButton = findViewById(R.id.floatingActionButtonViewOrEdit);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteDataModel.setNoteContent(((EditNoteFragment) getSupportFragmentManager().findFragmentByTag(EDIT_NOTE_FRAGMENT_TAG)).getNoteContent());
                Message msg = new Message();
                msg.what = CustomHandlerThread.UPDATE_NOTE;
                msg.obj = noteDataModel;
                CustomHandlerThread.getCustomHandlerThreadInstance(CUSTOM_HANDLER_THREAD_NAME, getApplicationContext()).startTask(msg);
            }
        });

        setSupportActionBar(toolbar);
    }

    private void getTextColorAndSizeData() {
        textColor = getSharedPreferences(THIS_APP_PREFERENCES, Context.MODE_PRIVATE).getInt(TEXT_COLOR_KEY, R.color.colorPrimaryDark);
        textSize = getSharedPreferences(THIS_APP_PREFERENCES, Context.MODE_PRIVATE).getInt(TEXT_SIZE_KEY, 14);
    }

    private void setToolbarData(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_edit_note:
                if(!item.isChecked()) {
                    item.setIcon(R.drawable.ic_edit_black_24dp_checked);
                    item.setChecked(true);
                    toolbar.getMenu().getItem(1).setIcon(R.drawable.ic_local_library_black_24dp);
                    toolbar.getMenu().getItem(1).setChecked(false);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, EditNoteFragment.newInstance(noteDataModel.getNoteContent(), textColor, textSize), EDIT_NOTE_FRAGMENT_TAG)
                            .commit();

                    floatingActionButton.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.action_view_note:
                if(!item.isChecked()) {
                    item.setIcon(R.drawable.ic_local_library_black_24dp_checked);
                    item.setChecked(true);
                    toolbar.getMenu().getItem(0).setIcon(R.drawable.ic_edit_black_24dp);
                    toolbar.getMenu().getItem(0).setChecked(false);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, ViewNoteFragment.newInstance(noteDataModel.getNoteContent(), textColor, textSize), VIEW_NOTE_FRAGMENT_TAG)
                            .commit();

                    floatingActionButton.setVisibility(View.INVISIBLE);
                }
                break;
        }
        return true;
    }

    private void processMessageGetting() {
        Message message = new Message();
        message.what = CustomHandlerThread.GET_NOTE_TEXT;
        message.obj = getIntent().getStringExtra(NOTE_NAME_KEY);
        message.replyTo = new Messenger(noteInteractionHandler);

        CustomHandlerThread.getCustomHandlerThreadInstance(CUSTOM_HANDLER_THREAD_NAME, getApplicationContext()).startTask(message);
    }

    public static final Intent newIntent(Context context, String name) {
        return new Intent(context, NoteViewOrEditActivity.class).putExtra(NOTE_NAME_KEY, name);
    }

    private class NoteInteractionHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == CustomHandlerThread.GET_NOTE_TEXT_REPLY) {
                noteDataModel = new NoteDataModel(getIntent().getStringExtra(NOTE_NAME_KEY), msg.obj.toString());

                setToolbarData(getIntent().getStringExtra(NOTE_NAME_KEY));

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ViewNoteFragment.newInstance(noteDataModel.getNoteContent(), textColor, textSize), EDIT_NOTE_FRAGMENT_TAG)
                        .commit();


                floatingActionButton.setVisibility(View.GONE);
            }
        }
    }
}
