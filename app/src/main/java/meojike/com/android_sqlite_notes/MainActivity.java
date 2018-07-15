package meojike.com.android_sqlite_notes;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toolbar;

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
    private RecyclerView recyclerView;
    private TextView textView;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
