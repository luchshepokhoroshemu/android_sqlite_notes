package meojike.com.android_sqlite_notes.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import javax.security.auth.callback.Callback;

public class ViewHolderBinder {
    private static final String TAG = "ViewHolderBinder";

    private MyBinderCallback myBinderCallback;


    ViewHolderBinder(MyBinderCallback myBinderCallback) {
        this.myBinderCallback = myBinderCallback;
    }

    public void bindViewHolder(RecyclerView.ViewHolder viewHolder, final String noteName) {
        NoteAdapter.NoteHolder noteHolder = (NoteAdapter.NoteHolder) viewHolder;
        noteHolder.noteName.setText(noteName);

        noteHolder.noteName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBinderCallback.handleClickToOpen(noteName);
            }
        });

        noteHolder.deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBinderCallback.handleClickToDelete(noteName);
            }
        });
    }

    public interface MyBinderCallback {
        void handleClickToDelete(String name);

        void handleClickToOpen(String name);
    }

}
