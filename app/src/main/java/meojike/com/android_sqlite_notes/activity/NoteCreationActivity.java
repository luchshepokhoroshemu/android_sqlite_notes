package meojike.com.android_sqlite_notes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import meojike.com.android_sqlite_notes.handler.CustomHandlerThread;
import meojike.com.android_sqlite_notes.R;
import meojike.com.android_sqlite_notes.database.NoteDataModel;

import static meojike.com.android_sqlite_notes.handler.CustomHandlerThread.CUSTOM_HANDLER_THREAD_NAME;
import static meojike.com.android_sqlite_notes.handler.CustomHandlerThread.SAVE_NOTE;

public class NoteCreationActivity extends AppCompatActivity {
    private static final String TAG = "NoteCreationActivity";

    private EditText noteName;
    private EditText noteContents;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_creation);

        setUI();

        setOnClickListeners();
    }

    public static final Intent newIntent(Context context) {
        return new Intent(context, NoteCreationActivity.class);
    }

    private void setUI() {
        noteName = findViewById(R.id.noteName);
        noteContents = findViewById(R.id.noteContent);
        floatingActionButton = findViewById(R.id.floatingActionButtonForNoteSaving);
    }

    private void setOnClickListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String name = noteName.getText().toString();
        String content = noteContents.getText().toString();

        if(!name.isEmpty()) {
            Message message = new Message();
            message.what = SAVE_NOTE;
            message.obj = new NoteDataModel(name, content);
            CustomHandlerThread.getCustomHandlerThreadInstance(CUSTOM_HANDLER_THREAD_NAME, getApplicationContext()).startTask(message);
            finish();
        } else {
            Toast.makeText(NoteCreationActivity.this, R.string.lack_of_note_name_alert, Toast.LENGTH_SHORT).show();
        }
    }


}
