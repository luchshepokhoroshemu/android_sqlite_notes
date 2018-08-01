package meojike.com.android_sqlite_notes.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import meojike.com.android_sqlite_notes.R;

public class NoteSettingsActivity extends AppCompatActivity {
    private static final String TAG = "NoteSettingsActivity";

    public static final String TEXT_COLOR_KEY = "textColorKey";
    public static final String TEXT_SIZE_KEY = "textSizeKey";
    public static final String THIS_APP_PREFERENCES = "thisAppPreferences";

    private SharedPreferences preferences;

    private TextView tvNoteTextSample;
    private Button buttonIncreaseTextSize;
    private Button buttonDecreaseTextSize;
    private Button buttonChangeTextColor;
    private FloatingActionButton floatingActionButton;

    private int textSize;
    private int textColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_settings);

        initSharedPrefs();

        setUI();
        setOnClickListeners();
    }

    private void initSharedPrefs() {
        preferences = getSharedPreferences(THIS_APP_PREFERENCES, Context.MODE_PRIVATE);
        textColor = preferences.getInt(TEXT_COLOR_KEY, R.color.colorPrimaryDark);
        textSize = preferences.getInt(TEXT_SIZE_KEY, 14);
        Log.d(TAG, "initSharedPrefs: text size and color " + textSize + " " + textColor);
    }

    private void setUI() {
        tvNoteTextSample = findViewById(R.id.text_sample);
        buttonIncreaseTextSize = findViewById(R.id.button_text_size_moar);
        buttonDecreaseTextSize = findViewById(R.id.button_text_size_less);
        buttonChangeTextColor = findViewById(R.id.button_color);
        floatingActionButton = findViewById(R.id.floatingActionButtonSettings);

        tvNoteTextSample.setTextSize(textSize);
        tvNoteTextSample.setTextColor(textColor);
    }

    private void setOnClickListeners() {

        buttonChangeTextColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateNewColor();
                tvNoteTextSample.setTextColor(textColor);
            }
        });


        buttonIncreaseTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseTextSize();
                tvNoteTextSample.setTextSize(textSize);
            }
        });


        buttonDecreaseTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseTextSize();
                tvNoteTextSample.setTextSize(textSize);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndFinish();
            }
        });
    }

    private void increaseTextSize() {
        textSize += 2;
    }

    private void decreaseTextSize() {
        textSize -= 2;
    }

    private void saveAndFinish() {
        savePreferences();
        finish();
    }

    private void generateNewColor() {
        Random randomInt = new Random();

        textColor = Color.argb(randomInt.nextInt(256), randomInt.nextInt(256), randomInt.nextInt(256), randomInt.nextInt(256));

    }

    private void savePreferences() {
        preferences = getSharedPreferences(THIS_APP_PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putInt(TEXT_COLOR_KEY, textColor)
                          .putInt(TEXT_SIZE_KEY, textSize)
                          .apply();
    }

    @Override
    public void onBackPressed() {
        saveAndFinish();
    }

    public static final Intent newIntent(Context context) {
        return new Intent(context, NoteSettingsActivity.class);
    }
}
