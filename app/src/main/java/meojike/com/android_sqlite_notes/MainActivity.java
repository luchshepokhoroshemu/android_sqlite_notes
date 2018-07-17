package meojike.com.android_sqlite_notes;

import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.Objects;

/**
 * Приложение должно состоять из четырех экранов
 * Каждый экран - отдельное активити
 * Первый экран выводит список созданных заметок
 * Второй экран позволяет либо просмотреть заметку, либо отредактировать её
 * Третий экран позволяет создавать заметку
 * Четвертый экран позволяет настраивать режим вывода заметки (размер текста, цвет текста и тд)
 * */

//TODO полностью скачать проект, запустить, посмотреть и сделать
    //TODO и рассмотреть последнее задание
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private TextView textView;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUI();
        setOnClickListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Message msg = new Message();
        msg.what = GET_ALL_NOTES;
        msg.replyTo = new Messenger(uiHandler);
        Objects.requireNonNull(WorkerThread.getInstance(WORKER_NAME)).launchTask(msg);
    }

    private void setUI() {
        recyclerView = findViewById(R.id.recyclerViewMain);
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }

    private void setOnClickListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: FABCLICKED");
            }
        });
    }




}
