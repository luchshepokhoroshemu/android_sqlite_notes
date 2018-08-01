package meojike.com.android_sqlite_notes.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import meojike.com.android_sqlite_notes.R;

import static meojike.com.android_sqlite_notes.activity.NoteSettingsActivity.TEXT_COLOR_KEY;
import static meojike.com.android_sqlite_notes.activity.NoteSettingsActivity.TEXT_SIZE_KEY;

public class ViewNoteFragment extends Fragment {
    public static final String VIEW_NOTE_FRAGMENT_TAG = "ViewNoteFragment";

    protected static final String NOTE_CONTENT_KEY = "noteContentKey";

    private TextView tvContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_note, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvContent = view.findViewById(R.id.note_view_text);
        tvContent.setText(getArguments().getString(NOTE_CONTENT_KEY));
        tvContent.setTextSize(getArguments().getInt(TEXT_SIZE_KEY));
        tvContent.setTextColor(getArguments().getInt(TEXT_COLOR_KEY));
    }


    public static ViewNoteFragment newInstance(String noteContent, int textColor, int textSize) {
        ViewNoteFragment viewNoteFragment = new ViewNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NOTE_CONTENT_KEY, noteContent);
        bundle.putInt(TEXT_COLOR_KEY, textColor);
        bundle.putInt(TEXT_SIZE_KEY, textSize);

        viewNoteFragment.setArguments(bundle);
        return viewNoteFragment;
    }
}
