package meojike.com.android_sqlite_notes.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import meojike.com.android_sqlite_notes.R;

import static meojike.com.android_sqlite_notes.activity.NoteSettingsActivity.TEXT_COLOR_KEY;
import static meojike.com.android_sqlite_notes.activity.NoteSettingsActivity.TEXT_SIZE_KEY;
import static meojike.com.android_sqlite_notes.fragment.ViewNoteFragment.NOTE_CONTENT_KEY;

public class EditNoteFragment extends Fragment {
    public static final String EDIT_NOTE_FRAGMENT_TAG = "EditNoteFragment";

    private EditText etNoteContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        etNoteContent = view.findViewById(R.id.note_edit_text);
        etNoteContent.setText(getArguments().getString(NOTE_CONTENT_KEY));
        etNoteContent.setTextSize(getArguments().getInt(TEXT_SIZE_KEY));
        etNoteContent.setTextColor(getArguments().getInt(TEXT_COLOR_KEY));

    }

    public static Fragment newInstance(String noteContent, int textColor, int textSize) {
        EditNoteFragment editNoteFragment = new EditNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NOTE_CONTENT_KEY, noteContent);
        bundle.putInt(TEXT_COLOR_KEY, textColor);
        bundle.putInt(TEXT_SIZE_KEY, textSize);

        editNoteFragment.setArguments(bundle);
        return editNoteFragment;
    }

    public String getNoteContent() {
        return etNoteContent.getText().toString();
    }
}
